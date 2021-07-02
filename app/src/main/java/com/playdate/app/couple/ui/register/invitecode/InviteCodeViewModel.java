package com.playdate.app.couple.ui.register.invitecode;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class InviteCodeViewModel extends ViewModel {

    public MutableLiveData<Boolean> RegisterClick = new MutableLiveData<>();

    private final MutableLiveData<Boolean> BackClick = new MutableLiveData<>();
    private final MutableLiveData<Boolean> SubmitClick = new MutableLiveData<>();
    private final MutableLiveData<Boolean> ResendClick = new MutableLiveData<>();
    public MutableLiveData<String> txtInviteCode = new MutableLiveData<>();
    public MutableLiveData<String> resendIn = new MutableLiveData<>();


    public MutableLiveData<Boolean> onRegisterUser() {

        if (RegisterClick == null) {
            RegisterClick = new MutableLiveData<>();
        }
        return RegisterClick;

    }
    public MutableLiveData<Boolean> OnSubmitClick() {

        return SubmitClick;

    }

    public MutableLiveData<Boolean> OnResendClick() {

        return ResendClick;

    }

    public MutableLiveData<Boolean> onBackClick() {

        return BackClick;

    }


    public void onBack(View view) {
        BackClick.setValue(true);
    }

    public void onSubmit(View view) {
        SubmitClick.setValue(true);
    }

    public void onResend(View view) {
        ResendClick.setValue(true);
    }


}
