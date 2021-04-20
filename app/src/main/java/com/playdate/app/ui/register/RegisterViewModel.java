package com.playdate.app.ui.register;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.playdate.app.model.LoginUser;
import com.playdate.app.model.RegisterUser;

public class RegisterViewModel extends ViewModel {
    public MutableLiveData<RegisterUser> userMutableLiveData;

    public MutableLiveData<Boolean> onFinishCall = new MutableLiveData<>();
    public MutableLiveData<String> fullname = new MutableLiveData<>();
    public MutableLiveData<String> address = new MutableLiveData<>();
    public MutableLiveData<String> phoneNumber = new MutableLiveData<>();
    public MutableLiveData<String> email = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();


    public MutableLiveData<RegisterUser> getRegisterUser() {

        if (userMutableLiveData == null) {
            Log.d("ddd", "init called");
            userMutableLiveData = new MutableLiveData<>();
            user = new RegisterUser("", "", "", "", "");
        }
        return userMutableLiveData;

    }

    public MutableLiveData<Boolean> getFinish() {
        return onFinishCall;

    }

    RegisterUser user;

    public void onClickRegister(View view) {
        user = new RegisterUser(fullname.getValue(), address.getValue(), phoneNumber.getValue(), email.getValue(), password.getValue());
        //  Log.d("ddd", fullname.getValue());
        userMutableLiveData.setValue(user);
    }

    public void onLoginClick(View view) {
        onFinishCall.setValue(true);
    }

}
