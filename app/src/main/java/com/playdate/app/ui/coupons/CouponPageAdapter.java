package com.playdate.app.ui.coupons;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import org.jetbrains.annotations.NotNull;

public class CouponPageAdapter extends FragmentPagerAdapter {

    public CouponPageAdapter(@NonNull @NotNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @NotNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new FragCouponStore();
        } else {
            return new FragMyCoupons();
        }

    }

    @Override
    public int getCount() {
        return 2;
    }
}
