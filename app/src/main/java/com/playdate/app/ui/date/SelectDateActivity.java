package com.playdate.app.ui.date;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.playdate.app.R;

public class SelectDateActivity extends AppCompatActivity {
    RelativeLayout rl_inperson, rl_virtual;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_date);
        rl_inperson = findViewById(R.id.in_person);
        rl_virtual = findViewById(R.id.virtual);

        rl_inperson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectDateActivity.this,LocationTracing.class));
            }
        });
    }
}
