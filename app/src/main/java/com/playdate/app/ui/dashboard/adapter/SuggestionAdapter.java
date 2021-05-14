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
import com.playdate.app.model.FriendRequest;
import com.playdate.app.model.Friends;
import com.playdate.app.model.GetUserSuggestionData;
import com.playdate.app.ui.dashboard.fragments.FragSuggestion;
import com.playdate.app.ui.onboarding.OnBoardingImageFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.playdate.app.data.api.RetrofitClientInstance.BASE_URL_IMAGE;

public class SuggestionAdapter extends PagerAdapter {

    private Context context;


   // ArrayList<GetUserSuggestionData> lst = new ArrayList<GetUserSuggestionData>();
 ArrayList<Friends> lst = new ArrayList<Friends>();
    ArrayList<GetUserSuggestionData> suggestions_list = new ArrayList<>();
    ArrayList<FriendRequest> friendRequests_list = new ArrayList<>();


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

    public SuggestionAdapter(Context mContext,ArrayList<GetUserSuggestionData> lst_getUserSuggestions) {
        this.suggestions_list = lst_getUserSuggestions;
        this.context = mContext;

    }


    @Override
    public int getCount() {
        return suggestions_list.size();
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

        ImageView iv_send_request, profile_image;
        TextView title, desc, txt_header_Suggestions;

        profile_image = view.findViewById(R.id.profile_image);
        iv_send_request = view.findViewById(R.id.iv_send_request);
        txt_header_Suggestions = view.findViewById(R.id.txt_header_Suggestions);
        txt_header_Suggestions.setText(suggestions_list.get(position).getFullName());
//        Picasso.get().load(BASE_URL_IMAGE + suggestions_list.get(position).getProfilePicPath())
//                .placeholder(R.drawable.cupertino_activity_indicator)
//                .placeholder(R.drawable.profile)
//                .into(profile_image);
//        if (suggestions_list.get(position).getFriendRequest().get(position).getStatus().equals("Pending")) {
//            iv_send_request.setImageResource(R.drawable.sent_request_sel);
//        } else {
//            iv_send_request.setImageResource(R.drawable.sent_request);
//        }
//        iv_send_request.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view1) {
//                lst.get(position).setRequestSent(true);
//
//                SuggestionAdapter.this.notifyDataSetChanged();
//            }
//        });

        container.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }


}