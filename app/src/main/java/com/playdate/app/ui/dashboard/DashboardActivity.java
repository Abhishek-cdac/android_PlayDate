package com.playdate.app.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.playdate.app.R;
import com.playdate.app.ui.anonymous_question.AnoQuesCreateActivity;
import com.playdate.app.ui.card_swipe.FragCardSwipe;
import com.playdate.app.ui.dashboard.adapter.FriendAdapter;
import com.playdate.app.ui.dashboard.fragments.FragLanding;
import com.playdate.app.ui.interfaces.OnInnerFragmentClicks;
import com.playdate.app.ui.my_profile_details.FragInstaLikeProfile;
import com.playdate.app.ui.my_profile_details.FragMyProfileDetails;
import com.playdate.app.ui.my_profile_details.FragMyProfilePayments;
import com.playdate.app.ui.my_profile_details.FragMyProfilePersonal;
import com.playdate.app.ui.register.relationship.RelationActivity;
import com.playdate.app.ui.social.FragSocialFeed;

public class DashboardActivity extends AppCompatActivity implements OnInnerFragmentClicks, View.OnClickListener {
    FragmentManager fm;
    FragmentTransaction ft;
    TextView txt_match;
    TextView txt_social;
    TextView txt_payment;
    TextView txt_account;
    TextView txt_personal;
    ImageView iv_love;
    ImageView iv_profile_sett;
    ImageView iv_plus;
    ImageView iv_play_date_logo;
    ImageView iv_cancel;
    ImageView iv_create_ano_ques;

    SwipeRefreshLayout mSwipeRefreshLayout;
    LinearLayout ll_mainMenu;
    LinearLayout ll_friends;
    LinearLayout ll_profile_menu;
    LinearLayout ll_option_love;
    LinearLayout ll_profile_support;
    LinearLayout ll_love_bottom;
    LinearLayout ll_profile_insta;
    LinearLayout ll_profile_drop_menu;

    LinearLayout ll_take_photo;
    LinearLayout ll_upload_photo;
    LinearLayout ll_Record_video;
    LinearLayout ll_upload_video;


    RelativeLayout rl_main;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ll_profile_insta = findViewById(R.id.ll_profile_insta);
        rl_main = findViewById(R.id.rl_main);
        ll_mainMenu = findViewById(R.id.ll_mainMenu);
        ll_friends = findViewById(R.id.ll_friends);
        ll_love_bottom = findViewById(R.id.ll_love_bottom);
        ll_profile_support = findViewById(R.id.ll_profile_support);
        ll_profile_menu = findViewById(R.id.ll_profile_menu);
        ll_option_love = findViewById(R.id.ll_option_love);
        txt_social = findViewById(R.id.txt_social);
        txt_match = findViewById(R.id.txt_match);
        txt_payment = findViewById(R.id.txt_payment);
        txt_account = findViewById(R.id.txt_account);
        txt_personal = findViewById(R.id.txt_personal);
        iv_plus = findViewById(R.id.iv_plus);
        iv_play_date_logo = findViewById(R.id.iv_play_date_logo);
        iv_love = findViewById(R.id.iv_love);
        iv_profile_sett = findViewById(R.id.iv_profile_sett);
        ll_profile_drop_menu = findViewById(R.id.ll_profile_drop_menu);
        iv_cancel = findViewById(R.id.iv_cancel);
        iv_create_ano_ques = findViewById(R.id.iv_create_ano_ques);

        ll_take_photo = findViewById(R.id.ll_take_photo);
        ll_upload_photo = findViewById(R.id.ll_upload_photo);
        ll_Record_video = findViewById(R.id.ll_Record_video);
        ll_upload_video = findViewById(R.id.ll_upload_video);

        iv_cancel.setOnClickListener(this);
        ll_profile_insta.setOnClickListener(this);
        iv_plus.setOnClickListener(this);
        txt_payment.setOnClickListener(this);
        txt_account.setOnClickListener(this);
        txt_personal.setOnClickListener(this);
        ll_profile_support.setOnClickListener(this);
        ll_love_bottom.setOnClickListener(this);
        iv_create_ano_ques.setOnClickListener(this);

        ll_take_photo.setOnClickListener(this);
        ll_upload_photo.setOnClickListener(this);
        ll_Record_video.setOnClickListener(this);
        ll_upload_video.setOnClickListener(this);

        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        mSwipeRefreshLayout = findViewById(R.id.swipeToRefresh);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.color_pink);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                FragSocialFeed fragment=new FragSocialFeed();
//                fragment.shuffle();
//                ReplaceFrag(fragment);
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        RecyclerView rv_friends = findViewById(R.id.rv_friends);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        FriendAdapter adapterfriend = new FriendAdapter();
        rv_friends.setAdapter(adapterfriend);
        rv_friends.setLayoutManager(manager);

        Fragment fragOne = new FragLanding();
//        Fragment fragOne = new FragMyProfileDetails();
//        Fragment fragOne = new FragMyProfilePersonal();

//        Fragment fragOne = new FragMyProfilePayments();
//        Fragment fragOne = new FragMoreSuggestion();
//        Fragment fragOne = new FragCardSwipeActivity();
        ft.add(R.id.flFragment, fragOne);
        ft.commit();

        txt_match.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt_social.setBackground(null);
                txt_match.setTextColor(getResources().getColor(R.color.white));
                txt_social.setTextColor(getResources().getColor(android.R.color.darker_gray));
                txt_match.setBackground(getResources().getDrawable(R.drawable.menu_button));
                ReplaceFrag(new FragCardSwipe());
            }
        });
        txt_social.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt_match.setBackground(null);
                txt_social.setTextColor(getResources().getColor(R.color.white));
                txt_match.setTextColor(getResources().getColor(android.R.color.darker_gray));
                txt_social.setBackground(getResources().getDrawable(R.drawable.menu_button));
                ReplaceFrag(new FragSocialFeed());
            }
        });

    }


    @Override
    public void ReplaceFrag(Fragment fragment) {
        ft = fm.beginTransaction();
        ft.replace(R.id.flFragment, fragment, fragment.getClass().getSimpleName());
//        ft.addToBackStack("tags");
        ft.commitAllowingStateLoss();
    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        if (ll_profile_drop_menu.getVisibility()==View.VISIBLE){
//            ll_profile_drop_menu.setVisibility(View.GONE);
//            iv_plus.setVisibility(View.VISIBLE);
//            return true;
//        }
//        return super.dispatchTouchEvent(ev);
//    }

    @Override
    public void loadProfile() {
        ll_profile_insta.performClick();
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();
        if (id == R.id.txt_payment) {


            txt_payment.setTextColor(getResources().getColor(R.color.white));
            txt_payment.setBackground(getResources().getDrawable(R.drawable.menu_button));

            txt_personal.setBackground(null);
            txt_personal.setTextColor(getResources().getColor(android.R.color.darker_gray));
            txt_account.setBackground(null);
            txt_account.setTextColor(getResources().getColor(android.R.color.darker_gray));


            ReplaceFrag(new FragMyProfilePayments());
        } else if (id == R.id.txt_personal) {

            txt_personal.setTextColor(getResources().getColor(R.color.white));
            txt_personal.setBackground(getResources().getDrawable(R.drawable.menu_button));

            txt_account.setBackground(null);
            txt_account.setTextColor(getResources().getColor(android.R.color.darker_gray));
            txt_payment.setBackground(null);
            txt_payment.setTextColor(getResources().getColor(android.R.color.darker_gray));


            ReplaceFrag(new FragMyProfilePersonal());
        } else if (id == R.id.txt_account) {


            txt_account.setTextColor(getResources().getColor(R.color.white));
            txt_account.setBackground(getResources().getDrawable(R.drawable.menu_button));

            txt_personal.setBackground(null);
            txt_personal.setTextColor(getResources().getColor(android.R.color.darker_gray));
            txt_payment.setBackground(null);
            txt_payment.setTextColor(getResources().getColor(android.R.color.darker_gray));

            ReplaceFrag(new FragMyProfileDetails());
        } else if (id == R.id.ll_profile_support) {
            ll_option_love.setVisibility(View.GONE);
            ll_friends.setVisibility(View.GONE);
            ll_profile_menu.setVisibility(View.VISIBLE);
            iv_love.setBackground(null);
            iv_love.setImageResource(R.drawable.love);
            iv_profile_sett.setBackground(getDrawable(R.drawable.rectangle_back));
            iv_profile_sett.setImageResource(R.drawable.tech_support_red);
            ReplaceFrag(new FragMyProfileDetails());

        } else if (id == R.id.ll_love_bottom) {
            ll_option_love.setVisibility(View.VISIBLE);
            ll_friends.setVisibility(View.VISIBLE);
            ll_profile_menu.setVisibility(View.GONE);
            iv_profile_sett.setBackground(null);
            iv_profile_sett.setImageResource(R.drawable.tech_support);
            iv_love.setBackground(getDrawable(R.drawable.rectangle_back));
            iv_love.setImageResource(R.drawable.love_high);

            ReplaceFrag(new FragLanding());
        } else if (id == R.id.ll_profile_insta) {
            iv_plus.setVisibility(View.VISIBLE);
            ll_option_love.setVisibility(View.GONE);
            ll_friends.setVisibility(View.GONE);
            ll_profile_menu.setVisibility(View.GONE);
            profile = new FragInstaLikeProfile();
            ReplaceFrag(profile);
        } else if (id == R.id.iv_plus) {
            ll_profile_drop_menu.setVisibility(View.VISIBLE);
            iv_plus.setVisibility(View.GONE);
            iv_play_date_logo.setVisibility(View.INVISIBLE);
        } else if (id == R.id.iv_cancel) {
            ll_profile_drop_menu.setVisibility(View.GONE);
            iv_plus.setVisibility(View.VISIBLE);
            iv_play_date_logo.setVisibility(View.VISIBLE);
        } else if (id == R.id.iv_create_ano_ques) {
            ll_profile_drop_menu.setVisibility(View.GONE);
            iv_plus.setVisibility(View.VISIBLE);
            startActivity(new Intent(DashboardActivity.this, AnoQuesCreateActivity.class));
        }

    }

    FragInstaLikeProfile profile;
    int count = 0;

    @Override
    public void onBackPressed() {


        if (null != profile) {
            if (count == 0) {
                count = 1;
                profile.onTypeChange(0);
            } else {
                super.onBackPressed();
            }

        } else {
            super.onBackPressed();
        }


    }


}



