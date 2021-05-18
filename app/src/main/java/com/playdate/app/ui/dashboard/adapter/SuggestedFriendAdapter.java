package com.playdate.app.ui.dashboard.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.model.FriendRequest;
import com.playdate.app.model.GetUserSuggestionData;
import com.playdate.app.ui.chat.request.FragRequest;
import com.playdate.app.ui.chat.request.Onclick;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SuggestedFriendAdapter extends RecyclerView.Adapter<SuggestedFriendAdapter.ViewHolder> {
    ArrayList<GetUserSuggestionData> suggestions_list = new ArrayList<>();
    Onclick itemClick;


    public SuggestedFriendAdapter(ArrayList<GetUserSuggestionData> lst_getUserSuggestions, Onclick itemClick) {
        this.suggestions_list = lst_getUserSuggestions;
        this.itemClick = itemClick;

    }

    Context mcontext;

    @NonNull
    @Override
    public SuggestedFriendAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_suggested, parent, false);
        mcontext = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SuggestedFriendAdapter.ViewHolder holder, int position) {
        holder.name.setText(suggestions_list.get(position).getFullName());
        if (suggestions_list.get(position).getProfilePicPath() == null) {
            holder.image.setBackgroundColor(mcontext.getResources().getColor(R.color.color_grey_light));
        }
        Picasso.get().load(suggestions_list.get(position).getProfilePicPath()).placeholder(R.drawable.ic_baseline_person_24)
                .fit()
                .centerCrop()
                .into(holder.image);


        if (null != suggestions_list.get(position).getFriendRequest()) {
            if (suggestions_list.get(position).getFriendRequest().size() == 0) {
                holder.request.setImageResource(R.drawable.sent_request);
            } else {
                if (suggestions_list.get(position).getFriendRequest().get(0).getStatus().toLowerCase().trim().equals("pending")) {
                    holder.request.setImageResource(R.drawable.sent_request_sel);
                } else {
                    holder.request.setImageResource(R.drawable.sent_request);
                }
            }
        }


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
        CardView card;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name_friend);
            image = itemView.findViewById(R.id.image_friend);
            request = itemView.findViewById(R.id.friend_request);
            diamond = itemView.findViewById(R.id.diamond);
            card = itemView.findViewById(R.id.card);

            request.setOnClickListener(v -> {
                int position = getAdapterPosition();
                String userId = suggestions_list.get(position).getId();
                if (null != suggestions_list.get(position).getFriendRequest()) {
                    if (suggestions_list.get(position).getFriendRequest().size() == 0) {
                        ArrayList<FriendRequest> lst = new ArrayList<>();
                        FriendRequest fr = new FriendRequest();
                        fr.setStatus("pending");
                        lst.add(fr);
                        suggestions_list.get(position).setFriendRequest(lst);
                        notifyDataSetChanged();
                        itemClick.onItemClicks(v, position, 10, userId);

                    } else {

                    }
                }


                //    suggestions_list.get(getPosition()).setRequestSent(true);
//                    SuggestedFriendAdapter.this.notifyDataSetChanged();
            });


        }
    }
}
