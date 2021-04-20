package com.playdate.app.ui.register.otp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.playdate.app.R;
import com.playdate.app.databinding.ActivityOtpBinding;
import com.playdate.app.ui.register.relationship.RelationActivity;

public class OTPActivity extends AppCompatActivity {

    private OTPViewModel otpViewModel;

    private ActivityOtpBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        otpViewModel = new OTPViewModel();
        binding = DataBindingUtil.setContentView(OTPActivity.this, R.layout.activity_otp);
        binding.setLifecycleOwner(this);
        binding.setOTPViewModel(otpViewModel);
        Intent mIntent = getIntent();
//        mIntent.getStringExtra("Name");
        String phone = mIntent.getStringExtra("Phone");
        otpViewModel.setMobile(phone);
//        mIntent.getStringExtra("Address");
//        mIntent.getStringExtra("Email");
//        mIntent.getStringExtra("Password");

        otpViewModel.onRegisterUser().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean loginUser) {
                OTPActivity.this.startActivity(new Intent(OTPActivity.this, RelationActivity.class));
            }
        });

    }
}
