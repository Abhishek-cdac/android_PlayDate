package com.playdate.app.business.dashboard_business;

import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.playdate.app.R;
import com.playdate.app.business.couponsGenerate.FragCouponParentBusiness;
import com.playdate.app.ui.dashboard.fragments.FragLanding;
import com.playdate.app.ui.interfaces.OnInnerFragmentClicks;

public class DashboardBusiness extends AppCompatActivity implements OnInnerFragmentClicks {

    Fragment CurrentFrag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_business);
        LinearLayout ll_coupon = findViewById(R.id.ll_coupon);

        Fragment fragOne;
        fragOne = new FragCouponParentBusiness();
        ReplaceFrag(fragOne);

    }

    @Override
    public void ReplaceFrag(Fragment fragment) {
        try {
            CurrentFrag = fragment;
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            if (fragmentManager.getFragments().size() > 0) {
                ft.replace(R.id.flFragment, fragment, fragment.getClass().getSimpleName());
            } else {
                ft.add(R.id.flFragment, fragment, fragment.getClass().getSimpleName());
            }
            ft.commitAllowingStateLoss();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void ReplaceFragWithStack(Fragment fragment) {

    }

    @Override
    public void NoFriends() {

    }

    @Override
    public void Reset() {

    }

    @Override
    public void loadProfile(String UserID) {

    }

    @Override
    public void loadMatchProfile(String UserID) {

    }
}
