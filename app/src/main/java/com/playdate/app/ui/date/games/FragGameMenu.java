package com.playdate.app.ui.date.games;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.playdate.app.R;
import com.playdate.app.ui.date.adapter.GamesMenuPagerAdapter;
import com.playdate.app.ui.interfaces.OnInnerFragmentClicks;

public class FragGameMenu extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_game_menu, container, false);

        ImageView iv_shuffle;
        ImageView iv_leaderboard;
        TabLayout tabLayout;
        TabLayout tab_layout2;
        ViewPager viewPager;

        iv_shuffle = view.findViewById(R.id.iv_shuffle);
        iv_leaderboard = view.findViewById(R.id.iv_leaderboard);
        tabLayout = view.findViewById(R.id.tab);
        tab_layout2 = view.findViewById(R.id.tab_layout);
        viewPager = view.findViewById(R.id.viewpager);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.popular));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.new_games));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.trending));

        tab_layout2.setupWithViewPager(viewPager);

        GamesMenuPagerAdapter adapter = new GamesMenuPagerAdapter(getChildFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
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

        iv_shuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnInnerFragmentClicks frag = (OnInnerFragmentClicks) getActivity();
                frag.ReplaceFrag(new FragCoinScreen());
            }
        });

        iv_leaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnInnerFragmentClicks frag = (OnInnerFragmentClicks) getActivity();
                frag.ReplaceFrag(new FragGameLeaderBoard());
            }
        });

        return view;
    }
}
