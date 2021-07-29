package com.playdate.app.ui.my_profile_details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.playdate.app.R;
import com.playdate.app.ui.coupons.WrapContentViewPager;
import com.playdate.app.ui.my_profile_details.adapters.MyPhotosPagerAdapter;

import org.jetbrains.annotations.NotNull;

public class FragMyPhotos extends Fragment {
    public FragMyPhotos() {
    }


    WrapContentViewPager viewPager;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_tab_my_photos, container, false);

        MyPhotosPagerAdapter adapter = new MyPhotosPagerAdapter(getChildFragmentManager(), getActivity());
        viewPager = view.findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);


        TabLayout tabLayout = view.findViewById(R.id.tab);
        tabLayout.addTab(tabLayout.newTab().setText("My Feed"));
        tabLayout.addTab(tabLayout.newTab().setText("Liked Media"));
        tabLayout.setTabIndicatorFullWidth(false);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                viewPager.reMeasureCurrentPage(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                viewPager.reMeasureCurrentPage(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return view;

    }

}
