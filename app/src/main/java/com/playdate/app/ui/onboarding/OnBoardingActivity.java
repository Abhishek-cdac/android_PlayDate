package com.playdate.app.ui.onboarding;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.playdate.app.R;
import com.playdate.app.service.FcmMessageService;
import com.playdate.app.ui.dashboard.DashboardActivity;
import com.playdate.app.ui.interfaces.OnBackPressed;
import com.playdate.app.ui.login.LoginActivity;
import com.playdate.app.util.session.SessionPref;

import static com.playdate.app.util.session.SessionPref.CompleteProfile;
import static com.playdate.app.util.session.SessionPref.LoginUserFCMID;

public class OnBoardingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OnBoardingViewModel loginViewModel = new OnBoardingViewModel(this);
        com.playdate.app.databinding.ActivityOnboardingBinding binding = DataBindingUtil.setContentView(OnBoardingActivity.this, R.layout.activity_onboarding);
        binding.setLifecycleOwner(this);
        binding.setOnBoardingViewModel(loginViewModel);
        binding.tabLayout.setupWithViewPager(binding.viewPager);
        binding.setManager(getSupportFragmentManager());

        loginViewModel.getStarted().observe(this, aBoolean -> {
            startActivity(new Intent(OnBoardingActivity.this, LoginActivity.class));
            finish();
        });

        ObjectAnimator scaleDown = ObjectAnimator.ofPropertyValuesHolder(
                binding.btnLogin,
                PropertyValuesHolder.ofFloat("scaleX", 1.2f),
                PropertyValuesHolder.ofFloat("scaleY", 1.2f));
        scaleDown.setDuration(600);
        scaleDown.setRepeatCount(ObjectAnimator.INFINITE);
        scaleDown.setRepeatMode(ObjectAnimator.REVERSE);

        scaleDown.start();



        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w("******", "Fetching FCM registration token failed", task.getException());
                        return;
                    }
                    String token = task.getResult();
                    Log.d("******", token);
                    SessionPref pref1 = SessionPref.getInstance(OnBoardingActivity.this);
                    pref1.saveStringKeyVal(LoginUserFCMID, token);
                });

        startService(new Intent(this,FcmMessageService.class));
        SessionPref pref = SessionPref.getInstance(this);
        if (pref.getBoolVal(SessionPref.DARKMODE)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        if (pref.getBoolVal(CompleteProfile)) {
            startActivity(new Intent(OnBoardingActivity.this, DashboardActivity.class));
            finish();
        }


    }


    @BindingAdapter("bind:fadevisible")
    public static void bindViewPagerAdapter(final ViewPager view, OnBoardingViewModel viewModel) {
        final OnBoardingAdapter adapter = new OnBoardingAdapter(view.getContext(), viewModel.activity.getSupportFragmentManager());
        view.setAdapter(adapter);

    }


    @BindingAdapter({"setUpWithViewpager"})
    public static void setUpWithViewpager(final com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator indicator, ViewPager viewPager) {
    }


}