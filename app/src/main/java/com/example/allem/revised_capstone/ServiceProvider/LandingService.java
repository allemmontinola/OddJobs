package com.example.allem.revised_capstone.ServiceProvider;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.allem.revised_capstone.Constants;
import com.example.allem.revised_capstone.Login;
import com.example.allem.revised_capstone.PreferenceUtils;
import com.example.allem.revised_capstone.PreferenceUtils_Service;
import com.example.allem.revised_capstone.R;

import java.util.Objects;

public class LandingService extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ImageView image;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_service);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        TextView txUser = headerView.findViewById(R.id.nav_nameService);
        TextView txEmail = headerView.findViewById(R.id.nav_emailService);
        image = headerView.findViewById(R.id.nav_imageService);
        /*String url_image = Constants.BASEURL+"oddjobs/Upload/User/"+ Login.user+".jpg";
        url_image = url_image.replace(" ","%20");
        Glide.with(this).load(url_image).asBitmap().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(image);*/


        txUser.setText(Login.user);
        txEmail.setText(Login.email);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new FragmentHomeService()).commit();
        navigationView.setCheckedItem(R.id.home);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar,
                R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FragmentHomeService()).commit();

                break;
            case R.id.profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FragmentProfileService()).commit();
                break;
            case R.id.appointment:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FragmentAppointService()).commit();
                break;

            case R.id.logout:
                Logout();
                break;

        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    PreferenceUtils_Service sessionManager;

    private void Logout() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setIcon(R.drawable.ic_logout);
        alert.setTitle("LOGOUT");
        alert.setMessage("Are you sure you want to logout?");
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sessionManager = new PreferenceUtils_Service(Objects.requireNonNull(LandingService.this));
                sessionManager.logout();
                Intent i = new Intent(LandingService.this, Login.class);
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
