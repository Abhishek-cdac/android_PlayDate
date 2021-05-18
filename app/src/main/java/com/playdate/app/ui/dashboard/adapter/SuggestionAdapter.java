package com.playdate.app.ui.dashboard.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.playdate.app.R;
import com.playdate.app.model.FriendRequest;
import com.playdate.app.model.Friends;
import com.playdate.app.model.GetUserSuggestionData;
import com.playdate.app.ui.chat.request.Onclick;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SuggestionAdapter extends PagerAdapter {

    private Context context;


    // ArrayList<GetUserSuggestionData> lst = new ArrayList<GetUserSuggestionData>();
    ArrayList<Friends> lst = new ArrayList<Friends>();
    ArrayList<GetUserSuggestionData> suggestions_list = new ArrayList<>();
    ArrayList<FriendRequest> friendRequests_list = new ArrayList<>();
    Onclick itemClick;


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

    public SuggestionAdapter(Context mContext, ArrayList<GetUserSuggestionData> lst_getUserSuggestions, Onclick itemClick) {
        this.suggestions_list = lst_getUserSuggestions;
        this.context = mContext;
        this.itemClick = itemClick;

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

        Picasso.get().load(suggestions_list.get(position).getProfilePicPath())
                //.placeholder(R.drawable.cupertino_activity_indicator)
                .placeholder(R.drawable.profile)
                .fit()
                .centerCrop()
                .into(profile_image);


        String userId = suggestions_list.get(position).getId();

        iv_send_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClick.onItemClicks(v, position, 10, userId);

            }
        });

//        if (suggestions_list.get(position).getFriendRequest().size() == 0) {
//            iv_send_request.setImageResource(R.drawable.sent_request);
//        } else {
//            Log.e("getStatus", " " + suggestions_list.get(position).getFriendRequest().get(position).getStatus());
//            if (suggestions_list.get(position).getFriendRequest().get(position).getStatus().equals("Pending")) {
//
//                Log.e("getStatusif", " " + suggestions_list.get(position).getFriendRequest().get(position).getStatus());
//                iv_send_request.setImageResource(R.drawable.sent_request_sel);
//
//            } else {
//                Log.e("getStatuselse", " " + suggestions_list.get(position).getFriendRequest().get(position).getStatus());
//
//                iv_send_request.setImageResource(R.drawable.sent_request);
//
//            }
//            SuggestionAdapter.this.notifyDataSetChanged();
//        }


        container.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }


}