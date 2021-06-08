package com.playdate.app.ui.dashboard.premium;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.playdate.app.ui.dashboard.more_suggestion.FragInvite;

import org.jetbrains.annotations.NotNull;

public class PremiunAdapter extends FragmentPagerAdapter {
    private final FragInvite fragInvite;
    public PremiunAdapter(@NonNull @NotNull FragmentManager fm, FragInvite fragInvite) {
        super(fm);
        this.fragInvite=fragInvite;
    }

    @Override
    public int getCount() {
        return 3;
    }


    @NonNull
    @NotNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FragPremium1(fragInvite);
            case 1:
                return new FragPremium2(fragInvite);
            case 2:
                return new FragPremium3(fragInvite);
        }
        return new Fragment();
    }
}
