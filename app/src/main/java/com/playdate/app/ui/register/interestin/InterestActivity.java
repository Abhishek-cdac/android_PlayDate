package com.playdate.app.ui.register.interestin;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.playdate.app.R;
import com.playdate.app.databinding.ActivityInterestinBinding;
import com.playdate.app.ui.register.relationship.RelationActivity;
import com.playdate.app.ui.register.username.UserNameActivity;

public class InterestActivity extends AppCompatActivity {

    InterestInViewModel viewModel;
    ActivityInterestinBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new InterestInViewModel();
        binding = DataBindingUtil.setContentView(InterestActivity.this, R.layout.activity_interestin);
        binding.setLifecycleOwner(this);
        binding.setInterestInViewModel(viewModel);

        viewModel.OnNextClick().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean click) {
                startActivity(new Intent(InterestActivity.this, UserNameActivity
                        .class));

            }
        });

        viewModel.OnMaleClick().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean click) {
                if(click){
                    binding.btnMale.setBackground(getDrawable(R.drawable.selected_btn_back));
                }else{
                    binding.btnMale.setBackground(getDrawable(R.drawable.normal_btn_back));
                }

               // binding.btnFemale.setBackground(getDrawable(R.drawable.normal_btn_back));
               // binding.btnNonBinary.setBackground(getDrawable(R.drawable.normal_btn_back));

            }
        });
        viewModel.OnFemaleClick().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean click) {

                if(click){
                    binding.btnFemale.setBackground(getDrawable(R.drawable.selected_btn_back));
                }else{
                    binding.btnFemale.setBackground(getDrawable(R.drawable.normal_btn_back));
                }
              //  binding.btnMale.setBackground(getDrawable(R.drawable.normal_btn_back));
//                binding.btnFemale.setBackground(getDrawable(R.drawable.selected_btn_back));
               // binding.btnNonBinary.setBackground(getDrawable(R.drawable.normal_btn_back));
            }
        });
        viewModel.OnNonBinClick().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean click) {

                if(click){
                    binding.btnNonBinary.setBackground(getDrawable(R.drawable.selected_btn_back));
                }else{
                    binding.btnNonBinary.setBackground(getDrawable(R.drawable.normal_btn_back));
                }

              //  binding.btnMale.setBackground(getDrawable(R.drawable.normal_btn_back));
              //  binding.btnFemale.setBackground(getDrawable(R.drawable.normal_btn_back));
//                binding.btnNonBinary.setBackground(getDrawable(R.drawable.selected_btn_back));
            }
        });

        viewModel.onBackClick().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean click) {
                finish();
            }
        });

    }
}
