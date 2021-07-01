package com.playdate.app.ui.date.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.model.CreateDateGetPartnerData;
import com.playdate.app.ui.chat.request.Onclick;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MyDataAdapter extends RecyclerView.Adapter<MyDataAdapter.ViewHolder> {
    Onclick itemClick;

    private final ArrayList<CreateDateGetPartnerData> list;
    private FragSelectPartner frag;

    Picasso picasso;

    public MyDataAdapter(Context mcontext, ArrayList<CreateDateGetPartnerData> lst_createDateGetPartner, Onclick itemClick) {
        this.list = lst_createDateGetPartner;
        this.itemClick = itemClick;
        picasso = Picasso.get();
    }

    @NonNull
    @NotNull
    @Override
    public MyDataAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_date_partner, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyDataAdapter.ViewHolder holder, int position) {
        holder.tv_partner_points.setText(list.get(position).getTotalPoints());
        holder.tv_partner_username.setText(list.get(position).getUsername());
        if (position == 0) {
            holder.crown.setVisibility(View.VISIBLE);
        } else {
            holder.crown.setVisibility(View.INVISIBLE);
        }

        picasso.load(list.get(position).getProfilePicPath()).placeholder(R.drawable.profile).into(holder.partner_image);

        holder.partner_image.setOnClickListener(v -> {

            itemClick.onItemClicks(v, position, 20, list.get(position).getUsername(), list.get(position).getTotalPoints(), list.get(position).getId(), list.get(position).getProfilePicPath());

        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView partner_image;
        ImageView crown;
        TextView tv_partner_username;
        TextView tv_partner_points;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            partner_image = itemView.findViewById(R.id.partner_image);
            crown = itemView.findViewById(R.id.crown);
            tv_partner_username = itemView.findViewById(R.id.tv_partner_username);
            tv_partner_points = itemView.findViewById(R.id.tv_partner_points);


        }
    }
}
