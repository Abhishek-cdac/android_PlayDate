package com.playdate.app.ui.blockuser;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.model.BlockedUser;
import com.playdate.app.ui.anonymous_question.AnonymousBottomSheet;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class BlockedUserAdapter extends RecyclerView.Adapter<BlockedUserAdapter.ViewHolder> {
    private final ArrayList<BlockedUser> list;
    private final Picasso picasso;
    private final BlockUserActivity blockUserActivity;

    public BlockedUserAdapter(ArrayList<BlockedUser> list, BlockUserActivity blockUserActivity) {
        this.list = list;
        this.blockUserActivity = blockUserActivity;
        picasso = Picasso.get();
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_blocked_users, null);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.setData(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_name;
        ImageView img_friend;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            txt_name = itemView.findViewById(R.id.txt_name);
            img_friend = itemView.findViewById(R.id.img_friend);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    blockUserActivity.showModel(list.get(getAdapterPosition()).getToUserId());
                }
            });
        }

        void setData(BlockedUser user) {
            txt_name.setText(user.getUsername());
            picasso.load(user.getProfilePicPath()).into(img_friend);
        }
    }


}
