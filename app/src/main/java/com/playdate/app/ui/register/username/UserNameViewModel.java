package com.playdate.app.ui.register.username;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UserNameViewModel extends ViewModel {
    public MutableLiveData<Boolean> RegisterClick = new MutableLiveData<>();

    private final MutableLiveData<Boolean> SingleClick = new MutableLiveData<>();
    private final MutableLiveData<Boolean> BackClick = new MutableLiveData<>();
    private final MutableLiveData<Boolean> NextClick = new MutableLiveData<>();
    public MutableLiveData<String> UserName = new MutableLiveData<>();
    private MutableLiveData<Boolean> Register;

    public MutableLiveData<Boolean> onRegisterUser() {

        if (RegisterClick == null) {
            RegisterClick = new MutableLiveData<>();
        }
        return RegisterClick;

    }

    public MutableLiveData<Boolean> OnSingleClick() {

        return SingleClick;

    }

    public MutableLiveData<String> OnUserNameInput() {

        return UserName;

    }

    public MutableLiveData<Boolean> OnNextClick() {

        return NextClick;

    }

    public MutableLiveData<Boolean> onBackClick() {

        return BackClick;

    }


    public void onSingle(View view) {
        SingleClick.setValue(true);
    }

    public void onBack(View view) {
        BackClick.setValue(true);
    }

    public void onNext(View view) {
        NextClick.setValue(true);
    }
}
