package com.playdate.app.ui.dashboard;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.playdate.app.R;
import com.playdate.app.ui.dashboard.fragments.FragLanding;
import com.playdate.app.ui.dashboard.more_suggestion.FragMoreSuggestion;

public class DashboardActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

//        Fragment fragOne = new FragLanding();
        Fragment fragOne = new FragMoreSuggestion();
        Bundle arguments = new Bundle();
        arguments.putBoolean("shouldYouCreateAChildFragment", true);
        fragOne.setArguments(arguments);
        ft.add(R.id.flFragment, fragOne);
        ft.commit();
    }
}
