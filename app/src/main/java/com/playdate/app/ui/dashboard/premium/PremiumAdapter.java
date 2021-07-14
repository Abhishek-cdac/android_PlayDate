package com.playdate.app.ui.dashboard.premium;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.playdate.app.model.PremiumPlans;
import com.playdate.app.ui.dashboard.more_suggestion.FragInvite;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class PremiumAdapter extends FragmentPagerAdapter {
    private final FragInvite fragInvite;
    ArrayList<PremiumPlans> lstPremium;
    public PremiumAdapter(@NonNull @NotNull FragmentManager fm, FragInvite fragInvite, ArrayList<PremiumPlans> lstPremium) {
        super(fm);
        this.fragInvite=fragInvite;
        this.lstPremium=lstPremium;
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
                return new FragPremium1(fragInvite,lstPremium.get(0));
            case 1:
                return new FragPremium2(fragInvite,lstPremium.get(1));
            case 2:
                return new FragPremium3(fragInvite,lstPremium.get(2));
        }
        return new Fragment();
    }
}
