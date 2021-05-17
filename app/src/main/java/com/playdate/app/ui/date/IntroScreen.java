package com.playdate.app.ui.date;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.playdate.app.R;

public class IntroScreen extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_intro);

        TextView tv_create_date = findViewById(R.id.tv_create_date);
        TextView tv_accept_date = findViewById(R.id.tv_accept_date);

    }
}
