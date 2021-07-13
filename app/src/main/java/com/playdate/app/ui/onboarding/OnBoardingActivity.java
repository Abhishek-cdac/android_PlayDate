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
        SessionPref pref = SessionPref.getInstance(this);
        if (pref.getBoolVal(CompleteProfile)) {
            startActivity(new Intent(OnBoardingActivity.this, DashboardActivity.class));
            finish();
        }

        ObjectAnimator scaleDown = ObjectAnimator.ofPropertyValuesHolder(
                binding.btnLogin,
                PropertyValuesHolder.ofFloat("scaleX", 1.2f),
                PropertyValuesHolder.ofFloat("scaleY", 1.2f));
        scaleDown.setDuration(600);
        scaleDown.setRepeatCount(ObjectAnimator.INFINITE);
        scaleDown.setRepeatMode(ObjectAnimator.REVERSE);

        scaleDown.start();

        if (pref.getBoolVal(SessionPref.DARKMODE)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("******", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
//                        String msg = getString(R.string.msg_token_fmt, token);
                        Log.d("******", token);
                        SessionPref pref= SessionPref.getInstance(OnBoardingActivity.this);
                        pref.saveStringKeyVal(LoginUserFCMID, token);
                        Toast.makeText(OnBoardingActivity.this, token, Toast.LENGTH_SHORT).show();
                    }
                });

        startService(new Intent(this,FcmMessageService.class));

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