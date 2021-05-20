package com.playdate.app.ui.date.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.playdate.app.ui.date.games.FragAllTimeLeader;

public class LeaderBoardPagerAdapter extends FragmentStatePagerAdapter {
    int totalTabs;

    public LeaderBoardPagerAdapter(@NonNull FragmentManager fm, int totalTabs) {
        super(fm);
        this.totalTabs = totalTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new FragAllTimeLeader();
            case 1:
                return new FragAllTimeLeader();
            case 2:
                return new FragAllTimeLeader();
        }
        return null;
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
