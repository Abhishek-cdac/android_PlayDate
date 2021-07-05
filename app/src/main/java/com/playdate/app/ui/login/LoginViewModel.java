package com.playdate.app.ui.login;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;
import com.playdate.app.model.LoginUser;

public class LoginViewModel extends ViewModel {

    public LoginViewModel() {
    }

    public MutableLiveData<String> EmailAddress = new MutableLiveData<>();
    public MutableLiveData<String> Password = new MutableLiveData<>();
    public MutableLiveData<Boolean> RegisterClick = new MutableLiveData<>();
    public MutableLiveData<Boolean> ForgotClick = new MutableLiveData<>();
    public MutableLiveData<Boolean> onFBClick = new MutableLiveData<>();
    public MutableLiveData<Boolean> onGoogleClick = new MutableLiveData<>();

    private CallbackManager callbackManager;
    private LoginManager loginManager;

    private MutableLiveData<LoginUser> userMutableLiveData;

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
        RegisterClick.setValue(true);
    }

    public void onClickLogin(View view) {

        LoginUser loginUser = new LoginUser(EmailAddress.getValue(), Password.getValue());
        userMutableLiveData.setValue(loginUser);
    }

    public void onClickForgotPass(View view) {
        ForgotClick.setValue(true);
    }

    public MutableLiveData<Boolean> getForgotClick() {

        if (ForgotClick == null) {
            ForgotClick = new MutableLiveData<>();
        }
        return ForgotClick;

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


//   CLIENT ID  - 796495283790-tmc38onsosd8gvcs206bknct6k6mh6po.apps.googleusercontent.com
//   CLIENT SECRET -  uu2Rrd-c1TG4Il9wXbbLAppJ

}
