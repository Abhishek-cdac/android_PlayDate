package com.playdate.app.ui.register;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.playdate.app.R;
import com.playdate.app.databinding.ActivityRegisterBinding;
import com.playdate.app.ui.register.otp.OTPActivity;

public class RegisterActivity extends AppCompatActivity {
    private RegisterViewModel registerViewModel;

    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerViewModel = new RegisterViewModel();
        binding = DataBindingUtil.setContentView(RegisterActivity.this, R.layout.activity_register);
        binding.setLifecycleOwner(this);
        binding.setRegisterViewModel(registerViewModel);

        registerViewModel.onRegisterUser().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean loginUser) {
                RegisterActivity.this.startActivity(new Intent(RegisterActivity.this, OTPActivity.class));
            }
        });

    }
}
