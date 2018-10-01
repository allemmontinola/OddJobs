package com.example.allem.revised_capstone.User;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.allem.revised_capstone.API.ServiceApi;
import com.example.allem.revised_capstone.Adapters.TagsAdapter;
import com.example.allem.revised_capstone.Constants;
import com.example.allem.revised_capstone.Data.TagData;
import com.example.allem.revised_capstone.Login;
import com.example.allem.revised_capstone.MapsActivity_View;
import com.example.allem.revised_capstone.Model.GetLatLongModel;
import com.example.allem.revised_capstone.Model.SuccessModel;
import com.example.allem.revised_capstone.Model.TagModel;
import com.example.allem.revised_capstone.Model.UserHomeAcceptedModel;
import com.example.allem.revised_capstone.Model.UserHomeModel;
import com.example.allem.revised_capstone.R;
import com.google.gson.Gson;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.TextInsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;
import com.nightonke.boommenu.Util;
import com.stepstone.apprating.AppRatingDialog;
import com.stepstone.apprating.listener.RatingDialogListener;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.support.constraint.Constraints.TAG;


public class FragmentHomeUser extends Fragment implements RatingDialogListener {
    View view;
    BoomMenuButton boomMenuButton;
    TextView tvNoPost;

    RecyclerView myRecycler, myRecyclerUpdate;
    public static ArrayList<TagModel> tagLists;
    private TagsAdapter adapter;

    TextView tvTitle, tvDesc, tvTags, tvReward, tvAccepted, tvVerify;
    Button btnViewMap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home_user, container, false);
        boomMenuButton = view.findViewById(R.id.home_user_boom_menu);
        tvNoPost = view.findViewById(R.id.home_user_tv_no_job_posted);
        Objects.requireNonNull(getActivity()).setTitle("Home");
        BoomMenu();

        tvTitle = view.findViewById(R.id.home_user_title_tv);
        tvDesc = view.findViewById(R.id.home_user_desc_tv);
        tvTags = view.findViewById(R.id.home_user_tag_tv);
        tvReward = view.findViewById(R.id.home_user_reward_tv);
        tvAccepted = view.findViewById(R.id.home_user_accepted_tv);

        rl = view.findViewById(R.id.home_user_hide_show_post);
        tvVerify = view.findViewById(R.id.home_user_verify);
        btnViewMap = view.findViewById(R.id.home_user_viewMap_btn);

        loadData();
        return view;

    }

    private void BoomMenu() {
        boomMenuButton.setButtonEnum(ButtonEnum.TextInsideCircle);
        boomMenuButton.setPiecePlaceEnum(PiecePlaceEnum.DOT_3_1);
        boomMenuButton.setButtonPlaceEnum(ButtonPlaceEnum.Horizontal);

        TextInsideCircleButton.Builder builder = new TextInsideCircleButton.Builder()
                .isRound(false)
                .shadowCornerRadius(Util.dp2px(10))
                .buttonCornerRadius(Util.dp2px(10))
                .textPadding(new Rect(3, 3, 3, 3))
                .imageRect(new Rect(Util.dp2px(10), Util.dp2px(10), Util.dp2px(70), Util.dp2px(70)))
                .imagePadding(new Rect(0, 0, 0, 0))
                .normalImageRes(R.drawable.ic_write_post)
                .normalTextRes(R.string.write)
                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        Post();
                    }
                });
        TextInsideCircleButton.Builder builder1 = new TextInsideCircleButton.Builder()
                .isRound(false)
                .shadowCornerRadius(Util.dp2px(10))
                .buttonCornerRadius(Util.dp2px(10))
                .textPadding(new Rect(3, 3, 3, 3))
                .imageRect(new Rect(Util.dp2px(10), Util.dp2px(10), Util.dp2px(70), Util.dp2px(70)))
                .imagePadding(new Rect(0, 0, 0, 0))
                .normalImageRes(R.drawable.ic_cancel_post)
                .normalTextRes(R.string.cancel)
                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        Delete();
                    }
                });
        TextInsideCircleButton.Builder builder2 = new TextInsideCircleButton.Builder()
                .isRound(false)
                .shadowCornerRadius(Util.dp2px(10))
                .buttonCornerRadius(Util.dp2px(10))
                .textPadding(new Rect(3, 3, 3, 3))
                .imageRect(new Rect(Util.dp2px(10), Util.dp2px(10), Util.dp2px(70), Util.dp2px(70)))
                .imagePadding(new Rect(0, 0, 0, 0))
                .normalImageRes(R.drawable.ic_edit)
                .normalTextRes(R.string.update)
                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        Update();
                    }
                });
        boomMenuButton.addBuilder(builder);
        boomMenuButton.addBuilder(builder1);
        boomMenuButton.addBuilder(builder2);

    }

    EditText tvUpdateTitle, tvUpdateDesc, tvUpdateTagOne, tvUpdateTagTwo, tvUpdateRewardOne, tvUpdateRewardTwo;
    String tagsUpdate, rewardsUpdate, titleUpdate, descUpdate;
    FloatingActionButton btnUpdate;

    private void Update() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.home_user_update_pop, null);
        dialogBuilder.setView(dialogView);

        tvUpdateTitle = dialogView.findViewById(R.id.home_user_update_title);
        tvUpdateDesc = dialogView.findViewById(R.id.home_user_update_desc);
        tvUpdateTagOne = dialogView.findViewById(R.id.home_user_update_tagOne);
        tvUpdateTagTwo = dialogView.findViewById(R.id.home_user_update_tagTwo);
        tvUpdateRewardOne = dialogView.findViewById(R.id.home_user_update_range1);
        tvUpdateRewardTwo = dialogView.findViewById(R.id.home_user_update_range2);

        myRecyclerUpdate = dialogView.findViewById(R.id.home_user_update_tag_list);

        btnUpdate = dialogView.findViewById(R.id.home_user_update_btn);

        Retrofit retro = new Retrofit.Builder()
                .baseUrl(Constants.BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ServiceApi serv = retro.create(ServiceApi.class);
        Call<TagData> tagModelCall = serv.spinner();
        tagModelCall.enqueue(new Callback<TagData>() {
            @Override
            public void onResponse(Call<TagData> call, final Response<TagData> response) {
                tagLists = new ArrayList<>();
                tagLists = response.body().getTags();
                layoutManager_pop = new LinearLayoutManager(getActivity());
                adapter = new TagsAdapter(getActivity(), tagLists);

                myRecyclerUpdate.setLayoutManager(layoutManager_pop);
                myRecyclerUpdate.setHasFixedSize(true);
                myRecyclerUpdate.setAdapter(adapter);
                Toast.makeText(getActivity(), "One tap for the first tag, Long Tap for the second tag", Toast.LENGTH_LONG).show();
                myRecyclerUpdate.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), myRecyclerUpdate, new ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        tvUpdateTagOne.setText(tagLists.get(position).getTags());
                    }

                    @Override
                    public void onLongClick(View view, int position) {
                        tvUpdateTagTwo.setText(tagLists.get(position).getTags());
                    }
                }));
            }

            @Override
            public void onFailure(Call<TagData> call, Throwable t) {

            }
        });

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        tagsUpdate = tvUpdateTagOne.getText().toString() + ", " + tvUpdateTagTwo.getText().toString();
        rewardsUpdate = tvUpdateRewardOne.getText().toString() + "-" + tvUpdateRewardTwo.getText().toString();
        titleUpdate = tvUpdateTitle.getText().toString();
        descUpdate = tvUpdateDesc.getText().toString();


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Constants.BASEURL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<SuccessModel> call = serviceApi.user_home_update(titleUpdate, descUpdate, tagsUpdate, rewardsUpdate, Login.email);
                call.enqueue(new Callback<SuccessModel>() {
                    @Override
                    public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                        if (response.body().getSuccess() == 0) {
                            Toast.makeText(getActivity(), "You cannot edit the Job you posted", Toast.LENGTH_SHORT).show();
                            alertDialog.dismiss();
                        } else if (response.body().getSuccess() == 1) {
                            Toast.makeText(getActivity(), "Job Posted edited", Toast.LENGTH_SHORT).show();
                            alertDialog.dismiss();
                            loadData();
                        }
                    }

                    @Override
                    public void onFailure(Call<SuccessModel> call, Throwable t) {

                    }
                });
            }
        });
    }

    TextView cancelPost;

    private void Delete() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.home_user_delete_pop, null);
        dialogBuilder.setView(dialogView);

        cancelPost = dialogView.findViewById(R.id.home_user_tv_cancel);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
        cancelPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Constants.BASEURL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<SuccessModel> call = serviceApi.user_home_cancel(Login.email);
                call.enqueue(new Callback<SuccessModel>() {
                    @Override
                    public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                        if (response.body().getSuccess() == 0) {
                            Toast.makeText(getActivity(), "Post Deleted", Toast.LENGTH_SHORT).show();
                            alertDialog.dismiss();
                            loadData();
                        } else if (response.body().getSuccess() == 1) {
                            Toast.makeText(getActivity(), "Cannot be deleted due to work in progress.", Toast.LENGTH_SHORT).show();
                            alertDialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<SuccessModel> call, Throwable t) {

                    }
                });
            }
        });
    }


    EditText title, description, tagOne, tagTwo, priceRange1, priceRange2;
    FloatingActionButton btnPost;
    RecyclerView.LayoutManager layoutManager_pop;

    private void Post() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.fragment_home_user_pop_post, null);
        dialogBuilder.setView(dialogView);
        title = dialogView.findViewById(R.id.home_user_pop_title);
        description = dialogView.findViewById(R.id.home_user_pop_desc);
        tagOne = dialogView.findViewById(R.id.home_user_pop_tagOne);
        tagTwo = dialogView.findViewById(R.id.home_user_pop_tagTwo);
        priceRange1 = dialogView.findViewById(R.id.home_user_pop_reward_range1);
        priceRange2 = dialogView.findViewById(R.id.home_user_pop_reward_range2);


        btnPost = dialogView.findViewById(R.id.home_user_pop_postBtn);

        myRecycler = dialogView.findViewById(R.id.home_user_tag_list);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
        Retrofit retro = new Retrofit.Builder()
                .baseUrl(Constants.BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ServiceApi serv = retro.create(ServiceApi.class);
        Call<TagData> tagModelCall = serv.spinner();
        tagModelCall.enqueue(new Callback<TagData>() {
            @Override
            public void onResponse(Call<TagData> call, final Response<TagData> response) {
                tagLists = new ArrayList<>();
                tagLists = response.body().getTags();
                layoutManager_pop = new LinearLayoutManager(getActivity());
                adapter = new TagsAdapter(getActivity(), tagLists);

                myRecycler.setLayoutManager(layoutManager_pop);
                myRecycler.setHasFixedSize(true);
                myRecycler.setAdapter(adapter);
                Toast.makeText(getActivity(), "One tap for the first tag, Long Tap for the second tag", Toast.LENGTH_LONG).show();
                myRecycler.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), myRecycler, new ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        tagOne.setText(tagLists.get(position).getTags());
                    }

                    @Override
                    public void onLongClick(View view, int position) {
                        tagTwo.setText(tagLists.get(position).getTags());
                    }
                }));
            }

            @Override
            public void onFailure(Call<TagData> call, Throwable t) {

            }
        });

        //POST btn
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tags_comma = tagOne.getText().toString() + ", " + tagTwo.getText().toString();
                String reward_range = priceRange1.getText().toString() + " - " + priceRange2.getText().toString();
                String str_title = title.getText().toString();
                String str_desc = description.getText().toString();
                Retrofit retro = new Retrofit.Builder()
                        .baseUrl(Constants.BASEURL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                ServiceApi serv = retro.create(ServiceApi.class);
                Call<SuccessModel> successModelCall = serv.user_home_insert(str_title, str_desc, tags_comma, reward_range, Login.email);
                successModelCall.enqueue(new Callback<SuccessModel>() {
                    @Override
                    public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                        if (response.body().getSuccess() == 1) {
                            Toast.makeText(getActivity(), "Successfully POSTED", Toast.LENGTH_SHORT).show();
                            alertDialog.dismiss();
                            loadData();
                        } else if (response.body().getSuccess() == 3) {
                            Toast.makeText(getActivity(), "You have already posted", Toast.LENGTH_SHORT).show();
                            alertDialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<SuccessModel> call, Throwable t) {

                    }
                });

            }
        });
    }

    RelativeLayout rl;

    private void loadPost() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ServiceApi serviceApi = retrofit.create(ServiceApi.class);
        Call<UserHomeModel> call = serviceApi.show_home_post(Login.email);
        call.enqueue(new Callback<UserHomeModel>() {
            @Override
            public void onResponse(Call<UserHomeModel> call, final Response<UserHomeModel> response) {
                if (response.body().getSuccess() == 1) {
                    rl.setVisibility(View.VISIBLE);
                    tvNoPost.setVisibility(View.GONE);
                    //Show
                    tvTitle.setText(response.body().getTitle());
                    tvDesc.setText(response.body().getDescription());
                    tvReward.setText(response.body().getReward());
                    tvTags.setText(response.body().getTags());
                    showAccepted();
                    btnViewMap.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Retrofit retro = new Retrofit.Builder()
                                    .baseUrl(Constants.BASEURL)
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();
                            ServiceApi service = retro.create(ServiceApi.class);
                            Call<GetLatLongModel> call = service.getlatLong_email_user(acceptedBy_user);
                            call.enqueue(new Callback<GetLatLongModel>() {
                                @Override
                                public void onResponse(Call<GetLatLongModel> call, Response<GetLatLongModel> response) {
                                    MapsActivity_View.lat = Double.parseDouble(response.body().getLatitude());
                                    MapsActivity_View.longti = Double.parseDouble(response.body().getLongtitude());
                                    Intent intent = new Intent(getContext(), MapsActivity_View.class);
                                    startActivity(intent);
                                }

                                @Override
                                public void onFailure(Call<GetLatLongModel> call, Throwable t) {

                                }
                            });
                        }
                    });

                    tvVerify.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new AppRatingDialog.Builder()
                                    .setPositiveButtonText("Submit")
                                    .setNegativeButtonText("Cancel")
                                    .setNeutralButtonText("Later")
                                    .setNoteDescriptions(Arrays.asList("Very Bad", "Not good", "Quite ok", "Very Good", "Excellent !!!"))
                                    .setDefaultRating(5)
                                    .setTitle("Rate his/her Job")
                                    .setDescription("Please select some stars and give your feedback")
                                    .setDefaultComment("He/She works neatly")
                                    .setStarColor(R.color.colorAccent)
                                    .setNoteDescriptionTextColor(R.color.black)
                                    /*.setTitleTextColor(R.color.black)
                                    .setDescriptionTextColor(R.color.black)
                                    .setHint("Please write your comment here ...")
                                    .setHintTextColor(R.color.gray)
                                    .setCommentTextColor(R.color.black)
                                    .setCommentBackgroundColor(R.color.white)
                                    */.setWindowAnimation(R.style.MyDialogFadeAnimation)
                                    .create(getActivity())
                                    .show();
                        }
                    });

                } else if (response.body().getSuccess() == 0) {
                    rl.setVisibility(View.GONE);
                    tvNoPost.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onFailure(Call<UserHomeModel> call, Throwable t) {

            }
        });
    }

    private void loadData() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();


        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        loadPost();
                        insertLatLong_Client();
                        progressDialog.dismiss();
                        //latLong();

                    }
                }, 3000);
    }

    String acceptedBy_user;
    String id_user;
    private void showAccepted() {
        Retrofit retro = new Retrofit.Builder()
                .baseUrl(Constants.BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ServiceApi serviceApi = retro.create(ServiceApi.class);
        Call<UserHomeAcceptedModel> call = serviceApi.acceptedBy(Login.email);
        call.enqueue(new Callback<UserHomeAcceptedModel>() {
            @Override
            public void onResponse(Call<UserHomeAcceptedModel> call, Response<UserHomeAcceptedModel> response) {
                tvAccepted.setText(response.body().getAcceptedBy());
                acceptedBy_user = response.body().getAcceptedBy();
                id_user = response.body().getId();
            }

            @Override
            public void onFailure(Call<UserHomeAcceptedModel> call, Throwable t) {

            }
        });
    }

    private void insertLatLong_Client() {
        Retrofit retro = new Retrofit.Builder()
                .baseUrl(Constants.BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ServiceApi serv = retro.create(ServiceApi.class);
        Call<SuccessModel> call = serv.user_latlong(Login.id, Login.latitude, Login.longtitude);
        call.enqueue(new Callback<SuccessModel>() {
            @Override
            public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                Toast.makeText(getActivity(), "Latitude: " + Login.latitude + "Longtitude: " + Login.latitude, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<SuccessModel> call, Throwable t) {

            }
        });
    }

    @Override
    public void onNegativeButtonClicked() {

    }

    @Override
    public void onNeutralButtonClicked() {

    }

    @Override
    public void onPositiveButtonClicked(int i, String s) {

        /*Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ServiceApi service = retrofit.create(ServiceApi.class);
        Call<SuccessModel> call = service.user_rate(id_user, i);*/
        Toast.makeText(getContext(), i + " " + s, Toast.LENGTH_SHORT).show();
    }

    private interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private FragmentHomeUser.ClickListener clickListener;

        RecyclerTouchListener(Context context, final RecyclerView recyclerView, final FragmentHomeUser.ClickListener clickListener) {
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


