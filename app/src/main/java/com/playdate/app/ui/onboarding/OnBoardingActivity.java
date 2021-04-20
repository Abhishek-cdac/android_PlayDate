package com.playdate.app.ui.onboarding;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.playdate.app.R;
import com.playdate.app.databinding.ActivityOnboardingBinding;
import com.playdate.app.ui.login.LoginActivity;

public class OnBoardingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OnBoardingViewModel loginViewModel = new OnBoardingViewModel(this);
        com.playdate.app.databinding.ActivityOnboardingBinding binding = DataBindingUtil.setContentView(OnBoardingActivity.this, R.layout.activity_onboarding);
        binding.setLifecycleOwner(this);
        binding.setOnBoardingViewModel(loginViewModel);
        binding.tabLayout.setupWithViewPager(binding.viewPager);
//        binding.getFadevisible();
//        binding.getIndicator();
        binding.setManager(getSupportFragmentManager());

        loginViewModel.getStarted().observe(this, aBoolean -> {
            startActivity(new Intent(OnBoardingActivity.this, LoginActivity.class));
            finish();
        });

    }


    @BindingAdapter("bind:fadevisible")
    public static void bindViewPagerAdapter(final ViewPager view, OnBoardingViewModel viewModel) {
        final OnBoardingAdapter adapter = new OnBoardingAdapter(view.getContext(), viewModel.activity.getSupportFragmentManager());
        view.setAdapter(adapter);

    }


    @BindingAdapter({"setUpWithViewpager"})
    public static void setUpWithViewpager(final com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator indicator, ViewPager viewPager) {
//        indicator.setViewPager(viewPager);
    }


}