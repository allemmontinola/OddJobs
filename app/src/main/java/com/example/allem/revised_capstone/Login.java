package com.example.allem.revised_capstone;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.allem.revised_capstone.API.ServiceApi;
import com.example.allem.revised_capstone.Model.ServiceLoginAutoModel;
import com.example.allem.revised_capstone.Model.ServiceLoginModel;
import com.example.allem.revised_capstone.Model.UserLoginAutoModel;
import com.example.allem.revised_capstone.Model.UserLoginModel;
import com.example.allem.revised_capstone.ServiceProvider.LandingService;
import com.example.allem.revised_capstone.User.LandingUser;

import java.util.Objects;

import br.com.goncalves.pugnotification.notification.PugNotification;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.dimorinny.floatingtextbutton.FloatingTextButton;

public class Login extends AppCompatActivity implements LocationListener {
    public static String user;
    public static String email;
    public static String id;
    public static String fName;
    public static String lName;
    public static String mInitial;
    public static String address;
    public static String contact;

    public static String ratings;
    public static String isLoggedIn;
    public static String isAvailable;

    public static String myID;
    public static String myID_serv;

    private PreferenceUtils sessionManager;
    private PreferenceUtils_Service sessionManager_service;

    EditText etUser, etPass;
    FloatingTextButton btnLogin, btnReg;
    RadioButton rdUser, rdServ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etUser = findViewById(R.id.log_username);
        etPass = findViewById(R.id.log_password);
        rdUser = findViewById(R.id.log_radioUser);
        rdServ = findViewById(R.id.log_radioService);
        CheckPermission();
        rdUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rdServ.setChecked(false);
            }
        });

        rdServ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rdUser.setChecked(false);
            }
        });
        btnLogin = findViewById(R.id.log_btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rdUser.isChecked()) {
                    LoginUser();
                } else if (rdServ.isChecked()) {
                    LoginService();
                } else if (!rdServ.isChecked() && !rdUser.isChecked()) {
                    Toast.makeText(Login.this, "Please select preferred Logged In", Toast.LENGTH_SHORT).show();
                }

            }
        });
        btnReg = findViewById(R.id.log_btnReg);
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, Register_Client.class);
                startActivity(i);
            }
        });

        sessionManager = new PreferenceUtils(Login.this);
        myID = sessionManager.getUserDetail().get("user_id");

        sessionManager_service = new PreferenceUtils_Service(Login.this);
        myID_serv = sessionManager_service.getUserDetail().get("service_id");

        if (myID != null) {
            Toast.makeText(this, "CLIENT", Toast.LENGTH_SHORT).show();
            UserAutoLogin();
        } else if (myID_serv != null) {
            Toast.makeText(this, "SERVICE", Toast.LENGTH_SHORT).show();
           ServiceAutoLogin();
        }

    }

    private void LoginUser() {
        String userName = etUser.getText().toString();
        String passWord = etPass.getText().toString();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ServiceApi api = retrofit.create(ServiceApi.class);
        Call<UserLoginModel> userLoginModelCall = api.userLogin(userName, passWord);
        userLoginModelCall.enqueue(new Callback<UserLoginModel>() {
            @Override
            public void onResponse(Call<UserLoginModel> call, Response<UserLoginModel> response) {
                if (Objects.requireNonNull(response.body()).getSuccess() == 1) {
                    Toast.makeText(Login.this, "Welcome to User side", Toast.LENGTH_SHORT).show();
                    user = response.body().getUser();
                    id = response.body().getId();
                    email = response.body().getEmail();
                    fName = response.body().getFirst();
                    lName = response.body().getLast();
                    mInitial = response.body().getMiddle();
                    address = response.body().getAddress();
                    contact = response.body().getContact();
                    isLoggedIn = response.body().getIsLoggedIn();


                    String user_id = Objects.requireNonNull(response.body()).getId();
                    sessionManager = new PreferenceUtils(getApplicationContext());
                    sessionManager.createLoginSession(user_id);

                    Intent i = new Intent(Login.this, LandingUser.class);
                    startActivity(i);
                    finish();

                } else {
                    Toast.makeText(Login.this, "Wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserLoginModel> call, Throwable t) {

            }
        });
    }

    private void LoginService() {
        String userName = etUser.getText().toString();
        String passWord = etPass.getText().toString();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ServiceApi api = retrofit.create(ServiceApi.class);
        Call<ServiceLoginModel> serviceLoginModelCall = api.serviceLogin(userName, passWord);
        serviceLoginModelCall.enqueue(new Callback<ServiceLoginModel>() {
            @Override
            public void onResponse(Call<ServiceLoginModel> call, Response<ServiceLoginModel> response) {
                if (Objects.requireNonNull(response.body()).getSuccess() == 1) {
                    Toast.makeText(Login.this, "Welcome to Service Provider side", Toast.LENGTH_SHORT).show();
                    user = response.body().getUser();
                    id = response.body().getId();
                    email = response.body().getEmail();
                    fName = response.body().getFirst();
                    lName = response.body().getLast();
                    mInitial = response.body().getMiddle();
                    address = response.body().getAddress();
                    contact = response.body().getContact();
                    isLoggedIn = response.body().getIsLoggedIn();

                    isAvailable = response.body().getIsAvailable();
                    ratings = response.body().getRatings();

                    String user_id = Objects.requireNonNull(response.body()).getId();
                    sessionManager_service = new PreferenceUtils_Service(getApplicationContext());
                    sessionManager_service.createLoginSession(user_id);


                    Intent i = new Intent(Login.this, LandingService.class);
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(Login.this, "Wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ServiceLoginModel> call, Throwable t) {

            }
        });
    }
    private void UserAutoLogin(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ServiceApi serviceApi = retrofit.create(ServiceApi.class);
        Call<UserLoginAutoModel> call = serviceApi.user_auto(myID);
        call.enqueue(new Callback<UserLoginAutoModel>() {
            @Override
            public void onResponse(Call<UserLoginAutoModel> call, Response<UserLoginAutoModel> response) {
                if (Objects.requireNonNull(response.body()).getSuccess() == 1) {
                    Toast.makeText(Login.this, "Welcome to User side", Toast.LENGTH_SHORT).show();
                    user = response.body().getUser();
                    id = response.body().getId();
                    email = response.body().getEmail();
                    fName = response.body().getFirst();
                    lName = response.body().getLast();
                    mInitial = response.body().getMiddle();
                    address = response.body().getAddress();
                    contact = response.body().getContact();
                    isLoggedIn = response.body().getIsLoggedIn().toString();


                    String user_id = Objects.requireNonNull(response.body()).getId();
                    sessionManager = new PreferenceUtils(getApplicationContext());
                    sessionManager.createLoginSession(user_id);

                    Intent i = new Intent(Login.this, LandingUser.class);
                    startActivity(i);
                    finish();

                } else {
                    Toast.makeText(Login.this, "Wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserLoginAutoModel> call, Throwable t) {

            }
        });
    }

    private void ServiceAutoLogin(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ServiceApi serviceApi = retrofit.create(ServiceApi.class);
        Call<ServiceLoginAutoModel> call = serviceApi.service_auto(myID_serv);
        call.enqueue(new Callback<ServiceLoginAutoModel>() {
            @Override
            public void onResponse(Call<ServiceLoginAutoModel> call, Response<ServiceLoginAutoModel> response) {
                if (Objects.requireNonNull(response.body()).getSuccess() == 1) {
                    Toast.makeText(Login.this, "Welcome to Service Provider side", Toast.LENGTH_SHORT).show();
                    user = response.body().getUser();
                    id = response.body().getId();
                    email = response.body().getEmail();
                    fName = response.body().getFirst();
                    lName = response.body().getLast();
                    mInitial = response.body().getMiddle();
                    address = response.body().getAddress();
                    contact = response.body().getContact();
                    isLoggedIn = response.body().getIsLoggedIn().toString();

                    isAvailable = response.body().getIsAvailable();
                    ratings = response.body().getRatings();

                    String user_id = Objects.requireNonNull(response.body()).getId();
                    sessionManager_service = new PreferenceUtils_Service(getApplicationContext());
                    sessionManager_service.createLoginSession(user_id);


                    Intent i = new Intent(Login.this, LandingService.class);
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(Login.this, "Wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ServiceLoginAutoModel> call, Throwable t) {

            }
        });
    }

    LocationManager locationManager;
    public static String latitude;
    public static String longtitude;

    @Override
    public void onLocationChanged(Location location) {
        // Getting reference to TextView tv_longitude
        //tvLongitude = (TextView) findViewById(R.id.tv_longitude);
        // Getting reference to TextView tv_latitude
        //tvLatitude = (TextView) findViewById(R.id.tv_latitude);

        longtitude = String.valueOf(location.getLongitude());
        latitude = String.valueOf(location.getLatitude());


    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(this, "Enabled new provider!" + provider,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(Login.this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getLocation();
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }

    public void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, this);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    public void CheckPermission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
        }
    }


}
