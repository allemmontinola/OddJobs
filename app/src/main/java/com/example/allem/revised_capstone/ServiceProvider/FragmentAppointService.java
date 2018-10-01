package com.example.allem.revised_capstone.ServiceProvider;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.allem.revised_capstone.API.ServiceApi;
import com.example.allem.revised_capstone.Adapters.ServiceAppointAdapter;
import com.example.allem.revised_capstone.Constants;
import com.example.allem.revised_capstone.Data.ServiceAppointData;
import com.example.allem.revised_capstone.Login;
import com.example.allem.revised_capstone.MapsActivity_View;
import com.example.allem.revised_capstone.Model.GetLatLongModel;
import com.example.allem.revised_capstone.Model.ServiceAppointModel;
import com.example.allem.revised_capstone.Model.SuccessModel;
import com.example.allem.revised_capstone.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class FragmentAppointService extends Fragment {
    View view;
    public static ArrayList<ServiceAppointModel> service_appoint_List;
    RecyclerView myRecycler;
    RecyclerView.LayoutManager layoutManager;
    private ServiceAppointAdapter adapter;

    private void MyViews(View view) {
        myRecycler = view.findViewById(R.id.service_appoint_lists_data);
        showAppointments();
    }

    TextView tvCancelApp, tvShowApp;
    String cancelID;
    public static String email, fullName;


    private void showAppointments() {
        try {
            Retrofit retro = new Retrofit.Builder()
                    .baseUrl(Constants.BASEURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            final ServiceApi service = retro.create(ServiceApi.class);
            Call<ServiceAppointData> call = service.service_appoint_show(Login.email);
            call.enqueue(new Callback<ServiceAppointData>() {
                @Override
                public void onResponse(Call<ServiceAppointData> call, final Response<ServiceAppointData> response) {
                    service_appoint_List = new ArrayList<>();
                    service_appoint_List = response.body().getAppointList();
                    layoutManager = new LinearLayoutManager(getActivity());
                    adapter = new ServiceAppointAdapter(getActivity(), service_appoint_List);

                    myRecycler.setLayoutManager(layoutManager);
                    myRecycler.setHasFixedSize(true);
                    myRecycler.setAdapter(adapter);
                    myRecycler.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), myRecycler, new ClickListener() {
                        @Override
                        public void onClick(View view, int position) {
                            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
                            LayoutInflater inflater = getLayoutInflater();
                            View dialogView = inflater.inflate(R.layout.service_appoint_pop, null);
                            dialogBuilder.setView(dialogView);

                            tvCancelApp = dialogView.findViewById(R.id.appoint_service_cancel_appointment);
                            tvShowApp = dialogView.findViewById(R.id.appoint_service_view_profile);

                            email = service_appoint_List.get(position).getAppointedFrom();

                            final AlertDialog alertDialog = dialogBuilder.create();
                            alertDialog.show();

                            cancelID = service_appoint_List.get(position).getId();
                            Toast.makeText(getContext(), cancelID, Toast.LENGTH_SHORT).show();

                            tvCancelApp.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Retrofit retrofit = new Retrofit.Builder()
                                            .baseUrl(Constants.BASEURL)
                                            .addConverterFactory(GsonConverterFactory.create())
                                            .build();
                                    ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                                    Call<SuccessModel> modCall = serviceApi.service_appoint_cancel(cancelID);
                                    modCall.enqueue(new Callback<SuccessModel>() {
                                        @Override
                                        public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                                            if (response.body().getSuccess() == 1) {
                                                Toast.makeText(getActivity(), "Appointment cancelled", Toast.LENGTH_SHORT).show();
                                                alertDialog.dismiss();
                                            } else if (response.body().getSuccess() == 0) {
                                                alertDialog.dismiss();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<SuccessModel> call, Throwable t) {

                                        }
                                    });

                                }
                            });
                            tvShowApp.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {


                                    Retrofit retrofit = new Retrofit.Builder()
                                            .baseUrl(Constants.BASEURL)
                                            .addConverterFactory(GsonConverterFactory.create())
                                            .build();
                                    ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                                    Call<GetLatLongModel> call = serviceApi.getlatLong_email(email);
                                    call.enqueue(new Callback<GetLatLongModel>() {
                                        @Override
                                        public void onResponse(Call<GetLatLongModel> call, Response<GetLatLongModel> response) {
                                            fullName = response.body().getFirst() + " " + response.body().getLast();
                                            MapsActivity_View.lat = Double.parseDouble(response.body().getLatitude());
                                            MapsActivity_View.longti = Double.parseDouble(response.body().getLongtitude());


                                        }

                                        @Override
                                        public void onFailure(Call<GetLatLongModel> call, Throwable t) {

                                        }
                                    });
                                    alertDialog.dismiss();
                                    Intent i = new Intent(getContext(), MapsActivity_View.class);
                                    startActivity(i);
                                }

                            });

                        }

                        @Override
                        public void onLongClick(View view, int position) {

                        }
                    }));
                }

                @Override
                public void onFailure(Call<ServiceAppointData> call, Throwable t) {

                }
            });
        }
        catch (Exception e){
            e.getMessage();
        }
    }

    private interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private FragmentAppointService.ClickListener clickListener;

        RecyclerTouchListener(Context context, final RecyclerView recyclerView, final FragmentAppointService.ClickListener clickListener) {
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

    TextView tvNoAppoint;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_appoint_service, container, false);
        tvNoAppoint = view.findViewById(R.id.service_appoint_tv_no_job_posted);
        MyViews(view);
        return view;
    }


}
