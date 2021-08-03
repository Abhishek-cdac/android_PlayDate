package com.playdate.app.ui.register.gender;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


public class GenderViewModel extends ViewModel {

    public MutableLiveData<Boolean> RegisterClick = new MutableLiveData<>();
    private final MutableLiveData<Boolean> MaleClick = new MutableLiveData<>();
    private final MutableLiveData<Boolean> BackClick = new MutableLiveData<>();
    private final MutableLiveData<Boolean> NextClick = new MutableLiveData<>();
    private final MutableLiveData<Boolean> FemaleClick = new MutableLiveData<>();
    private final MutableLiveData<Boolean> NBClick = new MutableLiveData<>();

    private MutableLiveData<Boolean> Register;

    public MutableLiveData<Boolean> onRegisterUser() {

        if (RegisterClick == null) {
            RegisterClick = new MutableLiveData<>();
        }
        return RegisterClick;

    }

    public MutableLiveData<Boolean> OnMaleClick() {

        return MaleClick;

    }

    public MutableLiveData<Boolean> OnFeMaleClick() {

        return FemaleClick;

    }

    public MutableLiveData<Boolean> OnNBClick() {

        return NBClick;

    }

    public MutableLiveData<Boolean> OnNextClick() {

        return NextClick;

    }

    public MutableLiveData<Boolean> onBackClick() {

        return BackClick;

    }


    public void OnMale(View view) {
        MaleClick.setValue(true);
    }

    public void onBack(View view) {
        BackClick.setValue(true);
    }

    public void onNext(View view) {
        NextClick.setValue(true);
    }

    public void OnFemale(View view) {
        FemaleClick.setValue(true);
    }

    public void OnNB(View view) {
        NBClick.setValue(true);
    }

}
