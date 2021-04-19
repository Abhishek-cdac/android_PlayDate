package com.playdate.app.ui.onboarding;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class OnBoardingViewModel extends ViewModel {

    OnBoardingActivity activity;

    public OnBoardingViewModel(OnBoardingActivity activity) {
        this.activity = activity;
    }

    public MutableLiveData<Boolean> RegisterClick = new MutableLiveData<>();

    public MutableLiveData<Boolean> getStarted() {

        if (RegisterClick == null) {
            RegisterClick = new MutableLiveData<>();
        }
        return RegisterClick;

    }

    public void onClick(View view) {
        RegisterClick.setValue(true);
    }


}
