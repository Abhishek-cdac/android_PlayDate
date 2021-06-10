package com.playdate.app.ui.date.adapter;

import android.content.Context;
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

    List<CreateDateGetPartnerData> suggestions_list;
    List<CreateDateGetPartnerData> suggestionsListFiltered;
    Onclick itemClick;
    FragSearchUser userFrag;
    Context mcontext;

    public SuggestedDateAdapter(Context applicationContext, ArrayList<CreateDateGetPartnerData> lst_createDateGetPartner, Onclick itemClick) {
        this.mcontext = applicationContext;
        this.suggestions_list = lst_createDateGetPartner;
        this.suggestionsListFiltered = lst_createDateGetPartner;
        this.itemClick = itemClick;
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
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // OnUserClick(position);
            }
        });
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // OnUserClick(position);
            }
        });

        Picasso.get().load(suggestionsListFiltered.get(position).getProfilePicPath()).placeholder(R.drawable.ic_baseline_person_24)
                .fit()
                .placeholder(R.drawable.profile)
                .centerCrop()
                .into(holder.image);


        holder.ll_searchDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  itemClick.onItemClicks(v, position, 22, suggestionsListFiltered.get(position).getUsername(), suggestionsListFiltered.get(position).getTotalPoints(), suggestionsListFiltered.get(position).getId(), suggestionsListFiltered.get(position).getProfilePicPath());

            }
        });


    }

    private void OnUserClick(int pos) {
//       boolean isFriend=false;
//        if(suggestionsListFiltered.get(pos).getFriendRequest().get(0).getStatus().toLowerCase().trim().equals("pending")){
//
//        }else{
//
//        }
        userFrag.OnUserProfileSelected(false, suggestions_list.get(pos).getId());

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
        TextView name, point;
        ImageView image, diamond;
        CardView card;
        LinearLayout ll_searchDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name_friend);
            image = itemView.findViewById(R.id.image_friend);
            point = itemView.findViewById(R.id.point);
            diamond = itemView.findViewById(R.id.diamond);
            card = itemView.findViewById(R.id.card);
            ll_searchDate = itemView.findViewById(R.id.ll_searchDate);


        }
    }
}
