package com.playdate.app.service;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import com.playdate.app.util.session.SessionPref;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

class MyLocationListener implements LocationListener {
    private final Context mContext;

    public MyLocationListener(Context ctx) {
        this.mContext = ctx;
    }

    @Override
    public void onLocationChanged(Location loc) {

        try {
            String cityName = null;
//            String marker = null;
            Geocoder gcd = new Geocoder(mContext, Locale.getDefault());
            List<Address> addresses;
            try {
                addresses = gcd.getFromLocation(loc.getLatitude(),
                        loc.getLongitude(), 1);
                if (addresses.size() > 0) {
                    cityName = addresses.get(0).getLocality();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            SessionPref pref = SessionPref.getInstance(mContext);
            pref.saveStringKeyVal("LastCity", cityName);
//            pref.saveStringKeyVal("Address_Complete", marker);
        } catch (Exception e) {
            e.printStackTrace();
        }

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
