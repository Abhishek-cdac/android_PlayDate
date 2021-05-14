package com.playdate.app.ui.date;

import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.playdate.app.R;

public class DatingActivity extends AppCompatActivity {
    DatingViewModel viewModel;


    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel=new DatingViewModel();
        setContentView(R.layout.activity_dating);
    }
}
