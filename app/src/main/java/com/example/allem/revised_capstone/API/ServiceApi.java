package com.example.allem.revised_capstone.API;

import com.example.allem.revised_capstone.Data.ServiceAppointData;
import com.example.allem.revised_capstone.Data.ServiceHomeData;
import com.example.allem.revised_capstone.Data.TagData;
import com.example.allem.revised_capstone.Data.User_AppointData;
import com.example.allem.revised_capstone.Data.User_AppointData_List;
import com.example.allem.revised_capstone.Model.CountAppointModel;
import com.example.allem.revised_capstone.Model.GetLatLongModel;
import com.example.allem.revised_capstone.Model.ServiceHomeAcceptModel;
import com.example.allem.revised_capstone.Model.ServiceLoginAutoModel;
import com.example.allem.revised_capstone.Model.ServiceLoginModel;
import com.example.allem.revised_capstone.Model.SuccessModel;
import com.example.allem.revised_capstone.Model.TagModel;
import com.example.allem.revised_capstone.Model.UserHomeAcceptedModel;
import com.example.allem.revised_capstone.Model.UserHomeModel;
import com.example.allem.revised_capstone.Model.UserLoginAutoModel;
import com.example.allem.revised_capstone.Model.UserLoginModel;
import com.example.allem.revised_capstone.Model.User_AppointModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ServiceApi {
    @FormUrlEncoded
    @POST("revised/service_login.php")
    Call<ServiceLoginModel> serviceLogin(@Field("username") String username,
                                         @Field("password") String password);

    @FormUrlEncoded
    @POST("revised/user_login.php")
    Call<UserLoginModel> userLogin(@Field("username") String username,
                                   @Field("password") String password);

    @FormUrlEncoded
    @POST("revised/user_appoint_insert.php")
    Call<SuccessModel> appoint_insert(@Field("time") String time,
                                      @Field("date") String date,
                                      @Field("appointedTo") String appointedTo,
                                      @Field("appointedFrom") String appointedFrom,
                                      @Field("appointedFullName") String fullName);

    @FormUrlEncoded
    @POST("revised/user_appoint_cancel.php")
    Call<SuccessModel> appoint_cancel(@Field("id") String id);

    @FormUrlEncoded
    @POST("revised/user_home_insert.php")
    Call<SuccessModel> user_home_insert (@Field("title") String title,
                                         @Field("description") String desc,
                                         @Field("tags") String tags,
                                         @Field("reward") String reward,
                                         @Field("postedBy") String postedBy);

    @FormUrlEncoded
    @POST("revised/user_home_update.php")
    Call<SuccessModel> user_home_update (@Field("title") String title,
                                         @Field("description") String desc,
                                         @Field("tags") String tags,
                                         @Field("reward") String reward,
                                         @Field("postedBy") String postedBy);

    @FormUrlEncoded
    @POST("revised/service_home_acceptJob")
    Call<SuccessModel> service_home_acceptJob(@Field("title") String title,
                                              @Field("description") String desc,
                                              @Field("reward") String reward,
                                              @Field("postedBy") String postedBy,
                                              @Field("acceptedBy") String accept);

    @FormUrlEncoded
    @POST("revised/service_appoint_cancel")
    Call<SuccessModel> service_appoint_cancel(@Field("id") String id);

    @FormUrlEncoded
    @POST("revised/user_latlong.php")
    Call<SuccessModel> user_latlong (@Field("id") String id,
                                     @Field("latitude") String latitude,
                                     @Field("longtitude") String longtitude);

    @FormUrlEncoded
    @POST("revised/service_latlong.php")
    Call<SuccessModel> serv_latlong (@Field("id") String id,
                                     @Field("latitude") String latitude,
                                     @Field("longtitude") String longtitude);

    @FormUrlEncoded
    @POST("revised/user_home_rating.php")
    Call<SuccessModel> user_rate (@Field("id") String id,
                                  @Field("ratings") String rate);

    @FormUrlEncoded
    @POST("revised/register.php")
    Call<SuccessModel> register (@Field("firstname") String first,
                                 @Field("lastname") String last,
                                 @Field("midInitial") String mid,
                                 @Field("age") String age,
                                 @Field("sex") String sex,
                                 @Field("contact") String contact,
                                 @Field("address") String address,
                                 @Field("email") String email,
                                 @Field("username") String username,
                                 @Field("password") String password);

    @FormUrlEncoded
    @POST("revised/user_login_auto.php")
    Call<UserLoginAutoModel> user_auto(@Field("id") String id);

    @FormUrlEncoded
    @POST("revised/service_login.auto.php")
    Call<ServiceLoginAutoModel> service_auto(@Field("id") String id);

    @GET("revised/user_spinner.php")
    Call<TagData> spinner();

    @GET("revised/user_show_appoint.php")
    Call<User_AppointData> show_users();

    @GET("revised/user_appoint_list.php")
    Call<User_AppointData_List> show_appointList(@Query("appointedFrom") String appointedFrom);

    @GET("revised/user_home_show.php")
    Call<UserHomeModel> show_home_post (@Query("postedBy") String postedBy);

    @GET("revised/user_home_acceptedBy.php")
    Call<UserHomeAcceptedModel> acceptedBy (@Query("postedBy") String postedBy);

    @GET("revised/user_home_cancel.php")
    Call<SuccessModel> user_home_cancel (@Query("postedBy") String postedBy);

    @GET("revised/service_home_show.php")
    Call<ServiceHomeData> service_show_home();

    @GET("revised/service_home_acceptJob_show.php")
    Call<ServiceHomeAcceptModel> service_home_accept_show(@Query("acceptedBy") String accepted);

    @GET("revised/service_home_acceptJob_check.php")
    Call<SuccessModel> service_home_accept_check(@Query("acceptedBy") String accepted);

    @GET("revised/service_appoint_show.php")
    Call<ServiceAppointData> service_appoint_show(@Query("appointedTo") String appointedTo);

    @GET("revised/service_appoint_count.php")
    Call<CountAppointModel> count_serv (@Query("appointedTo") String appointedTo);

    @GET("revised/service_mapView_to_user.php")
    Call<GetLatLongModel> getlatLong_email (@Query("email") String email);

    @GET("revised/user_mapView_to_service.php")
    Call<GetLatLongModel> getlatLong_email_user (@Query("email") String email);

    @GET("revised/register_check_email.php")
    Call<SuccessModel> check_email(@Query("email") String email);

    @GET("revised/register_check_user.php")
    Call<SuccessModel> check_user (@Query("username") String username);
}
