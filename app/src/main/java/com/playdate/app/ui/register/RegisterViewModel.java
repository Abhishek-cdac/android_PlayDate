package com.playdate.app.ui.register;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RegisterViewModel extends ViewModel {
    public MutableLiveData<Boolean> RegisterClick = new MutableLiveData<>();
    public MutableLiveData<Boolean> onFBClick = new MutableLiveData<>();
    public MutableLiveData<Boolean> onGoogleClick = new MutableLiveData<>();

    private MutableLiveData<Boolean> userMutableLiveData;
    private MutableLiveData<Boolean> Register;

    public MutableLiveData<Boolean> onRegisterUser() {

        if (RegisterClick == null) {
            RegisterClick = new MutableLiveData<>();
        }
        return RegisterClick;

    }


    public void onClickRegister(View view) {
        Log.d("ddd", "asdzsd");
        RegisterClick.setValue(true);
    }

    public MutableLiveData<Boolean> GoogleClick() {

        if (onGoogleClick == null) {
            onGoogleClick = new MutableLiveData<>();
        }
        return onGoogleClick;

    }


    public void onClickgPlus(View view) {

        onGoogleClick.setValue(true);

    }


    public MutableLiveData<Boolean> fbClick() {

        if (onFBClick == null) {
            onFBClick = new MutableLiveData<>();
        }
        return onFBClick;

    }


    public void onClickFb(View view) {

        onFBClick.setValue(true);


    }

    public void onClickTwitter(View view) {

    }

}
