package com.playdate.app.ui.dashboard.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.playdate.app.ui.dashboard.more_suggestion.FragInvite;
import com.playdate.app.ui.dashboard.more_suggestion.FragSuggested;
import com.playdate.app.ui.login.LoginActivity;

public class MoreSuggestionPagerAdapter extends FragmentStatePagerAdapter {
    private final int totalTabs;

    public MoreSuggestionPagerAdapter(@NonNull FragmentManager fm, int totalTabs) {
        super(fm);
        this.totalTabs = totalTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FragSuggested();
            case 1:
                return new FragInvite();
        }
        return new Fragment();
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
