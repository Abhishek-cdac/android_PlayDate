package com.playdate.app.ui.my_profile_details;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.playdate.app.ui.chat.request.FragInbox;
import com.playdate.app.ui.chat.request.FragRequest;

public class PhotoAdapter  extends FragmentStatePagerAdapter {
    int totalTabs;

    public PhotoAdapter(@NonNull FragmentManager fm, int totalTabs) {
        super(fm);
        this.totalTabs = totalTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FragSavedPost();
            case 1:
                return new FragSavedPost();
        }
        return null;
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
