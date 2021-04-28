package com.playdate.app.ui.dashboard.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.playdate.app.ui.dashboard.more_suggestion.FragInvite;
import com.playdate.app.ui.dashboard.more_suggestion.FragSuggested;

public class MoreSuggestionPagerAdapter extends FragmentStatePagerAdapter {
    int totalTabs;

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
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }
}
