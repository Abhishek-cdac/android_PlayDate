package com.playdate.app.ui.onboarding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;
import com.playdate.app.R;
import com.playdate.app.ui.login.LoginActivity;

import java.util.ArrayList;

public class GetStarted extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);

        ViewPager pager = findViewById(R.id.view_pager1);
        TabLayout tab = findViewById(R.id.tab_layout);
        Button started = findViewById(R.id.btnLogin);
        tab.setupWithViewPager(pager);

        ArrayList<Fragment> lst = new ArrayList<>();
        lst.add(new GetStartedFragment1());
        lst.add(new GetStartedFragment2());
        lst.add(new GetStartedFragment3());

        GetStartedAdapter adapter = new GetStartedAdapter(getSupportFragmentManager(), lst);
        pager.setAdapter(adapter);

        started.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GetStarted.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }
}