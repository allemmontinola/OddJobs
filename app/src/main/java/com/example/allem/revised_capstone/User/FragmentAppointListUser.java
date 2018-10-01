package com.example.allem.revised_capstone.User;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import com.example.allem.revised_capstone.Adapters.User_AppointAdapter_List;
import com.example.allem.revised_capstone.Constants;
import com.example.allem.revised_capstone.Data.User_AppointData_List;
import com.example.allem.revised_capstone.Login;
import com.example.allem.revised_capstone.Model.SuccessModel;
import com.example.allem.revised_capstone.Model.User_AppointModel_List;
import com.example.allem.revised_capstone.R;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class FragmentAppointListUser extends Fragment {
    View view;
    public static ArrayList<User_AppointModel_List> user_appoint_List;
    RecyclerView myRecycler;
    RecyclerView.LayoutManager layoutManager;
    private User_AppointAdapter_List adapter;

    private void MyViews(View view) {
        myRecycler = view.findViewById(R.id.user_appoint_lists_data);
        AppointLists();
    }

    TextView tvCancel;

    private void AppointLists() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ServiceApi serviceApi = retrofit.create(ServiceApi.class);
        Call<User_AppointData_List> showLists = serviceApi.show_appointList(Login.email);
        showLists.enqueue(new Callback<User_AppointData_List>() {
            @Override
            public void onResponse(Call<User_AppointData_List> call, Response<User_AppointData_List> response) {
                user_appoint_List = new ArrayList<>();
                user_appoint_List = response.body().getAppointList();
                layoutManager = new LinearLayoutManager(getActivity());
                adapter = new User_AppointAdapter_List(getActivity(), user_appoint_List);

                myRecycler.setLayoutManager(layoutManager);
                myRecycler.setHasFixedSize(true);
                myRecycler.setAdapter(adapter);
                myRecycler.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), myRecycler, new ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        Toast.makeText(getActivity(), user_appoint_List.get(position).getAppointedFullName() + user_appoint_List.get(position).getId(), Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onLongClick(View view, final int position) {
                        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
                        LayoutInflater inflater = getLayoutInflater();
                        View dialogView = inflater.inflate(R.layout.appoint_lists_user_pop, null);
                        dialogBuilder.setView(dialogView);

                        tvCancel = dialogView.findViewById(R.id.appoint_tv_cancel);

                        final AlertDialog alertDialog = dialogBuilder.create();
                        alertDialog.show();
                        tvCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Retrofit retros = new Retrofit.Builder()
                                        .baseUrl(Constants.BASEURL)
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .build();
                                ServiceApi servs = retros.create(ServiceApi.class);
                                Call<SuccessModel> cancel = servs.appoint_cancel(user_appoint_List.get(position).getId());
                                cancel.enqueue(new Callback<SuccessModel>() {
                                    @Override
                                    public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                                        Toast.makeText(getActivity(), "Appointment Cancelled", Toast.LENGTH_SHORT).show();
                                        FragmentTransaction fragmentManager = getActivity().getSupportFragmentManager().beginTransaction();
                                        fragmentManager.replace(R.id.fragment_container_user, new FragmentAppointListUser());
                                        fragmentManager.commit();
                                        alertDialog.dismiss();

                                    }

                                    @Override
                                    public void onFailure(Call<SuccessModel> call, Throwable t) {

                                    }
                                });
                            }
                        });
                    }
                }));
            }

            @Override
            public void onFailure(Call<User_AppointData_List> call, Throwable t) {

            }
        });
    }

    private interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private FragmentAppointListUser.ClickListener clickListener;

        RecyclerTouchListener(Context context, final RecyclerView recyclerView, final FragmentAppointListUser.ClickListener clickListener) {
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_appoint_list_user, container, false);
        MyViews(view);
        return view;
    }

}
