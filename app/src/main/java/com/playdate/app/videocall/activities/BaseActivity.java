package com.playdate.app.videocall.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.playdate.app.util.MyApplication;
import com.playdate.app.videocall.util.QBResRequestExecutor;
import com.playdate.app.videocall.utils.SharedPrefsHelper;

public abstract class BaseActivity extends AppCompatActivity {

    protected SharedPrefsHelper sharedPrefsHelper;
    protected QBResRequestExecutor requestExecutor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestExecutor = MyApplication.getInstance().getQbResRequestExecutor();
        sharedPrefsHelper = SharedPrefsHelper.getInstance();
    }

}