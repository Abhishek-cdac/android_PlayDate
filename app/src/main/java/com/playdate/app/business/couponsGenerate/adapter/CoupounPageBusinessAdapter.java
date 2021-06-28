package com.playdate.app.business.couponsGenerate.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.playdate.app.business.couponsGenerate.FragActiveCoupons;
import com.playdate.app.business.couponsGenerate.FragCouponCreated;
import com.playdate.app.business.couponsGenerate.FragCouponsGenerater;
import com.playdate.app.business.couponsGenerate.FragExpiredCoupon;
import com.playdate.app.ui.coupons.FragCouponStore;

public class CoupounPageBusinessAdapter extends FragmentPagerAdapter {
    int totalTabs;

    public CoupounPageBusinessAdapter(@NonNull FragmentManager fm, int totalTabs) {
        super(fm);
        this.totalTabs = totalTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FragActiveCoupons();
            case 1:
                return new FragExpiredCoupon();
        }
        return null;
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
