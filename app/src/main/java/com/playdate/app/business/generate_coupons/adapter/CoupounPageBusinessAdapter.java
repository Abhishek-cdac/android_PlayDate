package com.playdate.app.business.generate_coupons.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.playdate.app.business.generate_coupons.FragActiveCoupons;
import com.playdate.app.business.generate_coupons.FragExpiredCoupon;
import com.playdate.app.model.GetBusinessCouponData;

import java.util.ArrayList;

public class CoupounPageBusinessAdapter extends FragmentPagerAdapter {
    int totalTabs;



    private ArrayList<GetBusinessCouponData> lst;
    public CoupounPageBusinessAdapter(@NonNull FragmentManager fm, int totalTabs) {
        super(fm);
        this.totalTabs = totalTabs;

    }/* public CoupounPageBusinessAdapter(@NonNull FragmentManager fm, int totalTabs, ArrayList<GetBusinessCouponData> lst) {
        super(fm);
        this.totalTabs = totalTabs;
        this.lst=lst;
    }*/

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
