package com.playdate.app.ui.chat.request;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.model.Inbox;
import com.playdate.app.model.Suggestions;
import com.playdate.app.ui.dashboard.adapter.SuggestedFriendAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

public class InboxAdapter extends RecyclerView.Adapter<InboxAdapter.MyViewHolder> {
    private List<Inbox> inboxList;
    Onclick itemClick;

    public InboxAdapter(List<Inbox> inboxList, Onclick itemClick) {
        this.inboxList = inboxList;
        this.itemClick = itemClick;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, msg, txt_notify, txt_time;
        public ImageView profile_image, img_more;
        public RelativeLayout main_menu;

        public MyViewHolder(View view) {
            super(view);
            txt_time = (TextView) view.findViewById(R.id.txt_time);
            title = (TextView) view.findViewById(R.id.user_name);
            txt_notify = (TextView) view.findViewById(R.id.txt_notify);
            msg = (TextView) view.findViewById(R.id.txt_msg);
            main_menu = view.findViewById(R.id.main_rl);
            img_more = view.findViewById(R.id.img_more);
            profile_image = view.findViewById(R.id.profile_image);

            main_menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClick.onItemClick(v, getAdapterPosition(), 12);
                }
            });

        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.inbox_list_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Inbox inbox = inboxList.get(position);
        holder.title.setText(inbox.getName());
        holder.msg.setText(inbox.getMsg());
        Picasso.get().load(inbox.getImageUrl()).into(holder.profile_image);
        holder.txt_time.setVisibility(View.VISIBLE);
        holder.txt_time.setText(inbox.getTime());

        if (position >= 4) {
            holder.txt_notify.setVisibility(View.GONE);
            holder.img_more.setVisibility(View.VISIBLE);
        } else {
            holder.txt_notify.setVisibility(View.VISIBLE);
            holder.txt_notify.setText(inbox.getNotification());
            holder.img_more.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return inboxList.size();
    }
}