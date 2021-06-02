package com.playdate.app.couple.ui.register.coupleusername;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CoupleUserNameViewModel extends ViewModel {
    public MutableLiveData<Boolean> RegisterClick = new MutableLiveData<>();

    private MutableLiveData<Boolean> SingleClick = new MutableLiveData<>();
    private MutableLiveData<Boolean> BackClick = new MutableLiveData<>();
    private MutableLiveData<Boolean> NextClick = new MutableLiveData<>();
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
//
//    public void onTaken(View view) {
//        TakenClick.setValue(true);
//    }
}
