package com.example.allem.revised_capstone.User;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import com.example.allem.revised_capstone.API.ServiceApi;
import com.example.allem.revised_capstone.Constants;
import com.example.allem.revised_capstone.Login;
import com.example.allem.revised_capstone.Model.SuccessModel;
import com.example.allem.revised_capstone.PreferenceUtils;
import com.example.allem.revised_capstone.R;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LandingUser extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ImageView image;
    private DrawerLayout mDrawerLayout_user;
    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_user);

        Toolbar toolbar = findViewById(R.id.toolbar_user);
        setSupportActionBar(toolbar);

        mDrawerLayout_user = findViewById(R.id.drawer_layout_user);
        NavigationView navigationView = findViewById(R.id.nav_view_user);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        TextView txUser = headerView.findViewById(R.id.nav_nameUser);
        TextView txEmail = headerView.findViewById(R.id.nav_nameEmail);
        image = headerView.findViewById(R.id.nav_imageUser);
        /*String url_image = Constants.BASEURL + "oddjobs/Upload/User/" + Login.user + ".jpg";
        url_image = url_image.replace(" ", "%20");
        Glide.with(this).load(url_image).asBitmap().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(image);*/

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_user,
                new FragmentHomeUser()).commit();
        navigationView.setCheckedItem(R.id.homeUser);

        if (txUser == null) {
            txUser.setText("NO VALUE");
        } else {
            txUser.setText(Login.user);
            txEmail.setText(Login.email);
        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout_user, toolbar,
                R.string.open, R.string.close);
        mDrawerLayout_user.addDrawerListener(toggle);
        toggle.syncState();

    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout_user.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout_user.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.homeUser:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_user,
                        new FragmentHomeUser()).commit();
                break;

            case R.id.appointUser:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_user,
                        new FragmentAppointUser()).commit();
                break;

            case R.id.appointListUser:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_user,
                        new FragmentAppointListUser()).commit();
                break;

            case R.id.profileUser:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_user,
                        new FragmentProfileUser()).commit();
                break;

            case R.id.logoutUser:
                Logout();
                break;

        }
        mDrawerLayout_user.closeDrawer(GravityCompat.START);
        return true;
    }

    PreferenceUtils sessionManager;

    private void Logout() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setIcon(R.drawable.ic_logout);
        alert.setTitle("LOGOUT");
        alert.setMessage("Are you sure you want to logout?");
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sessionManager = new PreferenceUtils(Objects.requireNonNull(LandingUser.this));
                sessionManager.logout();
                Intent i = new Intent(LandingUser.this, Login.class);
                startActivity(i);
                finishAffinity();
            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.show();
    }

}
