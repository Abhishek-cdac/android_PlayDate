package com.playdate.app.business.businessphoto;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BusinessUploadPhotoViewModel extends ViewModel {


    public MutableLiveData<Boolean> NextClick = new MutableLiveData<>();
    public MutableLiveData<Boolean> BackClick = new MutableLiveData<>();
    public MutableLiveData<Boolean> Gallery = new MutableLiveData<>();
    public MutableLiveData<Boolean> Change = new MutableLiveData<>();
    public MutableLiveData<Boolean> Camera = new MutableLiveData<>();


    public MutableLiveData<Boolean> OnNextClick() {

        return NextClick;

    }

    public MutableLiveData<Boolean> onBackClick() {

        return BackClick;

    }

    public MutableLiveData<Boolean> onGalleryClick() {

        return Gallery;

    }public MutableLiveData<Boolean> onCameraClick() {

        return Camera;

    }

    public MutableLiveData<Boolean> OnChangeClick() {

        return Change;

    }

    public void onBack(View view) {
        BackClick.setValue(true);
    }

    public void onNext(View view) {
        NextClick.setValue(true);
    }

    public void OnGallery(View view) {
        Gallery.setValue(true);
    }

    public void OnChange(View view) {
        Change.setValue(true);
    }

    public void OnCamera(View view) {
        Camera.setValue(true);
    }

}
