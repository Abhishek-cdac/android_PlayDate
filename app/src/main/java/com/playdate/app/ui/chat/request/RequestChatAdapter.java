package com.playdate.app.ui.chat.request;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.playdate.app.ui.chat.request.FragInbox;
import com.playdate.app.ui.dashboard.more_suggestion.FragInvite;
import com.playdate.app.ui.dashboard.more_suggestion.FragSuggested;

public class RequestChatAdapter  extends FragmentStatePagerAdapter {
    int totalTabs;

    public RequestChatAdapter(@NonNull FragmentManager fm, int totalTabs) {
        super(fm);
        this.totalTabs = totalTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FragInbox();
            case 1:
                return new FragRequest();
        }
        return null;
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}