package com.playdate.app.ui.dashboard;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.ui.card_swipe.FragCardSwipeActivity;
import com.playdate.app.ui.dashboard.adapter.FriendAdapter;
import com.playdate.app.ui.dashboard.fragments.FragLanding;
import com.playdate.app.ui.dashboard.more_suggestion.FragMoreSuggestion;
import com.playdate.app.ui.interfaces.OnInnerFragmentClicks;

public class DashboardActivity extends AppCompatActivity implements OnInnerFragmentClicks {
    FragmentManager fm;
    FragmentTransaction ft;
    TextView txt_match;
    TextView txt_social;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        txt_social = findViewById(R.id.txt_social);
        txt_match = findViewById(R.id.txt_match);
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();

        RecyclerView rv_friends = findViewById(R.id.rv_friends);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        FriendAdapter adapterfriend = new FriendAdapter();
        rv_friends.setAdapter(adapterfriend);
        rv_friends.setLayoutManager(manager);

        Fragment fragOne = new FragLanding();
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
                ReplaceFrag(new FragCardSwipeActivity());
            }
        });
        txt_social.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt_match.setBackground(null);
                txt_social.setTextColor(getResources().getColor(R.color.white));
                txt_match.setTextColor(getResources().getColor(android.R.color.darker_gray));
                txt_social.setBackground(getResources().getDrawable(R.drawable.menu_button));
//                ReplaceFrag(new FragCardSwipeActivity());
            }
        });


    }


    @Override
    public void ReplaceFrag(Fragment fragment) {
        ft = fm.beginTransaction();
        ft.replace(R.id.flFragment, fragment, fragment.getClass().getSimpleName());
        ft.addToBackStack("tags");
        ft.commitAllowingStateLoss();
    }
}



