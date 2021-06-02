package com.playdate.app.couple.ui.register.coupleusername;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.playdate.app.R;
import com.playdate.app.couple.ui.register.couplebio.CoupleBioActivity;
import com.playdate.app.databinding.ActivityCoupleUsernameBinding;

import com.playdate.app.util.common.CommonClass;

public class CoupleUserNameActivity extends AppCompatActivity {
    CoupleUserNameViewModel coupleUserNameViewModel;
    ActivityCoupleUsernameBinding binding;
    Intent mIntent;
    CommonClass clsCommon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        clsCommon = CommonClass.getInstance();
        coupleUserNameViewModel = new CoupleUserNameViewModel();
        binding = DataBindingUtil.setContentView(CoupleUserNameActivity.this, R.layout.activity_couple_username);
        binding.setLifecycleOwner(this);
        binding.setCoupleUserNameViewModel(coupleUserNameViewModel);
        mIntent = getIntent();
        binding.setCoupleUserNameViewModel(coupleUserNameViewModel);


        coupleUserNameViewModel.OnNextClick().observe(this, click -> {

            if (coupleUserNameViewModel.UserName.getValue() != null) {

                startActivity(new Intent(CoupleUserNameActivity.this, CoupleBioActivity
                        .class));

            }

        });
        coupleUserNameViewModel.OnUserNameInput().

                observe(this, new Observer<String>() {

                    @Override
                    public void onChanged(String charSeq) {
//                if (charSeq.length() == 5) {
//                    startTimer();
//                } else {
////                    binding.spinKit.setVisibility(View.GONE);
//                }

                    }
                });

//        iv_next

        coupleUserNameViewModel.onBackClick().

                observe(this, new Observer<Boolean>() {
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


    @Override
    protected void onStop() {
        super.onStop();
        try {
            if(null!=handler){
                handler.removeCallbacksAndMessages(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
