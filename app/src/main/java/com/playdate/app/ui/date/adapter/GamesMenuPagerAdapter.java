package com.playdate.app.ui.date.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.playdate.app.ui.date.games.FragPopularGames;

public class GamesMenuPagerAdapter extends FragmentStatePagerAdapter {
    int totalTabs;

    public GamesMenuPagerAdapter(@NonNull FragmentManager fm, int totalTabs) {
        super(fm);
        this.totalTabs = totalTabs;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FragPopularGames();
            case 1:
                return new FragPopularGames();
            case 2:
                return new FragPopularGames();
        }
        return null;
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
