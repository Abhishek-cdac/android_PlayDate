package com.playdate.app.ui.onboarding;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class OnBoardingAdapter extends FragmentPagerAdapter {

//    private final Context mContext;

    public OnBoardingAdapter(final Context context, final FragmentManager fm) {
        super(fm);
//        mContext = context.getApplicationContext();
    }

    @Override
    public Fragment getItem(int position) {
        return new OnBoardingImageFragment(position);
    }




    @Override
    public int getCount() {
        return 4;
    }
}