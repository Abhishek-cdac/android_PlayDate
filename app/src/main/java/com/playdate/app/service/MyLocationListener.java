package com.playdate.app.service;

import android.content.ContentProvider;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.se.omapi.Session;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.playdate.app.ui.social.upload_media.PostMediaActivity;
import com.playdate.app.util.session.SessionPref;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.content.ContentValues.TAG;

class MyLocationListener implements LocationListener {
    Context mContext;
    String longitude;
    String latitude;
//    LottieAnimationView loader;

    public MyLocationListener(Context ctx) {
        this.mContext = ctx;
    }

    @Override
    public void onLocationChanged(Location loc) {
        Toast.makeText(mContext, "LocationListener called", Toast.LENGTH_SHORT).show();

        longitude = "Longitude: " + loc.getLongitude();
        Log.v(TAG, longitude);
        latitude = "Latitude: " + loc.getLatitude();
        Log.v(TAG, latitude);

        Log.d("Lattitude.............", latitude);
        Log.d("Longitude.............", longitude);

        /*------- To get city name from coordinates -------- */
        String cityName = null;
        String marker = null;
        Geocoder gcd = new Geocoder(mContext, Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = gcd.getFromLocation(loc.getLatitude(),
                    loc.getLongitude(), 1);
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
        SessionPref pref = SessionPref.getInstance(mContext);
        pref.saveStringKeyVal("LastCity", cityName);
        pref.saveStringKeyVal("Address_Complete", marker);
//        Log.d("Address_Complete", marker);
        Toast.makeText(mContext, cityName, Toast.LENGTH_SHORT).show();
//        loader.setVisibility(View.GONE);


    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }


}
