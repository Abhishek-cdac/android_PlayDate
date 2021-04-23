package com.playdate.app.ui.dashboard.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.ui.dashboard.fragments.FragSuggestion;
import com.playdate.app.ui.onboarding.OnBoardingImageFragment;

import java.util.ArrayList;

public class SuggestionAdapter extends FragmentPagerAdapter {

    private Context mContext;

    public SuggestionAdapter(final Context context, final FragmentManager fm) {
        super(fm);
        mContext = context.getApplicationContext();
    }

    @Override
    public Fragment getItem(int position) {
        return new FragSuggestion();
    }


    @Override
    public int getCount() {
        return 15;
    }
}