package com.playdate.app.business.couponcreated;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.playdate.app.R;
import com.playdate.app.business.chooselevel.ChooseLevelActivity;

public class CouponCreatedActivity extends AppCompatActivity {
RelativeLayout rl_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_created);
        rl_code = findViewById(R.id.rl_code);
        rl_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChooseLevelActivity.class);
                startActivity(intent);
            }
        });
    }
}