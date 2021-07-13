package com.playdate.app.ui.date;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.playdate.app.R;
import com.playdate.app.ui.date.fragments.FragAcceptDatePartner;
import com.playdate.app.ui.date.fragments.FragIntroScreen;

import com.playdate.app.ui.date.fragments.FragSelectDates;
import com.playdate.app.ui.interfaces.OnBackPressed;
import com.playdate.app.ui.interfaces.OnInnerFragmentClicks;


public class DateBaseActivity extends AppCompatActivity implements OnInnerFragmentClicks, OnBackPressed {
    private FragmentManager fm;
    private FragmentTransaction ft;
    public static boolean fromChat;
    public static boolean fromNotification;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_base);
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        firstFrag();
    }

    private void firstFrag() {
        Log.d("FromChat", "firstFrag: " + fromChat);
        Fragment fragIntro;
        if (fromChat) {
            fragIntro = new FragSelectDates(true);
            fromChat = false;
        } else if (fromNotification) {
            fragIntro = new FragAcceptDatePartner();
            fromNotification = false;
        } else {
            fragIntro = new FragIntroScreen();

        }
        ft.add(R.id.fl_fragment, fragIntro);
        ft.commit();
    }

    @Override
    public void ReplaceFrag(Fragment fragment) {
        ft = fm.beginTransaction();
        ft.replace(R.id.fl_fragment, fragment, fragment.getClass().getSimpleName());
        ft.addToBackStack("tags");
        ft.setTransition(FragmentTransaction.TRANSIT_NONE);
        ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
        ft.commitAllowingStateLoss();

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void ReplaceFragWithStack(Fragment fragment) {

    }

    @Override
    public void NoFriends() {

    }

    @Override
    public void Reset() {

    }

    @Override
    public void loadProfile(String id) {

    }

    @Override
    public void loadMatchProfile(String UserID) {

    }

    @Override
    public void onBack() {
        onBackPressed();
    }
}
