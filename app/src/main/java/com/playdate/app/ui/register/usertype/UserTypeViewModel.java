package com.playdate.app.ui.register.usertype;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


public class UserTypeViewModel extends ViewModel {
    public MutableLiveData<Boolean> RegisterClick = new MutableLiveData<>();

    private final MutableLiveData<Boolean> SingleClick = new MutableLiveData<>();
    private final MutableLiveData<Boolean> BackClick = new MutableLiveData<>();
    private final MutableLiveData<Boolean> NextClick = new MutableLiveData<>();
    private final MutableLiveData<Boolean> BusinessClick = new MutableLiveData<>();
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

    public MutableLiveData<Boolean> onBusinessClick() {

        return BusinessClick;

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

    public void onBuniness(View view) {
        BusinessClick.setValue(true);
    }

}
