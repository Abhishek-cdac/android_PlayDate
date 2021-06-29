package com.playdate.app.ui.chat.request;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.playdate.app.R;
import com.playdate.app.ui.chat.ChattingAdapter;
import com.playdate.app.ui.dashboard.OnAPIResponce;
import com.playdate.app.ui.dashboard.data.CallAPI;
import com.playdate.app.ui.dashboard.fragments.FragSearchUser;
import com.playdate.app.ui.interfaces.OnInnerFragmentClicks;
import com.playdate.app.ui.notification_screen.FragNotification;

import java.util.ArrayList;

public class RequestChatFragment extends Fragment implements View.OnClickListener {
    ChattingAdapter chattingAdapter;
    FragInbox inboxFrag;
//    FragRequest requestFrag;
    TextView txt_count;


    public RequestChatFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_request_chat, container, false);
        chattingAdapter = new ChattingAdapter();

        ImageView back_anonymous = view.findViewById(R.id.back_anonymous);
        ImageView iv_chat_notification = view.findViewById(R.id.iv_chat_notification);
        EditText edt_search_chat = view.findViewById(R.id.edt_search_chat);

        TabLayout tabLayout = view.findViewById(R.id.tab);
        txt_count = view.findViewById(R.id.txt_count);
        ViewPager viewPager = view.findViewById(R.id.viewpager);
        RelativeLayout rl_page = view.findViewById(R.id.rl_page);

        tabLayout.addTab(tabLayout.newTab().setText("Inbox"));
//        tabLayout.addTab(tabLayout.newTab().setText("   Requests   "));

        ArrayList<Fragment> lstPages = new ArrayList<>();
        inboxFrag = new FragInbox();
//        requestFrag = new FragRequest();

        lstPages.add(inboxFrag);
//        lstPages.add(requestFrag);

        RequestChatAdapter pagerAdapter = new RequestChatAdapter(getChildFragmentManager(),1, lstPages);
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

        back_anonymous.setOnClickListener(this);
        iv_chat_notification.setOnClickListener(this);

        edt_search_chat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    if (null != inboxFrag) {
                        inboxFrag.filter(s.toString());
//                        requestFrag.filter(s.toString());
                    }
//                    if (null != inboxFrag || null != requestFrag) {
//                        inboxFrag.filter(s.toString());
//                        requestFrag.filter(s.toString());
//                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
//                inbox.filter(s.toString());
            }
        });


        return view;
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.back_anonymous) {
            getActivity().finish();
        } else if (id == R.id.iv_chat_notification) {
            OnInnerFragmentClicks ref = (OnInnerFragmentClicks) getActivity();
            ref.ReplaceFrag(new FragNotification("chat"));
        }

    }


}