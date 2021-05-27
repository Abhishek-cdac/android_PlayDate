package com.playdate.app.ui.dashboard.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.playdate.app.R;
import com.playdate.app.model.FriendRequest;
import com.playdate.app.model.GetUserSuggestionData;
import com.playdate.app.ui.chat.request.Onclick;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SuggestionAdapter extends PagerAdapter {

    private Context context;


    ArrayList<GetUserSuggestionData> suggestions_list = new ArrayList<>();
    ArrayList<FriendRequest> friendRequests_list = new ArrayList<>();
    Onclick itemClick;


    public SuggestionAdapter(Context mContext, ArrayList<GetUserSuggestionData> lst_getUserSuggestions, Onclick itemClick) {
        this.suggestions_list = lst_getUserSuggestions;
        this.context = mContext;
        this.itemClick = itemClick;

    }


    @Override
    public int getCount() {
        return suggestions_list.size();
    }

    public int getItemPosition(Object object) {
        return POSITION_NONE;
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

        ImageView iv_send_request, profile_image, iv_chat_icon;
        TextView title, desc, txt_header_Suggestions;

        profile_image = view.findViewById(R.id.profile_image);
        iv_send_request = view.findViewById(R.id.iv_send_request);
        iv_chat_icon = view.findViewById(R.id.iv_chat_icon);
        txt_header_Suggestions = view.findViewById(R.id.txt_header_Suggestions);
        txt_header_Suggestions.setText(suggestions_list.get(position).getUsername());


        if (suggestions_list.get(position).getProfilePicPath() == null) {
            profile_image.setBackgroundColor(context.getResources().getColor(R.color.color_grey_light));
        }


        Picasso.get().load(suggestions_list.get(position).getProfilePicPath()).placeholder(R.drawable.ic_baseline_person_24)
                .fit()
                .centerCrop()
                .into(profile_image);


        String userId = suggestions_list.get(position).getId();
        Log.e("request_sent_userID",""+userId);

        if (suggestions_list.get(position).getFriendRequest() != null) {
            if (suggestions_list.get(position).getFriendRequest().size() > 0) {
                if (suggestions_list.get(position).getFriendRequest().get(0).getStatus().toLowerCase().equals("pending")) {
                    iv_send_request.setImageResource(R.drawable.sent_request_sel);
                } else {
                    iv_send_request.setImageResource(R.drawable.sent_request);
                }

                if (suggestions_list.get(position).getFriendRequest().get(0).getStatus().toLowerCase().equals("verified")) {
                    iv_chat_icon.setImageResource(R.drawable.chat_sel);
                } else {
                    iv_chat_icon.setImageResource(R.drawable.chat_black);
                }
            }
        }


        iv_send_request.setOnClickListener(v -> {
            if (suggestions_list.get(position).getFriendRequest() != null) {
                if (suggestions_list.get(position).getFriendRequest().size() == 0) {
                    List<FriendRequest> lst = new ArrayList<>();
                    FriendRequest fr = new FriendRequest();
                    fr.setStatus("Pending");
                    lst.add(fr);
                    suggestions_list.get(position).setFriendRequest(lst);
                    itemClick.onItemClicks(v, position, 10, userId);
                    notifyDataSetChanged();
                }
            }


        });

        iv_chat_icon.setOnClickListener(v -> {
            Toast.makeText(context, "Comming soon", Toast.LENGTH_SHORT).show();
        });


        container.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }


}