package com.playdate.app.ui.dashboard.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.model.FriendRequest;
import com.playdate.app.model.GetUserSuggestionData;
import com.playdate.app.ui.chat.request.Onclick;
import com.playdate.app.ui.dashboard.fragments.FragSearchUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SuggestedFriendAdapter extends RecyclerView.Adapter<SuggestedFriendAdapter.ViewHolder> {

    private final List<GetUserSuggestionData> suggestions_list;
    private final List<GetUserSuggestionData> suggestionsListFiltered;
    private final Onclick itemClick;
    private final FragSearchUser userFrag;
    private final Picasso picasso;

    public boolean hideShowMore = false;

    public void setHideShowMore() {
        hideShowMore = true;
    }

    public SuggestedFriendAdapter(ArrayList<GetUserSuggestionData> lst_getUserSuggestions, Onclick itemClick, FragSearchUser userFrag) {
        this.suggestions_list = lst_getUserSuggestions;
        this.suggestionsListFiltered = lst_getUserSuggestions;
        this.itemClick = itemClick;
        this.userFrag = userFrag;
        picasso = Picasso.get();
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
        holder.name.setText(suggestionsListFiltered.get(position).getUsername());
        if (suggestionsListFiltered.get(position).getProfilePicPath() == null) {
            holder.image.setBackgroundColor(mcontext.getResources().getColor(R.color.color_grey_light));
        }
        holder.name.setOnClickListener(v -> OnUserClick(position));
        holder.image.setOnClickListener(v -> OnUserClick(position));

        picasso.load(suggestionsListFiltered.get(position).getProfilePicPath()).placeholder(R.drawable.ic_baseline_person_24)
                .fit()
                .placeholder(R.drawable.profile)
                .centerCrop()
                .into(holder.image);


        if (null != suggestionsListFiltered.get(position).getFriendRequest()) {
            if (suggestionsListFiltered.get(position).getFriendRequest().size() == 0) {
                holder.request.setImageResource(R.drawable.sent_request);
            } else {
                if (suggestionsListFiltered.get(position).getFriendRequest().get(0).getStatus().toLowerCase().trim().equals("pending")) {
                    holder.request.setImageResource(R.drawable.sent_request_sel);
                } else {
                    holder.request.setImageResource(R.drawable.sent_request);
                }
            }
        }

        holder.request.setOnClickListener(v -> {
            if (null != suggestions_list.get(position).getFriendRequest()) {
                if (suggestions_list.get(position).getFriendRequest().size() == 0) {
                    ArrayList<FriendRequest> lst = new ArrayList<>();
                    FriendRequest fr = new FriendRequest();
                    fr.setStatus("pending");
                    lst.add(fr);
                    suggestions_list.get(position).setFriendRequest(lst);
                    notifyDataSetChanged();
                    itemClick.onItemClicks(v, position, 10, suggestions_list.get(position).getId());

                } else {

                }
            }
        });

        if (position == suggestionsListFiltered.size() - 1) {
            if (hideShowMore) {
                holder.txt_show.setVisibility(View.GONE);

            } else {
                holder.txt_show.setVisibility(View.VISIBLE);
            }
        } else {
            holder.txt_show.setVisibility(View.GONE);
        }
        holder.txt_show.setOnClickListener(v -> userFrag.loadMore());
    }

    private void OnUserClick(int pos) {
        userFrag.OnUserProfileSelected(false, suggestions_list.get(pos).getId());
    }

    @Override
    public int getItemCount() {
        if (suggestionsListFiltered == null)
            return 0;
        return suggestionsListFiltered.size();
    }

    public interface SuggestionsAdapterListner {
        void onSuggestionSelected(GetUserSuggestionData getUserSuggestionData);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final ImageView image;
        private final TextView txt_show;
        private final ImageView request;
        private final RelativeLayout rl_suggestion;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name_friend);
            image = itemView.findViewById(R.id.image_friend);
            request = itemView.findViewById(R.id.friend_request);
            txt_show = itemView.findViewById(R.id.txt_show);
            rl_suggestion = itemView.findViewById(R.id.rl_suggestion);


        }
    }
}
