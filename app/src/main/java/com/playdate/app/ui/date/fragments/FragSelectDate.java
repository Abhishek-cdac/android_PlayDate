package com.playdate.app.ui.date.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.playdate.app.R;
import com.playdate.app.service.GpsTracker;
import com.playdate.app.ui.interfaces.OnInnerFragmentClicks;
import com.playdate.app.util.common.TransparentProgressDialog;

import java.util.Objects;

public class FragSelectDate extends Fragment {

    private double lattitude;
    private double longitude;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_select_date, container, false);
        RelativeLayout rl_inperson = view.findViewById(R.id.in_person);
        RelativeLayout rl_virtual = view.findViewById(R.id.virtual);

        rl_inperson.setOnClickListener(v -> {
            locationFetch();

        });

        rl_virtual.setOnClickListener(v -> {
            OnInnerFragmentClicks frag = (OnInnerFragmentClicks) getActivity();
            Objects.requireNonNull(frag).ReplaceFrag(new FragSelectPartner("virtual"));
        });

        return view;
    }

    private void locationFetch() {
        GpsTracker gpsTracker = new GpsTracker(getActivity());
        if (gpsTracker.canGetLocation()) {
            this.lattitude = gpsTracker.getLatitude();
            this.longitude = gpsTracker.getLongitude();
            Log.d("latlong", "" + lattitude + "  " + longitude);
            if (String.valueOf(lattitude).equals("0.0") || String.valueOf(longitude).equals("0.0")) {
                Toast.makeText(getActivity(), "Fetching your location", Toast.LENGTH_SHORT).show();
//                locationFetch();

            } else {
                Log.d("Current Location", "locationFetch: " + lattitude + " , " + longitude);

                Toast.makeText(getActivity(), "" + lattitude + " , " + longitude, Toast.LENGTH_SHORT).show();
                FragLocationTracing.lattitude = lattitude;
                FragLocationTracing.longitude = longitude;

                OnInnerFragmentClicks frag = (OnInnerFragmentClicks) getActivity();
                Objects.requireNonNull(frag).ReplaceFrag(new FragSelectPartner("in Person"));
            }


        } else {
            gpsTracker.showSettingsAlert();
        }
    }


}
