package com.playdate.app.ui.register.interest.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;


import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.model.GetUserSuggestionData;
import com.playdate.app.model.Interest;

import java.util.ArrayList;
import java.util.List;

public class InterestAdapter extends RecyclerView.Adapter<InterestAdapter.ViewHolder> implements Filterable  {

    ArrayList<Interest> lst_int ;
    ArrayList<Interest> lst_intFilter ;

    public InterestAdapter(ArrayList<Interest> lst_int) {


        this.lst_int = lst_int;
        this.lst_intFilter = lst_int;
    }
    //    ArrayList<Interest> lst_int_search = new ArrayList();




    Context mContext;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_interest, parent, false);
        mContext = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txt_intresrt.setText(lst_intFilter.get(position).getName());
        if (lst_intFilter.get(position).isSelected()) {
            holder.txt_intresrt.setBackground(mContext.getDrawable(R.drawable.selected_interest_row));
            holder.iv_cross.setVisibility(View.VISIBLE);
//            Animation fadeInAnimation = AnimationUtils.loadAnimation(mContext, R.anim.shake);
//            holder.itemView.startAnimation(fadeInAnimation);
        } else {
            holder.iv_cross.setVisibility(View.GONE);
            holder.txt_intresrt.setBackground(mContext.getDrawable(R.drawable.circle_corner_gblue_grey));
        }

        holder.iv_cross.setOnClickListener(view -> {
            lst_intFilter.get(position).setSelected(!lst_intFilter.get(position).isSelected());
            notifyDataSetChanged();
        });

    }

    @Override
    public int getItemCount() {
        return lst_intFilter.size();
    }

    public void updateList(ArrayList<Interest> filterdNames) {

        lst_intFilter = filterdNames;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_intresrt;
        ImageView iv_cross;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_intresrt = itemView.findViewById(R.id.txt_intresrt);
            iv_cross = itemView.findViewById(R.id.iv_cross);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    lst_intFilter.get(getAdapterPosition()).setSelected(true);
                    notifyDataSetChanged();
                }
            });
        }
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    lst_intFilter = lst_int;
                } else {
                    List<Interest> filteredList = new ArrayList<>();
                    for (Interest row : lst_int) {
                        Log.e("rowrow", "" + row.getName());
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    lst_intFilter = (ArrayList<Interest>) filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = lst_intFilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                lst_intFilter = (ArrayList<Interest>) filterResults.values;
                notifyDataSetChanged();
            }

        };
    }

    public interface InterestAdapterListner {
        void onInterestSelected(Interest interest);
    }


}
