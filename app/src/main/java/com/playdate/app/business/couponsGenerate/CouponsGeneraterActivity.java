package com.playdate.app.business.couponsGenerate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.playdate.app.R;
import com.playdate.app.business.couponcreated.CouponCreatedActivity;

public class CouponsGeneraterActivity extends AppCompatActivity {

    Button btnCreateCoupons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupons_generater);
        btnCreateCoupons = findViewById(R.id.btnCreateCoupons);
        btnCreateCoupons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CouponCreatedActivity.class);
                startActivity(intent);
            }
        });
    }
}