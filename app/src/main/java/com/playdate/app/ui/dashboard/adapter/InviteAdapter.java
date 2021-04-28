package com.playdate.app.ui.dashboard.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.model.Invite;

import java.util.ArrayList;

public class InviteAdapter extends RecyclerView.Adapter<InviteAdapter.ViewHolder> {

    ArrayList<Invite> invite_list = new ArrayList<>();

    public InviteAdapter() {
        invite_list.add(new Invite(R.drawable.user, "Follow Contacts"));
        invite_list.add(new Invite(R.drawable.facebook, "Invite Friends by Facebook"));
        invite_list.add(new Invite(R.drawable.message, "Invite Friends by SMS oe Email"));
        invite_list.add(new Invite(R.drawable.upload, "Invite Friends by..."));

    }


    @NonNull
    @Override
    public InviteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_invite, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull InviteAdapter.ViewHolder holder, int position) {
        holder.title.setText(invite_list.get(position).getTitle());
        holder.icon.setImageResource(invite_list.get(position).getImage());

    }

    @Override
    public int getItemCount() {
        return invite_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            icon = itemView.findViewById(R.id.icons_invite);
            title = itemView.findViewById(R.id.title_invite);
        }
    }
}
