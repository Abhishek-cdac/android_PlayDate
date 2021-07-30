package com.playdate.app.ui.chat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.playdate.app.R;

import java.io.ByteArrayOutputStream;
import java.util.Objects;

import static com.playdate.app.ui.register.profile.UploadProfileActivity.REQUEST_LOCATION_CODE;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap gmap;
    private double lattitude, longitude;
    private Button btn_send;
    private MapView mapView;

    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        btn_send = findViewById(R.id.btn_send);


        Intent mIntent = getIntent();
        lattitude = mIntent.getDoubleExtra("lattitude", 0.0);
        longitude = mIntent.getDoubleExtra("longitude", 0.0);


        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }

        mapView = findViewById(R.id.map_view);
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle bundle) {
        super.onSaveInstanceState(bundle);

        Bundle mapViewBundle = bundle.getBundle(MAP_VIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            bundle.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);
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
    public void onMapReady(@NonNull GoogleMap googleMap) {
        gmap = googleMap;
//        mIntent = getIntent();
//        lattitude = mIntent.getDoubleExtra("lattitude", 0.0);
//        longitude = mIntent.getDoubleExtra("longitude", 0.0);


        gmap.setMinZoomPreference(16);
        gmap.setBuildingsEnabled(true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        gmap.setMyLocationEnabled(true);
        Toast.makeText(this, "" + lattitude + " , " + longitude, Toast.LENGTH_SHORT).show();
        LatLng ny = new LatLng(lattitude, longitude);
        gmap.addMarker(new MarkerOptions().position(ny));
        gmap.moveCamera(CameraUpdateFactory.newLatLng(ny));

        btn_send.setOnClickListener(v -> sendLocation());

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


    private void captureScreenShot() {

        GoogleMap.SnapshotReadyCallback callback = snapshot -> {
            Bitmap bitmap;
            bitmap = snapshot;

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Objects.requireNonNull(bitmap).compress(Bitmap.CompressFormat.JPEG, 40, baos);
            ChatMainActivity.locationBitmap = bitmap;

//            byte[] b = baos.toByteArray();
//            String temp = Base64.encodeToString(b, Base64.DEFAULT);
//
//            SessionPref pref = SessionPref.getInstance(MapActivity.this);
//            pref.saveStringKeyVal("locationImg", temp);
//
//            chatMainActivity.sharelocation(temp);


            Intent intent = new Intent();
            intent.putExtra("locationImg", true);
            setResult(REQUEST_LOCATION_CODE, intent);
            finish();

        };
        gmap.snapshot(callback);
    }
}





