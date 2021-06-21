package com.playdate.app.couple.ui.register.connect;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ConnectYourPartnerViewModel extends ViewModel {

    private MutableLiveData<Boolean> inviteClick = new MutableLiveData<>();
    private MutableLiveData<Boolean> BackClick = new MutableLiveData<>();
    private MutableLiveData<Boolean> NextClick = new MutableLiveData<>();
    private MutableLiveData<Boolean> joinClick = new MutableLiveData<>();


    public void setJoin(){
        joinClick.setValue(true);
    }

    public void setInvite() {
        inviteClick.setValue(true);
    }

    public MutableLiveData<Boolean> OnInviteClick() {

        return inviteClick;

    }

    public MutableLiveData<Boolean> OnJoinClick() {

        return joinClick;

    }

    public MutableLiveData<Boolean> OnNextClick() {

        return NextClick;

    }

    public MutableLiveData<Boolean> onBackClick() {

        return BackClick;

    }


    public void onInvite(View view) {
        inviteClick.setValue(true);
    }

    public void onBack(View view) {
        BackClick.setValue(true);
    }

    public void onNext(View view) {
        NextClick.setValue(true);
    }

    public void onJoin(View view) {
        joinClick.setValue(true);
    }

}
