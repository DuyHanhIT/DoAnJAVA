package com.example.shopsneaker.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MapsActivity extends AppCompatActivity implements com.google.android.gms.maps.OnMapReadyCallback {
    private com.google.android.gms.maps.GoogleMap map;
    androidx.appcompat.widget.Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.shopsneaker.R.layout.activity_maps);
        com.google.android.gms.maps.MapFragment mapFragment = (com.google.android.gms.maps.MapFragment) getFragmentManager().findFragmentById(com.example.shopsneaker.R.id.myMap);
        mapFragment.getMapAsync(this);
        toolbar= findViewById(com.example.shopsneaker.R.id.Toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Địa chỉ cửa hàng");
        toolbar.setNavigationOnClickListener(view -> {
            android.content.Intent intent =new android.content.Intent(getApplicationContext(),ContactActivity.class);
            startActivity(intent);
            finish();
        });
    }

    @Override
    public void onMapReady(@androidx.annotation.NonNull com.google.android.gms.maps.GoogleMap googleMap) {
        map=googleMap;
        com.google.android.gms.maps.model.LatLng sydney = new com.google.android.gms.maps.model.LatLng(10.8494, 106.7973);

        map.moveCamera(com.google.android.gms.maps.CameraUpdateFactory.newLatLngZoom(sydney,17));
        map.addMarker(new com.google.android.gms.maps.model.MarkerOptions()
                .title("VLTH Store")
                .snippet("Shop giày uy tín nhất Việt Nam")
                .position(sydney));
        //bat zoomout, zoomin
        map.getUiSettings().setZoomControlsEnabled(true);
    }
}

