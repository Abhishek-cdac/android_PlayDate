package com.playdate.app.ui.date;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.ybq.android.spinkit.SpinKitView;
import com.playdate.app.R;
import com.playdate.app.ui.dashboard.DashboardActivity;
import com.playdate.app.ui.dialogs.FullScreenDialog;
import com.squareup.picasso.Picasso;

public class PartnerSelected extends AppCompatActivity {

    ImageView iv_partner_image;
    ImageView iv_accepted;
    ImageView iv_loading;
    TextView tv_waiting;
    TextView tv_points;
    TextView tv_username;
    String image_url;
    String image_name;
    String image_points;
    boolean accepted = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_selected_partner);
        iv_partner_image = findViewById(R.id.partner_image);
        iv_accepted = findViewById(R.id.iv_accepted);
        iv_loading = findViewById(R.id.iv_loading);
        tv_waiting = findViewById(R.id.tv_waiting);
        tv_points = findViewById(R.id.tv_points);
        tv_username = findViewById(R.id.tv_username);

        Intent intent = getIntent();
        image_url = intent.getStringExtra("profile_image");
        image_name = intent.getStringExtra("profile_name");
        image_points = intent.getStringExtra("profile_points");

        tv_username.setText(image_name);
        tv_points.setText(image_points);
        Picasso.get().load(image_url).placeholder(R.drawable.cupertino_activity_indicator).into(iv_partner_image);

//        if (accepted) {
//            iv_loading.setVisibility(View.GONE);
//            tv_waiting.setText(R.string.accepted_patner);
//            iv_accepted.setVisibility(View.VISIBLE);
//        } else {
//            iv_loading.setVisibility(View.VISIBLE);
//            tv_waiting.setText(R.string.waiting_partner);
//            iv_accepted.setVisibility(View.GONE);
//        }

        tv_waiting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!accepted) {
                    iv_loading.setVisibility(View.GONE);
                    tv_waiting.setText(R.string.accepted_patner);
                    iv_accepted.setVisibility(View.VISIBLE);
                    accepted = true;
                } else {
                    startActivity(new Intent(PartnerSelected.this, SelectDateActivity.class));
                }


            }
        });


    }

    private void showPremium() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                accepted = true;
                Log.d("Accepted", String.valueOf(accepted));
            }
        }, 5000);


    }
}
