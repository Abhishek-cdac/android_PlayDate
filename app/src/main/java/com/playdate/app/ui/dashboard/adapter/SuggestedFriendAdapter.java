package com.playdate.app.ui.dashboard.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.model.GetUserSuggestionData;
import com.playdate.app.ui.chat.request.Onclick;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


import static com.playdate.app.data.api.RetrofitClientInstance.BASE_URL_IMAGE;

public class SuggestedFriendAdapter extends RecyclerView.Adapter<SuggestedFriendAdapter.ViewHolder> {
    ArrayList<GetUserSuggestionData> suggestions_list = new ArrayList<>();
    Onclick itemClick;
    String userId;

    public SuggestedFriendAdapter(ArrayList<GetUserSuggestionData> lst_getUserSuggestions, Onclick itemClick) {
        this.suggestions_list = lst_getUserSuggestions;
        this.itemClick = itemClick;

    }

    Context mcontext;

    @NonNull
    @Override
    public SuggestedFriendAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_suggested, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SuggestedFriendAdapter.ViewHolder holder, int position) {
        holder.name.setText(suggestions_list.get(position).getFullName());
        Picasso.get().load(BASE_URL_IMAGE + suggestions_list.get(position).getProfilePicPath())
                .placeholder(R.drawable.cupertino_activity_indicator)
                .placeholder(R.drawable.profile)
                .into(holder.image);
        holder.request.setImageResource(R.drawable.sent_request);
        userId = suggestions_list.get(position).getId();

//        if (suggestions_list.get(position).getFriendRequest().get(position).getStatus().equals("Pending")) {
//            holder.request.setImageResource(R.drawable.sent_request_sel);
//        } else {
//            holder.request.setImageResource(R.drawable.sent_request);
//        }

//        if (suggestions_list.get(position).isPremium()) {
//            holder.diamond.setVisibility(View.VISIBLE);
//        } else {
//            holder.diamond.setVisibility(View.GONE);
//        }


    }

    @Override
    public int getItemCount() {
        return suggestions_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView image, request, diamond;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name_friend);
            image = itemView.findViewById(R.id.image_friend);
            request = itemView.findViewById(R.id.friend_request);
            diamond = itemView.findViewById(R.id.diamond);

            request.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClick.onItemClicks(v, getAdapterPosition(), 10, userId);

                    //    suggestions_list.get(getPosition()).setRequestSent(true);
//                    SuggestedFriendAdapter.this.notifyDataSetChanged();
                }
            });


        }
    }
}
