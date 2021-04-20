package com.playdate.app.ui.register;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.playdate.app.R;
import com.playdate.app.databinding.ActivityRegisterBinding;
import com.playdate.app.model.RegisterUser;
import com.playdate.app.ui.register.otp.OTPActivity;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    private RegisterViewModel registerViewModel;

    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerViewModel = new RegisterViewModel();
        binding = DataBindingUtil.setContentView(RegisterActivity.this, R.layout.activity_register);
        binding.setLifecycleOwner(this);
        binding.setVMRegister(registerViewModel);

        registerViewModel.getFinish().observe(this, new Observer<Boolean>() {

            @Override
            public void onChanged(Boolean aBoolean) {
                finish();
            }
        });

        registerViewModel.getRegisterUser().observe(this, new Observer<RegisterUser>() {
            @Override
            public void onChanged(RegisterUser registerUser) {
                if (TextUtils.isEmpty(Objects.requireNonNull(registerUser).getFullname())) {

                } else if (TextUtils.isEmpty(Objects.requireNonNull(registerUser).getAddress())) {

                } else if (TextUtils.isEmpty(Objects.requireNonNull(registerUser).getPhoneNumber())) {

                } else if ((registerUser).getPhoneNumber().length() < 10) {

                } else if (TextUtils.isEmpty(Objects.requireNonNull(registerUser).getEmail())) {

                } else if (!registerUser.isEmailValid()) {

                } else if (TextUtils.isEmpty(Objects.requireNonNull(registerUser).getPassword())) {

                } else {
                    //next Page
                    Intent mIntent = new Intent(RegisterActivity.this, OTPActivity.class);
                    mIntent.putExtra("Name", registerUser.getFullname());
                    mIntent.putExtra("Phone", registerUser.getPhoneNumber());
                    mIntent.putExtra("Address", registerUser.getAddress());
                    mIntent.putExtra("Email", registerUser.getEmail());
                    mIntent.putExtra("Password", registerUser.getPassword());
                    startActivity(mIntent);
                }

            }
        });

    }
}
