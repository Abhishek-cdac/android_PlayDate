package com.playdate.app.ui.register.interestin;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class InterestInViewModel extends ViewModel {

    public MutableLiveData<Boolean> RegisterClick = new MutableLiveData<>();

    private MutableLiveData<Boolean> maleClick = new MutableLiveData<>();
    private MutableLiveData<Boolean> BackClick = new MutableLiveData<>();
    private MutableLiveData<Boolean> NextClick = new MutableLiveData<>();
    private MutableLiveData<Boolean> femaleClick = new MutableLiveData<>();
    private MutableLiveData<Boolean> nonBin = new MutableLiveData<>();
    private MutableLiveData<Boolean> Register;

    public MutableLiveData<Boolean> onRegisterUser() {

        if (RegisterClick == null) {
            RegisterClick = new MutableLiveData<>();
        }
        return RegisterClick;

    }

    public MutableLiveData<Boolean> OnMaleClick() {

        return maleClick;

    }

    public MutableLiveData<Boolean> OnNonBinClick() {

        return nonBin;

    }

    public MutableLiveData<Boolean> OnFemaleClick() {

        return femaleClick;

    }

    public MutableLiveData<Boolean> OnNextClick() {

        return NextClick;

    }

    public MutableLiveData<Boolean> onBackClick() {

        return BackClick;

    }


    public void onMale(View view) {
        maleClick.setValue(true);
    }

    public void onBack(View view) {
        BackClick.setValue(true);
    }

    public void onNext(View view) {
        NextClick.setValue(true);
    }

    public void onFemale(View view) {
        femaleClick.setValue(true);
    }

    public void onNonBin(View view) {
        nonBin.setValue(true);
    }

}