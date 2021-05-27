package com.playdate.app.ui.chat;

import android.content.Context;
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
import com.playdate.app.model.chat_models.ChatExample;
import com.playdate.app.model.chat_models.ChatMessage;
import com.playdate.app.ui.chat.request.FragInbox;
import com.playdate.app.ui.chat.request.Onclick;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ChattingAdapter extends RecyclerView.Adapter<ChattingAdapter.MyViewHolder> {
    private ArrayList<ChatExample> inboxList;
    Context mcontext;
    Onclick itemClick;
    private FragInbox frag;
    int selectedIndex = -1;

    public ChattingAdapter(ArrayList<ChatExample> inboxList, Onclick itemClick, FragInbox frag) {
        this.inboxList = inboxList;
        this.itemClick = itemClick;
        this.frag = frag;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView user_name, msg, txt_notify, txt_time;
        public ImageView profile_image, img_more;
        public RelativeLayout main_menu;

        public MyViewHolder(View view) {
            super(view);
            txt_time = (TextView) view.findViewById(R.id.txt_time);
            user_name = (TextView) view.findViewById(R.id.user_name);
            txt_notify = (TextView) view.findViewById(R.id.txt_notify);
            msg = (TextView) view.findViewById(R.id.txt_msg);
            main_menu = view.findViewById(R.id.main_rl);
            img_more = view.findViewById(R.id.img_more);
            profile_image = view.findViewById(R.id.profile_image);

            main_menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedIndex = getAdapterPosition();
                    frag.onClickEvent(selectedIndex);

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
        holder.user_name.setText(inboxList.get(position).getSenderName());
        Picasso.get().load(inboxList.get(position).getProfilePhoto())
                .placeholder(R.drawable.cupertino_activity_indicator).into(holder.profile_image);
//        holder.msg.setText(inboxList.get(position).getText());

    }


    @Override
    public int getItemCount() {
        return inboxList.size();
    }
}
