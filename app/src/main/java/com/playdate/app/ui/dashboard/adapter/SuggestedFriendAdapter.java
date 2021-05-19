package com.playdate.app.ui.dashboard.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
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
import java.util.List;

public class SuggestedFriendAdapter extends RecyclerView.Adapter<SuggestedFriendAdapter.ViewHolder> implements Filterable {

    //    ArrayList<GetUserSuggestionData> suggestions_list = new ArrayList<>();
//    ArrayList<GetUserSuggestionData> suggestionsListFiltered = new ArrayList<>();
    List<GetUserSuggestionData> suggestions_list;
    List<GetUserSuggestionData> suggestionsListFiltered;

    Onclick itemClick;


    public SuggestedFriendAdapter(ArrayList<GetUserSuggestionData> lst_getUserSuggestions, Onclick itemClick) {
        this.suggestions_list = lst_getUserSuggestions;
        this.suggestionsListFiltered = lst_getUserSuggestions;
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
        holder.name.setText(suggestionsListFiltered.get(position).getUsername());
        if (suggestionsListFiltered.get(position).getProfilePicPath() == null) {
            holder.image.setBackgroundColor(mcontext.getResources().getColor(R.color.color_grey_light));
        }
        Picasso.get().load(suggestionsListFiltered.get(position).getProfilePicPath()).placeholder(R.drawable.ic_baseline_person_24)
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

//        if (suggestions_list.get(position).isPremium()) {
//            holder.diamond.setVisibility(View.VISIBLE);
//        } else {
//            holder.diamond.setVisibility(View.GONE);
//        }


    }

    @Override
    public int getItemCount() {
        Log.e("suggestionsListFiltered", "" + suggestionsListFiltered.size());

        return suggestionsListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    suggestionsListFiltered = suggestions_list;
                } else {
                    List<GetUserSuggestionData> filteredList = new ArrayList<>();
                    for (GetUserSuggestionData row : suggestions_list) {
                        Log.e("rowrow", "" + row.getUsername());
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getUsername().toLowerCase().contains(charString.toLowerCase()) || row.getFullName().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    suggestionsListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestionsListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                suggestionsListFiltered = (ArrayList<GetUserSuggestionData>) filterResults.values;
                notifyDataSetChanged();
            }

        };
    }

    public interface SuggestionsAdapterListner {
        void onSuggestionSelected(GetUserSuggestionData getUserSuggestionData);
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
