package com.playdate.app.ui.register.interest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.model.Interest;

import java.util.ArrayList;
import java.util.List;

public class InterestAdapter extends RecyclerView.Adapter<InterestAdapter.ViewHolder> {

    ArrayList<Interest> lst = new ArrayList();


    public InterestAdapter() {
        lst.add(new Interest("Neflix", false));
        lst.add(new Interest("Football", false));
        lst.add(new Interest("Anime", false));
        lst.add(new Interest("Manga", false));
        lst.add(new Interest("Swimmng", false));
        lst.add(new Interest("Wine Testing", false));
        lst.add(new Interest("Soccer", false));
        lst.add(new Interest("Backet ball", false));
        lst.add(new Interest("Shopping", false));
        lst.add(new Interest("Design", false));
        lst.add(new Interest("Gym", false));
        lst.add(new Interest("Reading", false));
        lst.add(new Interest("Jogging", false));
        lst.add(new Interest("Dinning", false));
        lst.add(new Interest("Drawing", false));
        lst.add(new Interest("Cooking", false));
    }

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
        holder.txt_intresrt.setText(lst.get(position).getInterest());
        if (lst.get(position).isSelected()) {
            holder.txt_intresrt.setBackground(mContext.getDrawable(R.drawable.selected_interest_row));
            holder.iv_cross.setVisibility(View.VISIBLE);
        } else {
            holder.iv_cross.setVisibility(View.GONE);
            holder.txt_intresrt.setBackground(mContext.getDrawable(R.drawable.circle_corner_gblue_grey));
        }

        holder.iv_cross.setOnClickListener(view -> {
            lst.get(position).setSelected(!lst.get(position).isSelected());
            notifyDataSetChanged();
        });


    }

    @Override
    public int getItemCount() {
        return lst.size();
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
                    lst.get(getAdapterPosition()).setSelected(true);
                    notifyDataSetChanged();
                }
            });
        }
    }
}
