package com.playdate.app.ui.date;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.playdate.app.R;
import com.playdate.app.ui.date.fragments.FragIntroScreen;
import com.playdate.app.ui.date.fragments.FragLocationConfirmation;
import com.playdate.app.ui.date.games.FragGameMenu;
import com.playdate.app.ui.interfaces.OnInnerFragmentClicks;

public class DateBaseActivity extends AppCompatActivity implements OnInnerFragmentClicks {
    FragmentManager fm;
    FragmentTransaction ft;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_base);
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();

        Fragment fragIntro = new FragIntroScreen();
//        Fragment fragIntro = new FragGameMenu();
        ft.add(R.id.fl_fragment, fragIntro);
        ft.commit();
    }

    @Override
    public void ReplaceFrag(Fragment fragment) {
        ft = fm.beginTransaction();
        ft.replace(R.id.fl_fragment, fragment, fragment.getClass().getSimpleName());
//        ft.addToBackStack("tags");
        ft.commitAllowingStateLoss();

    }

    @Override
    public void loadProfile() {

    }
}
