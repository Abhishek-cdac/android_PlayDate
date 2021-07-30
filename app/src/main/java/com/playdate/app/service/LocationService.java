package com.playdate.app.service;


import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;

import com.playdate.app.util.session.SessionPref;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationService extends Service implements LocationListener {

    public LocationService() {
    }

    private Context mContext;

    @SuppressLint("MissingPermission")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        try {
            LocationListener locationListener = new MyLocationListener(this);
            LocationManager locationManager = (LocationManager)
                    getSystemService(Context.LOCATION_SERVICE);
            mContext = this;
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                SessionPref pref = SessionPref.getInstance(this);
                pref.saveLOngKeyLattitude("lattitude", latitude);
                pref.saveLongKeyLongitude("longitude", longitude);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return START_STICKY;
    }


    @Override

    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        try {
            String cityName = null;
            Geocoder gcd = new Geocoder(this, Locale.getDefault());
            List<Address> addresses;
            try {
                addresses = gcd.getFromLocation(location.getLatitude(),
                        location.getLongitude(), 1);
                if (addresses.size() > 0) {
                    cityName = addresses.get(0).getLocality();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            SessionPref pref = SessionPref.getInstance(mContext);
            pref.saveStringKeyVal("LastCity", cityName);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        pref.saveStringKeyVal("Address_Complete", marker);
    }
}

