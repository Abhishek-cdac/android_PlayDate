package com.playdate.app.ui.dashboard.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.model.Friends;

import java.util.ArrayList;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.ViewHolder> {

    ArrayList<Friends> lst = new ArrayList<Friends>();

    public FriendAdapter() {
        lst.add(new Friends("adreena helen", R.drawable.model1));
        lst.add(new Friends("gomes helen", R.drawable.model2));
        lst.add(new Friends("jonn den", R.drawable.model1));
        lst.add(new Friends("Ramsphy k", R.drawable.model2));
        lst.add(new Friends("reena", R.drawable.model1));
        lst.add(new Friends("adreena helen", R.drawable.model1));
        lst.add(new Friends("gomes helen", R.drawable.model2));
        lst.add(new Friends("jonn den", R.drawable.model1));
        lst.add(new Friends("Ramsphy k", R.drawable.model2));
        lst.add(new Friends("reena", R.drawable.model1));
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_friends, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.profile_image.setImageResource(lst.get(position).getImage());
        holder.txt_friend_name.setText(lst.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return lst.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView profile_image;
        TextView txt_friend_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profile_image = itemView.findViewById(R.id.profile_image);
            txt_friend_name = itemView.findViewById(R.id.txt_friend_name);
        }
    }
}
