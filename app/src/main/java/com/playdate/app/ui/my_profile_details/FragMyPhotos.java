package com.playdate.app.ui.my_profile_details;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.playdate.app.R;
import com.playdate.app.ui.coupons.WrapContentViewPager;
import com.playdate.app.ui.my_profile_details.adapters.MyPhotosPagerAdapter;
import com.playdate.app.util.session.SessionPref;

import org.jetbrains.annotations.NotNull;

public class FragMyPhotos extends Fragment implements View.OnClickListener {
    public FragMyPhotos() {
    }

//    private View view_1;
//    private View view_2;
//
//    private TextView txt_header1;
//    private TextView txt_header2;
    int Selected=0;
    WrapContentViewPager viewPager;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_tab_my_photos, container, false);

//        view_1 = view.findViewById(R.id.view_1);
//        view_2 = view.findViewById(R.id.view_2);
//        txt_header1 = view.findViewById(R.id.txt_header1);
//        txt_header2 = view.findViewById(R.id.txt_header2);
        MyPhotosPagerAdapter adapter=new MyPhotosPagerAdapter(getChildFragmentManager(),getActivity());
        viewPager = view.findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);
//        txt_header1.setOnClickListener(this);
//        txt_header2.setOnClickListener(this);
//        FragmentManager fm = getChildFragmentManager();
//        FragmentTransaction ft = fm.beginTransaction();
//        SessionPref pref = SessionPref.getInstance(getActivity());
//        ft.add(R.id.fl_myPhotos, new FragMyUploadMedia(pref.getStringVal(SessionPref.LoginUserID)));
//        ft.commit();


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

    @Override
    public void onClick(View v) {
        int id = v.getId();
//        if (id == R.id.txt_header1) {
//            if(Selected==0){
//                return;
//            }
//            Selected=0;
//            txt_header1.setTypeface(Typeface.DEFAULT_BOLD);
//            txt_header2.setTypeface(Typeface.DEFAULT);
//            view_1.setBackgroundColor(getResources().getColor(R.color.color_pink));
//            view_2.setBackgroundColor(getResources().getColor(R.color.color_grey));
//            viewPager.setCurrentItem(0);
//
//        } else {
//            if(Selected==1){
//                return;
//            }
//            Selected=1;
//            txt_header2.setTypeface(Typeface.DEFAULT_BOLD);
//            txt_header1.setTypeface(Typeface.DEFAULT);
//            view_1.setBackgroundColor(getResources().getColor(R.color.color_grey));
//            view_2.setBackgroundColor(getResources().getColor(R.color.color_pink));
//            viewPager.setCurrentItem(1);
//        }
    }
}
