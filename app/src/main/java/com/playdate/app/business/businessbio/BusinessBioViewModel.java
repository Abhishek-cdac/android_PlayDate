package com.playdate.app.business.businessbio;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BusinessBioViewModel extends ViewModel {

    public MutableLiveData<Boolean> NextClick = new MutableLiveData<>();
    public MutableLiveData<Boolean> BackClick = new MutableLiveData<>();
    public MutableLiveData<String> BioText = new MutableLiveData<>();
    public MutableLiveData<String> Bio = new MutableLiveData<>();


    public MutableLiveData<Boolean> OnNextClick() {

        return NextClick;

    }

    public MutableLiveData<Boolean> onBackClick() {

        return BackClick;

    }

    public MutableLiveData<String> OnBioInput() {

        return Bio;

    }

    public void onBack(View view) {
        BackClick.setValue(true);
    }

    public void onNext(View view) {
        NextClick.setValue(true);
    }
}
