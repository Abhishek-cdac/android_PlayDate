package com.playdate.app.ui.dashboard.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.playdate.app.R;
import com.playdate.app.model.Friends;
import com.playdate.app.ui.dashboard.fragments.FragSuggestion;
import com.playdate.app.ui.onboarding.OnBoardingImageFragment;

import java.util.ArrayList;

public class SuggestionAdapter extends PagerAdapter {

    private Context context;


    ArrayList<Friends> lst = new ArrayList<Friends>();

    public SuggestionAdapter(Context mContext) {
        this.context = mContext;
        lst.add(new Friends("Maria Gomes", "", true));
        lst.add(new Friends("Maria Gomes", "", true));
        lst.add(new Friends("Maria Gomes", "", true));
        lst.add(new Friends("Maria Gomes", "", true));
        lst.add(new Friends("Maria Gomes", "", true));
        lst.add(new Friends("Maria Gomes", "", true));
        lst.add(new Friends("Maria Gomes", "", true));
        lst.add(new Friends("Maria Gomes", "", true));
        lst.add(new Friends("Maria Gomes", "", true));
        lst.add(new Friends("Maria Gomes", "", true));
        lst.add(new Friends("Maria Gomes", "", true));
        lst.add(new Friends("Maria Gomes", "", true));
        lst.add(new Friends("Maria Gomes", "", true));
        lst.add(new Friends("Maria Gomes", "", true));
        lst.add(new Friends("Maria Gomes", "", true));
        lst.add(new Friends("Maria Gomes", "", true));
    }


    @Override
    public int getCount() {
        return lst.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    private LayoutInflater layoutInflater;

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.frag_suggestions, container, false);

        ImageView iv_send_request;
        TextView title, desc;

        iv_send_request = view.findViewById(R.id.iv_send_request);
        if (lst.get(position).isRequestSent()) {
            iv_send_request.setImageResource(R.drawable.sent_request_sel);
        } else {
            iv_send_request.setImageResource(R.drawable.sent_request);
        }
        iv_send_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                lst.get(position).setRequestSent(true);
                SuggestionAdapter.this.notifyDataSetChanged();

            }
        });

        container.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }


}