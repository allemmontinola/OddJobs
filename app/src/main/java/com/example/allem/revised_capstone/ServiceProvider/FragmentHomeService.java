package com.example.allem.revised_capstone.ServiceProvider;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.allem.revised_capstone.API.ServiceApi;
import com.example.allem.revised_capstone.Adapters.ServiceHomeAdapter;
import com.example.allem.revised_capstone.Constants;
import com.example.allem.revised_capstone.Data.ServiceAppointData;
import com.example.allem.revised_capstone.Data.ServiceHomeData;
import com.example.allem.revised_capstone.Login;
import com.example.allem.revised_capstone.Model.CountAppointModel;
import com.example.allem.revised_capstone.Model.ServiceAppointModel;
import com.example.allem.revised_capstone.Model.ServiceHomeAcceptModel;
import com.example.allem.revised_capstone.Model.ServiceHomeModel;
import com.example.allem.revised_capstone.Model.SuccessModel;
import com.example.allem.revised_capstone.R;

import java.util.ArrayList;

import br.com.goncalves.pugnotification.notification.PugNotification;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.dimorinny.floatingtextbutton.FloatingTextButton;


public class FragmentHomeService extends Fragment implements SearchView.OnQueryTextListener {
    View view;
    public static ArrayList<ServiceHomeModel> service_home_list;
    RecyclerView myRecycler;
    RecyclerView.LayoutManager layoutManager;
    private ServiceHomeAdapter adapter;

    private void MyViews(View view) {
        SearchView search = view.findViewById(R.id.service_home_search);
        search.setOnQueryTextListener(this);
        myRecycler = view.findViewById(R.id.service_home_lists);
        homeService();
        loadData();
    }

    TextView tvAcceptJob;
    String strTitle, strDesc, strReward, strpostedBy;
    LinearLayout linear_list, linear_job_accept;
    private void homeService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final ServiceApi service = retrofit.create(ServiceApi.class);
        Call<ServiceHomeData> call = service.service_show_home();
        call.enqueue(new Callback<ServiceHomeData>() {
            @Override
            public void onResponse(Call<ServiceHomeData> call, final Response<ServiceHomeData> response) {
                service_home_list = new ArrayList<>();
                service_home_list = response.body().getHome();
                layoutManager = new LinearLayoutManager(getActivity());
                adapter = new ServiceHomeAdapter(getActivity(), service_home_list);

                myRecycler.setLayoutManager(layoutManager);
                myRecycler.setHasFixedSize(true);
                myRecycler.setAdapter(adapter);
                myRecycler.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), myRecycler, new ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
                        LayoutInflater inflater = getLayoutInflater();
                        View dialogView = inflater.inflate(R.layout.fragment_home_service_pop, null);
                        dialogBuilder.setView(dialogView);

                        tvAcceptJob = dialogView.findViewById(R.id.home_service_pop_accept_tv);

                        final AlertDialog alertDialog = dialogBuilder.create();
                        alertDialog.show();

                        strTitle = service_home_list.get(position).getTitle();
                        strDesc = service_home_list.get(position).getDescription();
                        strReward = service_home_list.get(position).getReward();
                        strpostedBy = service_home_list.get(position).getPostedBy();


                        tvAcceptJob.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Retrofit retro = new Retrofit.Builder()
                                        .baseUrl(Constants.BASEURL)
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .build();
                                ServiceApi serv = retro.create(ServiceApi.class);
                                Call<SuccessModel> success_call = serv.service_home_acceptJob(strTitle, strDesc, strReward, strpostedBy, Login.email);
                                success_call.enqueue(new Callback<SuccessModel>() {
                                    @Override
                                    public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                                        if (response.body().getSuccess() == 0) {
                                            Toast.makeText(getActivity(), "You cannot accept job anymore", Toast.LENGTH_SHORT).show();
                                            alertDialog.dismiss();
                                            linear_job_accept.setVisibility(View.GONE);
                                            linear_list.setVisibility(View.VISIBLE);

                                        } else if (response.body().getSuccess() == 1) {
                                            Toast.makeText(getActivity(), "Job Accepted", Toast.LENGTH_SHORT).show();
                                            alertDialog.dismiss();
                                            showJobAccept();
                                            linear_job_accept.setVisibility(View.VISIBLE);
                                            linear_list.setVisibility(View.GONE);
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<SuccessModel> call, Throwable t) {

                                    }
                                });
                            }
                        });
                    }

                    @Override
                    public void onLongClick(View view, int position) {

                    }
                }));

            }

            @Override
            public void onFailure(Call<ServiceHomeData> call, Throwable t) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home_service, container, false);
        MyViews(view);
        linear_list = view.findViewById(R.id.linear_home_service_list);
        linear_job_accept = view.findViewById(R.id.linear_home_service_job_accept);

        tvTitleShow = view.findViewById(R.id.home_service_accept_title_tv);
        tvDescShow = view.findViewById(R.id.home_service_accept_desc_tv);
        tvRewardShow = view.findViewById(R.id.home_service_accept_reward_tv);
        tvPostedbyShow = view.findViewById(R.id.home_service_accept_postedBy_tv);

        btnDoneJob = view.findViewById(R.id.home_service_accept_btn);
        notif();
        return view;
    }

    TextView tvTitleShow, tvDescShow, tvRewardShow, tvPostedbyShow;
    FloatingTextButton btnDoneJob;
    private void showJobAccept(){
        Retrofit  retro = new Retrofit.Builder()
                .baseUrl(Constants.BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ServiceApi service = retro.create(ServiceApi.class);
        Call<ServiceHomeAcceptModel> call = service.service_home_accept_show(Login.email);
        call.enqueue(new Callback<ServiceHomeAcceptModel>() {
            @Override
            public void onResponse(Call<ServiceHomeAcceptModel> call, Response<ServiceHomeAcceptModel> response) {
                tvTitleShow.setText(response.body().getTitle());
                tvDescShow.setText(response.body().getDescription());
                tvRewardShow.setText(response.body().getReward());
                tvPostedbyShow.setText(response.body().getPostedBy());
            }

            @Override
            public void onFailure(Call<ServiceHomeAcceptModel> call, Throwable t) {

            }
        });
    }

    private void loadData(){
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();


        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        loadPost();
                        insertLatLong_Service();
                        count_appoint();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }
    private void loadPost(){
         Retrofit retro = new Retrofit.Builder()
                 .baseUrl(Constants.BASEURL)
                 .addConverterFactory(GsonConverterFactory.create())
                 .build();
         ServiceApi service = retro.create(ServiceApi.class);
         Call<SuccessModel> call = service.service_home_accept_check(Login.email);
         call.enqueue(new Callback<SuccessModel>() {
             @Override
             public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                 if(response.body().getSuccess() == 1){
                     linear_job_accept.setVisibility(View.VISIBLE);
                     linear_list.setVisibility(View.GONE);

                 }
                 else if(response.body().getSuccess() == 0){
                     linear_job_accept.setVisibility(View.GONE);
                     linear_list.setVisibility(View.VISIBLE);

                 }
             }

             @Override
             public void onFailure(Call<SuccessModel> call, Throwable t) {

             }
         });

    }

    private void insertLatLong_Service(){
        Retrofit retro = new Retrofit.Builder()
                .baseUrl(Constants.BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ServiceApi serv = retro.create(ServiceApi.class);
        Call<SuccessModel> call = serv.serv_latlong(Login.id ,Login.latitude, Login.longtitude);
        call.enqueue(new Callback<SuccessModel>() {
            @Override
            public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                Toast.makeText(getActivity(), "Latitude: " + Login.latitude + "Longtitude: " + Login.latitude , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<SuccessModel> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    private interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private FragmentHomeService.ClickListener clickListener;

        RecyclerTouchListener(Context context, final RecyclerView recyclerView, final FragmentHomeService.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null)
                        clickListener.onLongClick(child, recyclerView.getChildAdapterPosition(child));
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildAdapterPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
    private void notif(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ServiceApi service = retrofit.create(ServiceApi.class);
        Call<ServiceAppointData> call = service.service_appoint_show(Login.email);
        call.enqueue(new Callback<ServiceAppointData>() {
            @Override
            public void onResponse(Call<ServiceAppointData> call, Response<ServiceAppointData> response) {
                if(response.body().getSuccess() == 1){
                    Retrofit retrofit_ = new Retrofit.Builder()
                            .baseUrl(Constants.BASEURL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    ServiceApi serviceApi = retrofit_.create(ServiceApi.class);
                    Call<CountAppointModel> calls = serviceApi.count_serv(Login.email);
                    calls.enqueue(new Callback<CountAppointModel>() {
                        @Override
                        public void onResponse(Call<CountAppointModel> call, Response<CountAppointModel> response) {
                            if(response.body().getSuccess() == 1){
                                PugNotification.with(getContext())
                                        .load()
                                        .title("Appointment Request")
                                        .message("You have " + response.body().getCountAppoint() + " appointment(s)")
                                        .smallIcon(R.drawable.ic_notifications)
                                        .largeIcon(R.drawable.logo)
                                        .flags(Notification.DEFAULT_ALL)
                                        .simple()
                                        .build();
                            }
                        }

                        @Override
                        public void onFailure(Call<CountAppointModel> call, Throwable t) {

                        }
                    });

                }
                else {
                    Toast.makeText(getContext(), "No appointments", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ServiceAppointData> call, Throwable t) {

            }
        });
    }
    String count_appoint;
    private void count_appoint(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ServiceApi serviceApi = retrofit.create(ServiceApi.class);
        Call<CountAppointModel> call = serviceApi.count_serv(Login.email);
        call.enqueue(new Callback<CountAppointModel>() {
            @Override
            public void onResponse(Call<CountAppointModel> call, Response<CountAppointModel> response) {

                count_appoint = response.body().getCountAppoint();
            }

            @Override
            public void onFailure(Call<CountAppointModel> call, Throwable t) {

            }
        });
    }
}
