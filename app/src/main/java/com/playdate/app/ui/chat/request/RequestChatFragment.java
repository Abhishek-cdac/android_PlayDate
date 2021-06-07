package com.playdate.app.ui.chat.request;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.google.android.material.tabs.TabLayout;
import com.playdate.app.R;
import com.playdate.app.model.chat_models.ChatExample;
import com.playdate.app.ui.chat.ChattingAdapter;
import com.playdate.app.ui.dashboard.fragments.FragSearchUser;
import com.playdate.app.ui.interfaces.OnInnerFragmentClicks;
import com.playdate.app.util.common.CommonClass;

import java.util.ArrayList;

public class RequestChatFragment extends Fragment {
    ChattingAdapter chattingAdapter;
    FragInbox inbox;


    public RequestChatFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_request_chat, container, false);
        chattingAdapter = new ChattingAdapter();
        EditText edt_search_chat = view.findViewById(R.id.edt_search_chat);

        TabLayout tabLayout = view.findViewById(R.id.tab);
        ViewPager viewPager = view.findViewById(R.id.viewpager);
        RelativeLayout rl_page = view.findViewById(R.id.rl_page);

        tabLayout.addTab(tabLayout.newTab().setText("Inbox"));
        tabLayout.addTab(tabLayout.newTab().setText("   Requests   "));
//        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        int height = new CommonClass().getScreenHeight(getActivity());


        int m1 = (int) getResources().getDimension(R.dimen._15sdp);
        int m2 = (int) getResources().getDimension(R.dimen._10sdp);
        int m3 = (int) getResources().getDimension(R.dimen._20sdp);
        int m4 = (int) getResources().getDimension(R.dimen._20sdp);
        int m5 = (int) getResources().getDimension(R.dimen._60sdp);
        int m6 = (int) getResources().getDimension(R.dimen._75sdp);

        rl_page.getLayoutParams().height = height - (m1 + m2 + m3 + m4 + m5 + m6);

        RequestChatAdapter pagerAdapter = new RequestChatAdapter(getChildFragmentManager(), tabLayout.getTabCount());
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

//        edt_search_chat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                OnInnerFragmentClicks ref = (OnInnerFragmentClicks) getActivity();
//                ref.ReplaceFrag(new FragSearchUser());
//
//            }
//        });
        edt_search_chat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                inbox = new FragInbox();
////                inbox.setFilters(s);
////                chattingAdapter.getFilter().filter(s);
//                inbox.filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
//                inbox.filter(s.toString());
            }
        });


        return view;
    }


}