package com.playdate.app.ui.dashboard.fragments;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.playdate.app.R;
import com.playdate.app.ui.dashboard.adapter.SuggestionAdapter;
import com.playdate.app.ui.dashboard.more_suggestion.FragMoreSuggestion;
import com.playdate.app.ui.interfaces.OnInnerFragmentClicks;
import com.playdate.app.util.common.CommonClass;

public class FragLanding extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_landing, container, false);
        ViewPager vp_suggestion = view.findViewById(R.id.vp_suggestion);
        RelativeLayout Rl_page=view.findViewById(R.id.Rl_page);

//        SuggestionAdapter adapter = new SuggestionAdapter(getActivity(), getActivity().getSupportFragmentManager());
        SuggestionAdapter adapter = new SuggestionAdapter(getActivity());
        vp_suggestion.setAdapter(adapter);
        vp_suggestion.setCurrentItem(8);
        vp_suggestion.setPadding(130, 0, 130, 0);



        int height = new CommonClass().getScreenHeight(getActivity());

        int m1= (int) getResources().getDimension(R.dimen._20sdp);
        int m2= (int) getResources().getDimension(R.dimen._20sdp);
        int m3= (int) getResources().getDimension(R.dimen._20sdp);
        int m4= (int) getResources().getDimension(R.dimen._20sdp);
        int m5= (int) getResources().getDimension(R.dimen._60sdp);
        int m6= (int) getResources().getDimension(R.dimen._65sdp);

        Rl_page.getLayoutParams().height=height-(m1+m2+m3+m4+m5+m6);

        TextView txt_footer_see_more = view.findViewById(R.id.txt_footer_see_more);
        txt_footer_see_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                OnInnerFragmentClicks frag = (OnInnerFragmentClicks) getActivity();
                frag.ReplaceFrag(new FragMoreSuggestion());
            }
        });


        return view;
    }
}
