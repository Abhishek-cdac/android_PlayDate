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
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.playdate.app.util.session.SessionPref;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationService extends Service implements LocationListener{
    double latitude;
    double longitude;

    public LocationService() {

    }


    @SuppressLint("MissingPermission")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Toast.makeText(this, "Location Service class", Toast.LENGTH_SHORT).show();
        LocationManager locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new MyLocationListener(this);
        Log.d("LOCATIONManager_____", String.valueOf(locationManager));

        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 5000, 10, locationListener);


        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        Log.d("Location..", String.valueOf(location));
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

    @Override
    public void onLocationChanged(@NonNull Location location) {
        Toast.makeText(this, "LocationListener called", Toast.LENGTH_SHORT).show();
        String cityName = null;
        String marker = null;
        Geocoder gcd = new Geocoder(this, Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = gcd.getFromLocation(location.getLatitude(),
                    location.getLongitude(), 1);
            if (addresses.size() > 0) {
                Log.d("LOCALITY..............", addresses.get(0).getLocality());
                System.out.println(addresses.get(0).getLocality());
                cityName = addresses.get(0).getLocality();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String s = longitude + "\n" + latitude + "\n\nMy Current City is: "
                + cityName;

    }
}


//    @Override
//    public void onLocationChanged(@NonNull Location loc) {
//        String marker ;
//        Geocoder gcd = new Geocoder(this, Locale.getDefault());
//        List<Address> addresses;
//        try {
//            addresses = gcd.getFromLocation(loc.getLatitude(),
//                    loc.getLongitude(), 1);
//            if (addresses.size() > 0) {
//                System.out.println(addresses.get(0).getLocality());
////                cityName = addresses.get(0).getLocality();
//                marker = addresses.get(0).getLocality();
//                Log.d("Address_Complete", marker);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
////        String s = longitude + "\n" + latitude + "\n\nMy Current City is: "
//////                + cityName;
////        SessionPref pref = SessionPref.getInstance(this);
////        pref.saveStringKeyVal("Address_Complete", marker);
////        Log.d("Address_Complete", marker);
//    }
//}