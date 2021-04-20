package com.playdate.app.ui.register.age_verification;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AgeVerificationViewModel  extends ViewModel {
    public MutableLiveData<Boolean> RegisterClick = new MutableLiveData<>();

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

}