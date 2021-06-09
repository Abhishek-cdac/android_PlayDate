package com.playdate.app.ui.chat.request;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class RequestChatAdapter extends FragmentStatePagerAdapter {
    private final int totalTabs;
    private final ArrayList<Fragment> lstPages;

    public RequestChatAdapter(@NonNull FragmentManager fm, int totalTabs, ArrayList<Fragment> lstPages) {
        super(fm);
        this.totalTabs = totalTabs;
        this.lstPages = lstPages;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return lstPages.get(position);
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
