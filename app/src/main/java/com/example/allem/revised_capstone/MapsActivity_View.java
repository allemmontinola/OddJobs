package com.example.allem.revised_capstone;

import android.app.ProgressDialog;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.example.allem.revised_capstone.API.ServiceApi;
import com.example.allem.revised_capstone.Login;
import com.example.allem.revised_capstone.Model.GetLatLongModel;
import com.example.allem.revised_capstone.R;
import com.example.allem.revised_capstone.ServiceProvider.FragmentAppointService;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapsActivity_View extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_service);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    public static double lat;
    public static double longti;
    String fullName = FragmentAppointService.fullName;
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        LatLng sydney = new LatLng(lat, longti);
        mMap.addMarker(new MarkerOptions().position(sydney).title(fullName));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.setMaxZoomPreference(30);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13.0f));
    }

}
