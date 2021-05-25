package com.playdate.app.ui.my_profile_details;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.playdate.app.ui.chat.request.FragInbox;
import com.playdate.app.ui.chat.request.FragRequest;
import com.playdate.app.util.session.SessionPref;

public class PhotoAdapter  extends FragmentStatePagerAdapter {
    int totalTabs;

    String UserID;
    public PhotoAdapter(@NonNull FragmentManager fm, int totalTabs, String UserID) {
        super(fm);
        this.UserID=UserID;
        this.totalTabs = totalTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FragMyUploadMedia(UserID);
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
