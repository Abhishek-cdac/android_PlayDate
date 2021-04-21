package com.playdate.app.ui.register.otp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.playdate.app.R;
import com.playdate.app.databinding.ActivityOtpBinding;
import com.playdate.app.ui.register.age_verification.AgeVerifiationActivity;
import com.playdate.app.ui.register.relationship.RelationActivity;
import com.playdate.app.util.common.CommonClass;

public class OTPActivity extends AppCompatActivity {

    private OTPViewModel otpViewModel;

    private ActivityOtpBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        otpViewModel = new OTPViewModel();
        otpViewModel.init();
        binding = DataBindingUtil.setContentView(OTPActivity.this, R.layout.activity_otp);
        binding.setLifecycleOwner(this);
        binding.setOTPViewModel(otpViewModel);
        Intent mIntent = getIntent();
//        mIntent.getStringExtra("Name");
        String phone = mIntent.getStringExtra("Phone");
        otpViewModel.setMobile(phone);
        otpViewModel.startTimer();
//        mIntent.getStringExtra("Address");
//        mIntent.getStringExtra("Email");
//        mIntent.getStringExtra("Password");

        otpViewModel.onRegisterUser().observe(this, loginUser -> OTPActivity.this.startActivity(new Intent(OTPActivity.this, AgeVerifiationActivity.class)));
        otpViewModel.resendClick.observe(this, loginUser -> {
            Toast.makeText(OTPActivity.this, "Resent", Toast.LENGTH_SHORT).show();
            binding.txtResend.setVisibility(View.INVISIBLE);
            binding.txtTimer.setVisibility(View.VISIBLE);
            otpViewModel.startTimer();
        });
        otpViewModel.CountDownFinish.observe(this, loginUser -> {
            binding.txtResend.setVisibility(View.VISIBLE);
            binding.txtTimer.setVisibility(View.GONE);
        });
        otpViewModel.iv_backClick.observe(this, loginUser -> {
            finish();
        });
        otpViewModel.txtOTP.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String loginUser) {
                if (loginUser.length() > 4) {
                    new CommonClass().hideKeyboard(binding.edtOtp, OTPActivity.this);
                    binding.edtOtp.clearFocus();
                }

            }
        });

    }
}
