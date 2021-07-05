package com.playdate.app.ui.chat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.playdate.app.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap gmap;
    Intent mIntent;
    double lattitude, longitude;
    Button btn_send;
    MapView mapView;
    ChatMainActivity chatMainActivity;

    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        btn_send = findViewById(R.id.btn_send);
        chatMainActivity = new ChatMainActivity();

//        mIntent = getIntent();
//        lattitude = mIntent.getDoubleExtra("lattitude", 0.0);
//        lattitude = mIntent.getDoubleExtra("longitude", 0.0);

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }

        mapView = findViewById(R.id.map_view);
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);
        }

        mapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmap = googleMap;
        mIntent = getIntent();
        lattitude = mIntent.getDoubleExtra("lattitude", 0.0);
        lattitude = mIntent.getDoubleExtra("longitude", 0.0);

        gmap.setMinZoomPreference(16);
        Toast.makeText(this, "" + lattitude + " , " + longitude, Toast.LENGTH_SHORT).show();
        LatLng ny = new LatLng(25.2323, 85.17361);
        gmap.moveCamera(CameraUpdateFactory.newLatLng(ny));

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendLocation();
            }
        });


    }

    private void sendLocation() {

        new AlertDialog.Builder(MapActivity.this)

                .setInverseBackgroundForced(true)
                .setMessage("Are you sure you want to share your location?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, id) -> captureScreenShot())
                .setNegativeButton("No", null)
                .show();


    }

    Bitmap bitmap123;

    private void captureScreenShot() {

        GoogleMap.SnapshotReadyCallback callback = new GoogleMap.SnapshotReadyCallback() {


            @Override
            public void onSnapshotReady(@Nullable Bitmap snapshot) {
                bitmap123 = snapshot;

                Log.d("LocationImg MapActivity", "sharelocation: "+bitmap123.toString());

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap123.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();


                chatMainActivity.sharelocation(byteArray);

//                Intent intent = new Intent(MapActivity.this, ImageViewActivity.class);
//                intent.putExtra("image", byteArray);
//                startActivity(intent);
            }
        };
        gmap.snapshot(callback);
    }
}



