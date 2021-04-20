package com.playdate.app.ui.register.age_verification;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.playdate.app.R;
import com.playdate.app.databinding.ActivityAgeVerificationBinding;
import com.playdate.app.databinding.ActivityRegisterBinding;
import com.playdate.app.ui.register.RegisterActivity;
import com.playdate.app.ui.register.RegisterViewModel;
import com.playdate.app.ui.register.otp.OTPActivity;

public class AgeVerifiationActivity  extends AppCompatActivity {
    private AgeVerificationViewModel age_verify_viewmodel;

    private ActivityAgeVerificationBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        age_verify_viewmodel = new AgeVerificationViewModel();
        binding = DataBindingUtil.setContentView(AgeVerifiationActivity.this, R.layout.activity_age_verification);
        binding.setLifecycleOwner(this);
        binding.setAgeVerificationViewModel(age_verify_viewmodel);

        age_verify_viewmodel.onRegisterUser().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean loginUser) {
                AgeVerifiationActivity.this.startActivity(new Intent(AgeVerifiationActivity.this, OTPActivity.class));
            }
        });

    }
}