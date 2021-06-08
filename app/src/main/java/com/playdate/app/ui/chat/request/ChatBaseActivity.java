package com.playdate.app.ui.chat.request;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.playdate.app.R;
import com.playdate.app.ui.date.fragments.FragIntroScreen;
import com.playdate.app.ui.interfaces.OnInnerFragmentClicks;

public class ChatBaseActivity extends AppCompatActivity implements OnInnerFragmentClicks {
    FragmentManager fm;
    FragmentTransaction ft;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_base);

        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();

        Fragment fragIntro = new RequestChatFragment();

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
    public void ReplaceFragWithStack(Fragment fragment) {
        ft = fm.beginTransaction();
        ft.replace(R.id.fl_fragment, fragment, fragment.getClass().getSimpleName());
        ft.addToBackStack("tags");
        ft.commitAllowingStateLoss();
    }

    @Override
    public void NoFriends() {

    }

    @Override
    public void Reset() {

    }

    @Override
    public void loadProfile(String UserID) {

    }

    @Override
    public void loadMatchProfile(String UserID) {

    }
}
