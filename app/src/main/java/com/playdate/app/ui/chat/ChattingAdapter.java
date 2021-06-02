package com.playdate.app.ui.chat;

import android.content.Context;
import android.graphics.Color;
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
    int selectedToDelete = -1;

    public ChattingAdapter(ArrayList<ChatExample> inboxList, Onclick itemClick, FragInbox frag) {
        this.inboxList = inboxList;
        this.itemClick = itemClick;
        this.frag = frag;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView user_name, msg, txt_notify, txt_time;
        public ImageView profile_image, img_more, iv_delete_chat;
        public RelativeLayout main_menu;
        public LinearLayout ll_chat_details;
        public LinearLayout main_ll;

        public MyViewHolder(View view) {
            super(view);
            txt_time = (TextView) view.findViewById(R.id.txt_time);
            user_name = (TextView) view.findViewById(R.id.user_name);
            txt_notify = (TextView) view.findViewById(R.id.txt_notify);
            msg = (TextView) view.findViewById(R.id.txt_msg);
            main_menu = view.findViewById(R.id.main_rl);
            img_more = view.findViewById(R.id.img_more);
            profile_image = view.findViewById(R.id.profile_image);
            ll_chat_details = view.findViewById(R.id.ll_chat_details);
            iv_delete_chat = view.findViewById(R.id.iv_delete_chat);
            main_ll = view.findViewById(R.id.main_ll);
            selectedToDelete = getAdapterPosition();

            main_menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedIndex = getAdapterPosition();
                    frag.onClickEvent(selectedIndex);
                }
            });

//            main_menu.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//                    selectedIndex = getAdapterPosition();
//                    main_menu.setBackgroundColor(mcontext.getResources().getColor(R.color.color_pink_dull));
//                    ll_chat_details.setVisibility(View.GONE);
//                    iv_delete_chat.setVisibility(View.VISIBLE);
//
//                    return true;
//                }
//            });


        }
    }


    @NonNull
    @Override
    public ChattingAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.inbox_list_row, parent, false);
        mcontext = parent.getContext();
        return new ChattingAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.user_name.setText(inboxList.get(position).getSenderName());
        Picasso.get().load(inboxList.get(position).getProfilePhoto())
                .placeholder(R.drawable.cupertino_activity_indicator).into(holder.profile_image);

        holder.main_menu.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (selectedToDelete == position) {
                    holder.main_ll.setBackgroundColor(mcontext.getResources().getColor(R.color.color_pink_dull));
                    holder.ll_chat_details.setVisibility(View.GONE);
                    holder.iv_delete_chat.setVisibility(View.VISIBLE);
                    notifyDataSetChanged();
                }
                //                selectedToDelete = position;
                return true;
            }
        });

        holder.iv_delete_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedToDelete == position) {
                    inboxList.remove(selectedToDelete);
                    notifyDataSetChanged();
                }
                //deleteAPi
            }
        });


//        holder.msg.setText(inboxList.get(position).getText());

    }


    @Override
    public int getItemCount() {
        return inboxList.size();
    }
}
