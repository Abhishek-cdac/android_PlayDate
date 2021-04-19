package com.playdate.app.ui.login;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.playdate.app.model.LoginUser;

public class LoginViewModel extends ViewModel {


    public MutableLiveData<String> EmailAddress = new MutableLiveData<>();
    public MutableLiveData<String> Password = new MutableLiveData<>();
    public MutableLiveData<Boolean> RegisterClick = new MutableLiveData<>();

    private MutableLiveData<LoginUser> userMutableLiveData;
//    private MutableLiveData<Boolean> Register;

    public MutableLiveData<Boolean> getRegisterUser() {

        if (RegisterClick == null) {
            RegisterClick = new MutableLiveData<>();
        }
        return RegisterClick;

    }

    public MutableLiveData<LoginUser> getUser() {

        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
        }
        return userMutableLiveData;

    }

    public void onClickRegister(View view) {
        Log.d("ddd","asdzsd");
        RegisterClick.setValue(true);
    }

    public void onClickLogin(View view) {

        LoginUser loginUser = new LoginUser(EmailAddress.getValue(), Password.getValue());
        userMutableLiveData.setValue(loginUser);
    }

    public void onClickForgotPass(View view) {

        LoginUser loginUser = new LoginUser(EmailAddress.getValue(), Password.getValue());

        userMutableLiveData.setValue(loginUser);


    }
}

