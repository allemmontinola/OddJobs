package com.example.allem.revised_capstone.User;


import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.SearchView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.allem.revised_capstone.API.ServiceApi;
import com.example.allem.revised_capstone.Adapters.User_AppointAdapter;
import com.example.allem.revised_capstone.Constants;
import com.example.allem.revised_capstone.Data.User_AppointData;
import com.example.allem.revised_capstone.Login;
import com.example.allem.revised_capstone.Model.SuccessModel;
import com.example.allem.revised_capstone.Model.User_AppointModel;
import com.example.allem.revised_capstone.R;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class FragmentAppointUser extends Fragment implements SearchView.OnQueryTextListener {
    View view;
    public static ArrayList<User_AppointModel> user_appointList;
    RecyclerView myRecycler;
    RecyclerView.LayoutManager layoutManager;
    private User_AppointAdapter adapter;
    String fullName = Login.fName + " " + Login.mInitial + " " + Login.lName;

    private void MyViews(View view) {
        SearchView search = view.findViewById(R.id.user_appoint_search);
        search.setOnQueryTextListener(this);
        myRecycler = view.findViewById(R.id.user_appoint_lists);
        AppointUsers();
    }

    CalendarView calendarView;
    TimePicker timePicker;
    Button btnAppointTask;
    String emailTo, emailFrom, timeFormatted;
    static String date;
    private void AppointUsers() {
        Retrofit retro = new Retrofit.Builder()
                .baseUrl(Constants.BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ServiceApi serviceApi = retro.create(ServiceApi.class);
        Call<User_AppointData> call = serviceApi.show_users();
        call.enqueue(new Callback<User_AppointData>() {
            @Override
            public void onResponse(Call<User_AppointData> call, Response<User_AppointData> response) {
                user_appointList = new ArrayList<>();
                user_appointList = response.body().getAppoint();
                layoutManager = new LinearLayoutManager(getActivity());
                adapter = new User_AppointAdapter(getActivity(), user_appointList);

                myRecycler.setLayoutManager(layoutManager);
                myRecycler.setHasFixedSize(true);
                myRecycler.setAdapter(adapter);
                myRecycler.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), myRecycler, new ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        Toast.makeText(getActivity(), user_appointList.get(position).getEmail(), Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
                        LayoutInflater inflater = getLayoutInflater();
                        View dialogView = inflater.inflate(R.layout.appoint_user_pop_calendar, null);
                        dialogBuilder.setView(dialogView);

                        calendarView = dialogView.findViewById(R.id.appoint_user_calendar);
                        timePicker = dialogView.findViewById(R.id.appoint_user_timer);
                        btnAppointTask = dialogView.findViewById(R.id.appoint_user_btnAppoint);

                        emailTo = user_appointList.get(position).getEmail();
                        emailFrom = Login.email;
                        //DATE
                        calendarView.setMinDate(calendarView.getDate());
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd, yyyy");
                        date = simpleDateFormat.format(new Date(calendarView.getDate()));
                        //TIME
                        Time time = new Time(timePicker.getCurrentHour(),timePicker.getCurrentMinute(),0);
                        simpleDateFormat = new SimpleDateFormat("h:mm a");
                        timeFormatted = simpleDateFormat.format(time);


                        final AlertDialog alertDialog = dialogBuilder.create();
                        alertDialog.show();
                        btnAppointTask.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Retrofit retrofit = new Retrofit.Builder()
                                        .baseUrl(Constants.BASEURL)
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .build();
                                ServiceApi serv = retrofit.create(ServiceApi.class);
                                Call<SuccessModel> success = serv.appoint_insert(timeFormatted, date, emailTo, emailFrom, fullName);
                                success.enqueue(new Callback<SuccessModel>() {
                                    @Override
                                    public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                                        Toast.makeText(getActivity(), "Appointment Successful", Toast.LENGTH_SHORT).show();
                                        alertDialog.dismiss();
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
            public void onFailure(Call<User_AppointData> call, Throwable t) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_appoint_user, container, false);
        MyViews(view);
        return view;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.filter(newText);
        return false;
    }

    private interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private FragmentAppointUser.ClickListener clickListener;

        RecyclerTouchListener(Context context, final RecyclerView recyclerView, final FragmentAppointUser.ClickListener clickListener) {
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
}
