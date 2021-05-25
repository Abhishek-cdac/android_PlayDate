package com.playdate.app.ui.chat_ui_screen;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.model.ChattingGetChats;
import com.playdate.app.model.Inbox;
import com.playdate.app.ui.chat_ui_screen.request.InboxAdapter;
import com.playdate.app.ui.chat_ui_screen.request.Onclick;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ChattingAdapter extends RecyclerView.Adapter<ChattingAdapter.MyViewHolder> {
    private List<ChattingAdapter> inboxList;
    Onclick itemClick;

    public ChattingAdapter(List<ChattingAdapter> inboxList, Onclick itemClick) {
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


    @NonNull
    @Override
    public ChattingAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.inbox_list_row, parent, false);
        return new ChattingAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.title.setText(inboxList.get(position).);
        ChattingGetChats inbox = inboxList.get(position);
        holder.title.setText(inbox.getName());
//        holder.msg.setText(inbox.getMessages());
//        Picasso.get().load(inbox.getImageUrl()).into(holder.profile_image);
//        holder.txt_time.setVisibility(View.VISIBLE);
//        holder.txt_time.setText(inbox.getTime());
//
//        if (position >= 4) {
//            holder.txt_notify.setVisibility(View.GONE);
//            holder.img_more.setVisibility(View.VISIBLE);
//        } else {
//            holder.txt_notify.setVisibility(View.VISIBLE);
//            holder.txt_notify.setText(inbox.getNotification());
//            holder.img_more.setVisibility(View.GONE);
//        }
    }


    @Override
    public int getItemCount() {
        return inboxList.size();
    }
}
