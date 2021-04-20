package com.playdate.app.ui.onboarding;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class GetStartedAdapter extends FragmentStatePagerAdapter {
    ArrayList<Fragment> lst;


    public GetStartedAdapter(@NonNull FragmentManager fm, ArrayList<Fragment> lst) {
        super(fm);
        this.lst = lst;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        return lst.get(position);
    }

    @Override
    public int getCount() {
        return lst.size();
    }
}
