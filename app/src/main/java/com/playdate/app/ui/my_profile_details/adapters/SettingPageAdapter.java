package com.playdate.app.ui.my_profile_details.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.playdate.app.ui.my_profile_details.FragMyProfileDetails;
import com.playdate.app.ui.my_profile_details.FragMyProfilePayments;
import com.playdate.app.ui.my_profile_details.FragMyProfilePersonal;

import org.jetbrains.annotations.NotNull;

public class SettingPageAdapter extends FragmentPagerAdapter {
    public SettingPageAdapter(@NonNull @NotNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @NotNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new FragMyProfileDetails();
        } else if (position == 1) {
            return new FragMyProfilePersonal();
        }  else if (position == 2) {
            return new FragMyProfilePersonal();
        }
        return new Fragment();
    }

    @Override
    public int getCount() {
        return 2;
    }
}
