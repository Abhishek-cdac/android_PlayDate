package com.playdate.app.business.couponsGenerate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.playdate.app.R;
import com.playdate.app.business.couponsGenerate.adapter.ActiveCouponsAdapter;
import com.playdate.app.business.couponsGenerate.adapter.CoupounPageBusinessAdapter;
import com.playdate.app.ui.coupons.CoupounPageAdapter;
import com.playdate.app.ui.coupons.WrapContentViewPager;
import com.playdate.app.ui.dashboard.adapter.MoreSuggestionPagerAdapter;
import com.playdate.app.ui.dashboard.adapter.RestaurentListAdapter;
import com.playdate.app.ui.dialogs.BoosterDialogDM;
import com.playdate.app.ui.interfaces.OnInnerFragmentClicks;
import com.playdate.app.ui.notification_screen.FragNotification;
import com.playdate.app.util.session.SessionPref;

public class FragCouponParentBusiness extends Fragment {

    //    WrapContentViewPager viewpager;
    ViewPager viewpager;
    private RecyclerView rv_coupons_list;
    private SessionPref pref;
    private TextView txt_coupons;
    private TextView generator;
    ImageView iv_dashboard_notification;
    TextView txt_count;
    EditText edt_search;
    TabLayout tabLayout;
    private Fragment CurrentFrag;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_coupon_parent_business, container, false);
        viewpager = view.findViewById(R.id.viewpager);
        iv_dashboard_notification = view.findViewById(R.id.iv_dashboard_notification);
        txt_coupons = view.findViewById(R.id.txt_coupons);
        txt_count = view.findViewById(R.id.txt_count);
        generator = view.findViewById(R.id.generator);
        edt_search = view.findViewById(R.id.edt_search);
        pref = SessionPref.getInstance(getActivity());
        tabLayout = view.findViewById(R.id.tab);

        tabLayout.addTab(tabLayout.newTab().setText(" Active "));
        tabLayout.addTab(tabLayout.newTab().setText("   Expired   "));

        generator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FragCouponsGenerater(getActivity()).show();
            }
        });

        CoupounPageBusinessAdapter pagerAdapter = new CoupounPageBusinessAdapter(getChildFragmentManager(), tabLayout.getTabCount());
        viewpager.setAdapter(pagerAdapter);
        viewpager.setCurrentItem(0);
        tabLayout.setTabIndicatorFullWidth(false);
        viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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

//        CoupounPageBusinessAdapter adapter = new CoupounPageBusinessAdapter(getChildFragmentManager());
//        viewpager.setAdapter(adapter);
//
//        txt_store.setOnClickListener(this);
//        txt_my_coupon.setOnClickListener(this);
//        iv_dashboard_notification.setOnClickListener(this);
//
//        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                viewpager.reMeasureCurrentPage(position);
//                if (position == 0) {
//                    txt_store.setTextColor(getResources().getColor(R.color.white));
//                    txt_store.setBackground(getResources().getDrawable(R.drawable.menu_button));
//                    txt_my_coupon.setBackground(null);
//                    txt_my_coupon.setTextColor(getResources().getColor(android.R.color.darker_gray));
//                } else {
//                    txt_my_coupon.setTextColor(getResources().getColor(R.color.white));
//                    txt_my_coupon.setBackground(getResources().getDrawable(R.drawable.menu_button));
//                    txt_store.setBackground(null);
//                    txt_store.setTextColor(getResources().getColor(android.R.color.darker_gray));
//                }
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
//
//        return view;
//    }
//
//    @Override
//    public void onClick(View v) {
//        if (v.getId() == R.id.txt_store) {
//            viewpager.setCurrentItem(0);
//        }
////        else if (v.getId() == R.id.iv_dashboard_notification) {
////            ReplaceFrag(new FragNotification("Coupons"));
////        }
//        else if (v.getId() == R.id.txt_my_coupon) {
//
//            new FragCouponsGenerater(getActivity()).show();
////            viewpager.setCurrentItem(1);
//        }
//
//    }
//
//    @Override
//    public void ReplaceFrag(Fragment fragment) {
//        try {
//            CurrentFrag = fragment;
//            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//            FragmentTransaction ft = fragmentManager.beginTransaction();
//            if (fragmentManager.getFragments().size() > 0) {
//                ft.replace(R.id.flFragment, fragment, fragment.getClass().getSimpleName());
//            } else {
//                ft.add(R.id.flFragment, fragment, fragment.getClass().getSimpleName());
//            }
//            ft.commitAllowingStateLoss();
//
//            //  mSwipeRefreshLayout.setEnabled(CurrentFrag.getClass().getSimpleName().equals("FragSocialFeed"));
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    @Override
//    public void ReplaceFragWithStack(Fragment fragment) {
//
//    }
//
//    @Override
//    public void NoFriends() {
//
//    }
//
//    @Override
//    public void Reset() {
//
//    }
//
//    @Override
//    public void loadProfile(String UserID) {
//
//    }
//
//    @Override
//    public void loadMatchProfile(String UserID) {
//
//    }

    }
}