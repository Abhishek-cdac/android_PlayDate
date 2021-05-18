package com.playdate.app.ui.date.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.ybq.android.spinkit.SpinKitView;
import com.playdate.app.R;
import com.playdate.app.ui.date.fragments.FragLocationConfirmation;
import com.playdate.app.ui.interfaces.OnInnerFragmentClicks;

public class FragLocationTracing extends Fragment {

    SpinKitView spin_kit_location_trace;
    SpinKitView spin_kit_dots1;
    SpinKitView spin_kit_dots2;

    RelativeLayout rl_other;
    RelativeLayout rl_mine;
    TextView tv_location;

    ImageView iv_my_image;
    ImageView iv_partner_image;
    ImageView iv_pin_restaurent;
    ImageView iv_check_mine;
    ImageView iv_check_other;
    ImageView iv_check_rest;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_location_tracing, container, false);

        spin_kit_location_trace = view.findViewById(R.id.spin_kit_location_trace);
        spin_kit_dots1 = view.findViewById(R.id.spin_kit_dots1);
        spin_kit_dots2 = view.findViewById(R.id.spin_kit_dots2);

        rl_other = view.findViewById(R.id.rl_other);
        rl_mine = view.findViewById(R.id.rl_mine);
        tv_location = view.findViewById(R.id.tv_location);

        iv_my_image = view.findViewById(R.id.iv_my_image);
        iv_partner_image = view.findViewById(R.id.iv_partner_image);
        iv_pin_restaurent = view.findViewById(R.id.iv_pin_restaurent);
        iv_check_mine = view.findViewById(R.id.iv_check_mine);
        iv_check_other = view.findViewById(R.id.iv_check_other);
        iv_check_rest = view.findViewById(R.id.iv_check_rest);
        animationFirst();

        return view;
    }

//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_location_tracing);
//
//        spin_kit_location_trace = findViewById(R.id.spin_kit_location_trace);
//        spin_kit_dots1 = findViewById(R.id.spin_kit_dots1);
//        spin_kit_dots2 = findViewById(R.id.spin_kit_dots2);
//
//        rl_other = findViewById(R.id.rl_other);
//        rl_mine = findViewById(R.id.rl_mine);
//        tv_location = findViewById(R.id.tv_location);
//
//        iv_my_image = findViewById(R.id.iv_my_image);
//        iv_partner_image = findViewById(R.id.iv_partner_image);
//        iv_pin_restaurent = findViewById(R.id.iv_pin_restaurent);
//        iv_check_mine = findViewById(R.id.iv_check_mine);
//        iv_check_other = findViewById(R.id.iv_check_other);
//        iv_check_rest = findViewById(R.id.iv_check_rest);
//        animationFirst();
//
//    }

    private void animationFirst() {

        new Handler().postDelayed(new Runnable() {
            public void run() {
                rl_mine.setVisibility(View.GONE);
                iv_check_mine.setVisibility(View.VISIBLE);
                spin_kit_dots1.setVisibility(View.GONE);
                spin_kit_dots2.setVisibility(View.VISIBLE);
                tv_location.setText("Use a nice cologne...");
                spin_kit_location_trace.getLayoutParams().height = 450;
                spin_kit_location_trace.getLayoutParams().width = 450;
                animationSecond();
            }
        }, 5000);


    }

    private void animationSecond() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                rl_other.setVisibility(View.GONE);
                iv_check_other.setVisibility(View.VISIBLE);
                tv_location.setText("Please be patient...");
                spin_kit_location_trace.getLayoutParams().height = 350;
                spin_kit_location_trace.getLayoutParams().width = 350;
                animationThird();
            }
        }, 5000);
    }

    private void animationThird() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                iv_pin_restaurent.setVisibility(View.GONE);
                spin_kit_dots2.setVisibility(View.GONE);
                iv_check_rest.setVisibility(View.VISIBLE);
                tv_location.setText("We found you...");
                spin_kit_location_trace.getLayoutParams().height = 200;
                spin_kit_location_trace.getLayoutParams().width = 200;
                reDirect();
            }
        }, 5000);
    }

    private void reDirect() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                OnInnerFragmentClicks frag = (OnInnerFragmentClicks) getActivity();
                frag.ReplaceFrag(new FragLocationConfirmation());
//                startActivity(new Intent(getActivity(), LocationConfirmationActivity.class));
            }
        }, 3000);
    }
}
