package com.playdate.app.ui.dashboard.more_suggestion;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.playdate.app.R;
import com.playdate.app.ui.dashboard.adapter.MoreSuggestionPagerAdapter;
import com.playdate.app.util.common.CommonClass;

public class FragMoreSuggestion extends Fragment {


    public FragMoreSuggestion() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_more_suggestion, container, false);
        TabLayout tabLayout = view.findViewById(R.id.tab);
        ViewPager viewPager = view.findViewById(R.id.viewpager);
        RelativeLayout rl_page = view.findViewById(R.id.rl_page);

        tabLayout.addTab(tabLayout.newTab().setText("Suggested"));
        tabLayout.addTab(tabLayout.newTab().setText("   Invite   "));
//      tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        int height = new CommonClass().getScreenHeight(getActivity());


        int m1 = (int) getResources().getDimension(R.dimen._15sdp);
        int m2 = (int) getResources().getDimension(R.dimen._10sdp);
        int m3 = (int) getResources().getDimension(R.dimen._20sdp);
        int m4 = (int) getResources().getDimension(R.dimen._20sdp);
        int m5 = (int) getResources().getDimension(R.dimen._60sdp);
        int m6 = (int) getResources().getDimension(R.dimen._75sdp);

        rl_page.getLayoutParams().height = height - (m1 + m2 + m3 + m4 + m5 + m6);

        MoreSuggestionPagerAdapter pagerAdapter = new MoreSuggestionPagerAdapter(getChildFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(0);
        tabLayout.setTabIndicatorFullWidth(false);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
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
