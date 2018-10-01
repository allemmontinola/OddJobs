package com.example.allem.revised_capstone;

import android.app.Service;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.allem.revised_capstone.API.ServiceApi;
import com.example.allem.revised_capstone.Model.SuccessModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Register_Client extends AppCompatActivity {
    EditText etFirst, etLast, etMid, etEmail, etContact, etAddress, etAge, etUser, etPass, etRePass, etSex;
    Button btnReg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register__client);

        etFirst = findViewById(R.id.register_first);
        etLast = findViewById(R.id.register_Last);
        etMid = findViewById(R.id.register_Initial);
        etEmail = findViewById(R.id.register_email);
        etContact = findViewById(R.id.register_contact);
        etAddress =findViewById(R.id.register_address);
        etAge = findViewById(R.id.register_age);
        etUser = findViewById(R.id.register_username);
        etPass = findViewById(R.id.register_password1);
        etRePass = findViewById(R.id.register_password2);
        etSex = findViewById(R.id.register_gender);

        btnReg = findViewById(R.id.btnRegister);
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retro = new Retrofit.Builder()
                        .baseUrl(Constants.BASEURL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                ServiceApi service = retro.create(ServiceApi.class);
                Call<SuccessModel> call = service.check_email(etEmail.getText().toString());
                call.enqueue(new Callback<SuccessModel>() {
                    @Override
                    public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                        if(response.body().getSuccess() == 1){
                            Toast.makeText(Register_Client.this, "Email has been already taken.", Toast.LENGTH_SHORT).show();
                            etEmail.isFocused();
                        }
                        else if(response.body().getSuccess() == 0) {
                            if(!etPass.getText().toString().equals(etRePass.getText().toString())){
                                Toast.makeText(Register_Client.this, "Password are not matched", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Retrofit retrofit = new Retrofit.Builder()
                                        .baseUrl(Constants.BASEURL)
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .build();
                                ServiceApi service = retrofit.create(ServiceApi.class);
                                Call<SuccessModel> callBack = service.check_user(etUser.getText().toString());
                                callBack.enqueue(new Callback<SuccessModel>() {
                                    @Override
                                    public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                                        if(response.body().getSuccess() == 1){
                                            Toast.makeText(Register_Client.this, "Email has been already taken.", Toast.LENGTH_SHORT).show();
                                            etUser.isFocused();
                                        }
                                        else{
                                            InsertReg();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<SuccessModel> call, Throwable t) {

                                    }
                                });
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<SuccessModel> call, Throwable t) {

                    }
                });
            }
        });
    }
    private void InsertReg(){
        String first = etFirst.getText().toString();
        String last = etLast.getText().toString();
        String mInitial = etMid.getText().toString();
        String age = etAge.getText().toString();
        String sex = etSex.getText().toString();
        String email = etEmail.getText().toString();
        String address = etAddress.getText().toString();
        String contact = etContact.getText().toString();
        String username = etUser.getText().toString();
        String password = etPass.getText().toString();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ServiceApi serviceApi = retrofit.create(ServiceApi.class);
        Call<SuccessModel> call = serviceApi.register(first, last, mInitial, age, sex, contact, address, email, username,password);
        call.enqueue(new Callback<SuccessModel>() {
            @Override
            public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                if(response.body().getSuccess() == 1){
                    Toast.makeText(Register_Client.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Register_Client.this, Login.class);
                    startActivity(i);
                    finish();
                }

            }

            @Override
            public void onFailure(Call<SuccessModel> call, Throwable t) {

            }
        });
    }
}
