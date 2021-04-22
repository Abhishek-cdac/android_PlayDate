package com.playdate.app.ui.register.interest;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class InterestViewModel extends ViewModel {

    private MutableLiveData<Boolean> NextClick = new MutableLiveData<>();
    public MutableLiveData<Boolean> OnNextClick() {

        return NextClick;

    }

    public void onNext(View view) {
        NextClick.setValue(true);
    }

    public void onBack(View view) {
//        BackClick.setValue(true);
    }

}
