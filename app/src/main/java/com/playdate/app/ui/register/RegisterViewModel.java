package com.playdate.app.ui.register;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.playdate.app.model.RegisterUser;

public class RegisterViewModel extends ViewModel {




    public MutableLiveData<RegisterUser> userMutableLiveData;

    public MutableLiveData<Boolean> onFinishCall = new MutableLiveData<>();
    public MutableLiveData<String> fullname = new MutableLiveData<>();
    public MutableLiveData<String> address = new MutableLiveData<>();
    public MutableLiveData<String> phoneNumber = new MutableLiveData<>();
    public MutableLiveData<String> email = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();

    public MutableLiveData<Boolean> onFBClick = new MutableLiveData<>();
    public MutableLiveData<Boolean> onGoogleClick = new MutableLiveData<>();


    public MutableLiveData<RegisterUser> getRegisterUser() {

        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
            user = new RegisterUser("", "", "", "", "", "");
        }
        return userMutableLiveData;

    }

    public MutableLiveData<Boolean> getFinish() {
        return onFinishCall;

    }

    RegisterUser user;

    public void onClickRegister(View view) {
        user = new RegisterUser(fullname.getValue(), address.getValue(), phoneNumber.getValue(), email.getValue(), password.getValue(), "");
        //  Log.d("ddd", fullname.getValue());
        userMutableLiveData.setValue(user);
    }

    public void onLoginClick(View view) {
        onFinishCall.setValue(true);
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


    public void onClickGoogle(View view) {

        onGoogleClick.setValue(true);
    }

    public MutableLiveData<Boolean> getOnGoogleClick() {

        if (onGoogleClick == null) {
            onGoogleClick = new MutableLiveData<>();
        }
        return onGoogleClick;

    }

    public void clearFileds() {
        user = new RegisterUser("", "", "", "", "", "");
        userMutableLiveData.setValue(user);

    }
}
