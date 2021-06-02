package com.playdate.app.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.playdate.app.util.session.SessionPref;

public class LocationService extends Service {
    double latitude;
    double longitude;

    public LocationService() {
    }

    @SuppressLint("MissingPermission")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        LocationListener locationListener = new MyLocationListener(this);

//        Toast.makeText(this, "Location Service class", Toast.LENGTH_SHORT).show();
        LocationManager locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);

        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();

            Log.d("Lattitude.............", String.valueOf(latitude));
            Log.d("Longitude.............", String.valueOf(longitude));
            SessionPref pref = SessionPref.getInstance(this);
            pref.saveLOngKeyLattitude("lattitude", latitude);
            pref.saveLongKeyLongitude("longitude", longitude);

        }


        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }



}