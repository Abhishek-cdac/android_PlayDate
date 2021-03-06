package com.playdate.app.ui.date.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.model.CreateDateGetPartnerData;

import com.playdate.app.ui.chat.request.Onclick;

import com.playdate.app.ui.dashboard.fragments.FragSearchUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SuggestedDateAdapter extends RecyclerView.Adapter<SuggestedDateAdapter.ViewHolder> implements Filterable {

    private final List<CreateDateGetPartnerData> suggestions_list;
    private List<CreateDateGetPartnerData> suggestionsListFiltered;
    private final Onclick itemClick;
    private FragSearchUser userFrag;
    private Context mcontext;
    private final Picasso picasso;

    public SuggestedDateAdapter(Context applicationContext, ArrayList<CreateDateGetPartnerData> lst_createDateGetPartner, Onclick itemClick) {
        this.mcontext = applicationContext;
        this.suggestions_list = lst_createDateGetPartner;
        this.suggestionsListFiltered = lst_createDateGetPartner;
        this.itemClick = itemClick;
        picasso = Picasso.get();
    }

    @NonNull
    @Override
    public SuggestedDateAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_serach_date, parent, false);
        mcontext = parent.getContext();
        return new SuggestedDateAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SuggestedDateAdapter.ViewHolder holder, int position) {
        holder.name.setText(suggestionsListFiltered.get(position).getUsername());
        holder.point.setText(suggestionsListFiltered.get(position).getTotalPoints() + " Points");
        if (suggestionsListFiltered.get(position).getProfilePicPath() == null) {
            holder.image.setBackgroundColor(mcontext.getResources().getColor(R.color.color_grey_light));
        }
//        holder.name.setOnClickListener(v -> OnUserClick(position));
//        holder.image.setOnClickListener(v -> OnUserClick(position));

        picasso.load(suggestionsListFiltered.get(position).getProfilePicPath()).placeholder(R.drawable.ic_baseline_person_24)
                .fit()
                .placeholder(R.drawable.profile)
                .centerCrop()
                .into(holder.image);


        holder.ll_searchDate.setOnClickListener(v -> OnUserClick(position, v));


    }

    private void OnUserClick(int pos, View v) {
        try {
            Log.d("Username", "OnUserClick: " + suggestionsListFiltered.get(pos).getUsername());

            itemClick.onItemClicks(v, pos, 22, suggestionsListFiltered.get(pos).getUsername(), suggestionsListFiltered.get(pos).getTotalPoints(), suggestionsListFiltered.get(pos).getId(), suggestionsListFiltered.get(pos).getProfilePicPath());

//            userFrag.OnUserProfileSelected(false, suggestions_list.get(pos).getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
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
                    List<CreateDateGetPartnerData> filteredList = new ArrayList<>();
                    try {
                        for (CreateDateGetPartnerData row : suggestions_list) {

                            // name match condition. this might differ depending on your requirement
                            // here we are looking for name or phone number match
                            if (row.getUsername() != null) {
                                if (row.getUsername().toLowerCase().contains(charString.toLowerCase()) || row.getFullName().contains(charSequence)) {
                                    filteredList.add(row);
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    suggestionsListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestionsListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                suggestionsListFiltered = (ArrayList<CreateDateGetPartnerData>) filterResults.values;
                notifyDataSetChanged();
            }

        };
    }

    public interface SuggestionsAdapterListner {
        void onSuggestionSelected(CreateDateGetPartnerData createDateGetPartnerData);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final TextView point;
        private final ImageView image;
        private final LinearLayout ll_searchDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name_friend);
            image = itemView.findViewById(R.id.image_friend);
            point = itemView.findViewById(R.id.point);
//            ImageView diamond = itemView.findViewById(R.id.diamond);
//            CardView card = itemView.findViewById(R.id.card);
            ll_searchDate = itemView.findViewById(R.id.ll_searchDate);


        }
    }
}
