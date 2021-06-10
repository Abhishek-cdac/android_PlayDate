package com.playdate.app.ui.forgot_password;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ForgotViewModel extends ViewModel {
    public MutableLiveData<String> mobile = new MutableLiveData<>();
    public MutableLiveData<String> old_password = new MutableLiveData<>();
    public MutableLiveData<String> newPass = new MutableLiveData<>();
    public MutableLiveData<String> newPassConfirm = new MutableLiveData<>();
    public MutableLiveData<String> RegisterClick = new MutableLiveData<>();
    public MutableLiveData<Boolean> ChangeClick = new MutableLiveData<>();

    public MutableLiveData<String> getResetPass() {

        if (RegisterClick == null) {
            RegisterClick = new MutableLiveData<>();
        }
        return RegisterClick;

    }



    public MutableLiveData<Boolean> onChangeFinalClick() {

        if (ChangeClick == null) {
            ChangeClick = new MutableLiveData<>();
        }
        return ChangeClick;

    }

    public void onClickSend(View view) {
        RegisterClick.setValue(mobile.getValue());
    }

    public void onClickChange(View view) {
        ChangeClick.setValue(true);
    }
}
