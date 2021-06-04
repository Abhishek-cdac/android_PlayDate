package com.playdate.app.ui.chat.request;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.model.Inbox;
import com.playdate.app.model.chat_models.ChatExample;
import com.playdate.app.ui.chat.ChattingAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.MyViewHolder> {
    ArrayList<ChatExample> inboxList;
    Onclick itemClick;
    FragInbox ref;

    public RequestAdapter(ArrayList<ChatExample> inboxList, Onclick itemClick) {
        this.inboxList = inboxList;
        this.itemClick = itemClick;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, msg;
        public ImageView profile_image, img_accept, img_reject;
        public RelativeLayout main_menu;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.user_name);
            msg = (TextView) view.findViewById(R.id.txt_msg);
            main_menu = view.findViewById(R.id.main_rl);
            img_accept = view.findViewById(R.id.img_accept);
            img_reject = view.findViewById(R.id.img_reject);
            profile_image = view.findViewById(R.id.profile_image);
//            main_menu.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    itemClick.onItemClick(v, getAdapterPosition(), 12);
//                }
//            });
        }
    }

    @Override
    public RequestAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_list_row, parent, false);
        return new RequestAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RequestAdapter.MyViewHolder holder, int position) {
//        Inbox inbox = inboxList.get(position);
        ChatExample example = inboxList.get(position);
        holder.title.setText(example.getSenderName());
        Picasso.get().load(example.getProfilePhoto()).into(holder.profile_image);
        //        holder.msg.setText(inbox.getMsg());


        holder.img_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // api for getChats
                inboxList.remove(position);
                notifyDataSetChanged();
            }
        });

        holder.img_reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inboxList.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return inboxList.size();
    }
}