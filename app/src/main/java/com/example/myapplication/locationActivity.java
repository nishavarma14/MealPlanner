package com.example.myapplication;

import androidx.fragment.app.FragmentActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.myapplication.databinding.ActivityLocationBinding;
import com.google.android.gms.maps.model.PolylineOptions;

public class locationActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityLocationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLocationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera

        LatLng mountreachOffice = new LatLng(20.899291109292733, 77.76381686676253);
        mMap.addMarker(new MarkerOptions().position(mountreachOffice).icon(BitmapDescriptorFactory.fromResource(R.drawable.office)).title(" Mountreach Solution Pvt Ltd"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mountreachOffice,16),5000,null);
        mMap.addCircle(new CircleOptions().strokeColor(Color.GREEN)
                .fillColor(Color.argb(100,0,255,0))
                .radius(150)
                .center(mountreachOffice));

        LatLng murtizapurcollege = new LatLng(20.77714536365476, 77.40705740844903);
        mMap.addMarker(new MarkerOptions().position(murtizapurcollege).icon(BitmapDescriptorFactory.fromResource(R.drawable.college1)).title(" Government polytechnic murtizapur"));
        mMap.addCircle(new CircleOptions()
                .strokeColor(Color.GREEN)
                .fillColor(Color.argb(100,0,255,0))
                .radius(150)
                .center(murtizapurcollege));

        mMap.addPolyline(new PolylineOptions()
                .add(mountreachOffice,murtizapurcollege)
                .color(Color.BLUE)
                .width(4));



    }
}

