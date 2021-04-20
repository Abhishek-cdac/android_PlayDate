package com.playdate.app.ui.register.otp;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class OTPViewModel extends ViewModel {
    public MutableLiveData<Boolean> RegisterClick = new MutableLiveData<>();
    public MutableLiveData<String> txtmobile = new MutableLiveData<>();

    private MutableLiveData<Boolean> userMutableLiveData;
    private MutableLiveData<Boolean> Register;

    public MutableLiveData<Boolean> onRegisterUser() {

        if (RegisterClick == null) {
            RegisterClick = new MutableLiveData<>();
        }
        return RegisterClick;

    }

    public void setMobile(String mobile) {
        String temp = mobile.substring(0, 3);
        String temp1 = mobile.substring(3, 6);
        String temp2 = mobile.substring(6, 10);
        txtmobile.setValue(temp + "-" + temp1 + "-" + temp2);
    }

    public void onClickSubmit(View view) {
        RegisterClick.setValue(true);
    }

}


