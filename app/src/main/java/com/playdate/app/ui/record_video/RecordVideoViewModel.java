package com.playdate.app.ui.record_video;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RecordVideoViewModel extends ViewModel {

    private final MutableLiveData<Boolean> NextClick = new MutableLiveData<>();


    public MutableLiveData<Boolean> OnNextClick() {

        return NextClick;

    }

    public void onNext(View view) {
        NextClick.setValue(true);
    }
}
