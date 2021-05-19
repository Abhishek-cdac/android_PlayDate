package com.playdate.app.ui.date.games;

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
import com.playdate.app.ui.date.adapter.LeaderBoardPagerAdapter;

public class FragGameLeaderBoard extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_game_leaderboard, container, false);
        TabLayout tabs = view.findViewById(R.id.tabs);
        ViewPager viewpager = view.findViewById(R.id.viewpager);

        tabs.addTab(tabs.newTab().setText("All Time"));
        tabs.addTab(tabs.newTab().setText("This Week"));
        tabs.addTab(tabs.newTab().setText("This Month"));

        LeaderBoardPagerAdapter adapter = new LeaderBoardPagerAdapter(getChildFragmentManager(), tabs.getTabCount());
        viewpager.setAdapter(adapter);
        viewpager.setCurrentItem(0);

        viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewpager.setCurrentItem(tab.getPosition());
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
