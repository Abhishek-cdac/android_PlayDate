package com.playdate.app.ui.register.username;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.playdate.app.R;
import com.playdate.app.databinding.ActivityUsernameBinding;
import com.playdate.app.ui.register.bio.BioActivity;
import com.playdate.app.util.common.CommonClass;

public class UserNameActivity extends AppCompatActivity {
    UserNameViewModel userNameViewModel;
    ActivityUsernameBinding binding;
    Intent mIntent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userNameViewModel = new UserNameViewModel();
        binding = DataBindingUtil.setContentView(UserNameActivity.this, R.layout.activity_username);
        binding.setLifecycleOwner(this);
        binding.setUserNameViewModel(userNameViewModel);
        mIntent = getIntent();
        userNameViewModel.OnNextClick().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean click) {
                if (mIntent.getBooleanExtra("fromProfile", false)) {
                    Intent mIntent = new Intent();
                    setResult(408, mIntent);
                    finish();
                } else {
                    startActivity(new Intent(UserNameActivity.this, BioActivity
                            .class));
                }

            }
        });
        userNameViewModel.OnUserNameInput().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String charSeq) {
                if (charSeq.length() == 5) {
                    startTimer();
                } else {
//                    binding.spinKit.setVisibility(View.GONE);
                }

            }
        });

//        iv_next

        userNameViewModel.onBackClick().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean click) {
                finish();
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (null != handler)
                handler.removeCallbacksAndMessages(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Handler handler;

    private void startTimer() {
        binding.spinKit.setVisibility(View.VISIBLE);
        new CommonClass().hideKeyboard(binding.spinKit, this);
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.spinKit.setVisibility(View.GONE);
                binding.ivNext.setVisibility(View.VISIBLE);
                binding.edtFullname.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.check, 0);

            }
        }, 1000);
    }
}
