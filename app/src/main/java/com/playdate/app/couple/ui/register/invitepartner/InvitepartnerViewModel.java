package com.playdate.app.couple.ui.register.invitepartner;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class InvitepartnerViewModel extends ViewModel {
    private final MutableLiveData<Boolean> BackClick = new MutableLiveData<>();
    private final MutableLiveData<Boolean> NextClick = new MutableLiveData<>();

    public MutableLiveData<Boolean> OnNextClick() {
        return NextClick;
    }

    public MutableLiveData<Boolean> onBackClick() {
        return BackClick;
    }


    public void onBack(View view) {
        BackClick.setValue(true);
    }

    public void onNext(View view) {
        NextClick.setValue(true);
    }

}
