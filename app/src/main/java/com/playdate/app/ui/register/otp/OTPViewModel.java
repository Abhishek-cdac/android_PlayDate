package com.playdate.app.ui.register.otp;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class OTPViewModel extends ViewModel {
    public MutableLiveData<Boolean> RegisterClick = new MutableLiveData<>();
    public MutableLiveData<Boolean> CountDownFinish = new MutableLiveData<>();
    public MutableLiveData<String> txtmobile = new MutableLiveData<>();
    public MutableLiveData<String> txtOTP = new MutableLiveData<>();
    public MutableLiveData<String> resendIn = new MutableLiveData<>();
    public MutableLiveData<Boolean> resendClick = new MutableLiveData<>();
    public MutableLiveData<Boolean> iv_backClick = new MutableLiveData<>();

    private MutableLiveData<Boolean> userMutableLiveData;
    private MutableLiveData<Boolean> Register;

    public MutableLiveData<Boolean> onRegisterUser() {

        if (RegisterClick == null) {
            RegisterClick = new MutableLiveData<>();
        }
        return RegisterClick;

    }

    public MutableLiveData<Boolean> checkCountDown() {
        return CountDownFinish;
    }

    public MutableLiveData<String> OnOTPTypeDone() {
        return txtOTP;
    }

    public void setMobile(String mobile) {
        String temp = mobile.substring(0, 3);
        String temp1 = mobile.substring(3, 6);
        String temp2 = mobile.substring(6, 10);
        txtmobile.setValue(temp + "-" + temp1 + "-" + temp2);
    }


    public void onClickSubmit(View view) {
        RegisterClick.setValue(true);
    }

    public void onBackClick(View view) {
        iv_backClick.setValue(true);
    }

    public void onResendOTP(View view) {
        resendClick.setValue(true);

    }

    public void setTimerText(String text) {
        resendIn.setValue(text);
    }

    public void startTimer() {
        new CountDownTimer(60000, 1000) {
            public void onTick(long millisUntilFinished) {
                if ((millisUntilFinished / 1000) < 10) {
                    setTimerText("You can resent OTP in 00:0" + (millisUntilFinished / 1000));
                } else {
                    setTimerText("You can resent OTP in 00:" + (millisUntilFinished / 1000));
                }

            }

            public void onFinish() {
                CountDownFinish.setValue(true);
            }
        }.start();
    }


}


