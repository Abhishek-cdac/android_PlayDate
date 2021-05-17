package com.playdate.app.ui.chat;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.model.Chat;
import com.playdate.app.ui.dialogs.DialogWinner;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;

    public static final int OTHER = 0;
    public static final int ME = 1;
    public static final int OPPONENT = 2;
    ArrayList<Chat> chat_list = new ArrayList<>();

    public ChatAdapter() {
        chat_list.add(new Chat("Hey Myron! looks like we matched", "https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/4p3a7420-copy-1524689604.jpg", ME));
        chat_list.add(new Chat("Hey Kayle! yeah it looks like we can have a nice conversation", "https://images.saymedia-content.com/.image/t_share/MTc0MDkwNjUxNDc2OTYwODM0/5-instagram-models-you-should-be-following.png", OPPONENT));
        chat_list.add(new Chat("Nice Smile btw!1", "https://images.saymedia-content.com/.image/t_share/MTc1MDE0NzI4MTg2OTk2NTIz/5-instagram-models-you-should-be-following.png", ME));
        chat_list.add(new Chat("Not better than you.", "https://s29588.pcdn.co/wp-content/uploads/sites/2/2018/08/Claire-Abbott-1.jpg.optimal.jpg", OPPONENT));
        chat_list.add(new Chat("According to Forbes,which entreprenour became first person in history to have net worth of $400 billion?", "https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/4p3a7420-copy-1524689604.jpg", OTHER));
    }

    @Override
    public int getItemCount() {
        return chat_list.size();
    }

    @Override
    public int getItemViewType(int position) {
        int type = chat_list.get(position).getChat_type();
        switch (type) {
            case OTHER:
                return OTHER;

            case ME:
                return ME;

            case OPPONENT:
                return OPPONENT;

        }
        return ME;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;
        RecyclerView.ViewHolder viewHolder = null;
        mContext = parent.getContext();

        if (viewType == ME) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_type_me, parent, false);
            viewHolder = new ViewHolderMe(view);

        } else if (viewType == OPPONENT) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_type_oponent, parent, false);
            viewHolder = new ViewHolderOponent(view);


        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_type_other, parent, false);
            viewHolder = new ViewHolderOther(view);

        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == ME) {
            ViewHolderMe viewHolderMe = (ViewHolderMe) holder;
            Picasso.get().load(chat_list.get(position).getImage())
                    .placeholder(R.drawable.cupertino_activity_indicator)
                    .into(viewHolderMe.iv_profile);
            viewHolderMe.tv_msg.setText(chat_list.get(position).getMessage());
        } else if (holder.getItemViewType() == OPPONENT) {
            ViewHolderOponent viewHolderOponent = (ViewHolderOponent) holder;
            Picasso.get().load(chat_list.get(position).getImage())
                    .placeholder(R.drawable.cupertino_activity_indicator)
                    .into(viewHolderOponent.iv_profile);
            viewHolderOponent.tv_msg.setText(chat_list.get(position).getMessage());

        } else {
            ViewHolderOther viewHolderOther = (ViewHolderOther) holder;
            viewHolderOther.tv_msg.setText(chat_list.get(position).getMessage());

        }
    }

    public void addToList(EditText et_msg) {
        chat_list.add(new Chat(et_msg.getText().toString(), "https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/4p3a7420-copy-1524689604.jpg", ME));
        notifyDataSetChanged();
        et_msg.setText("");
    }


    public class ViewHolderOther extends RecyclerView.ViewHolder {
        TextView tv_msg;
        TextView answer1;

        public ViewHolderOther(@NonNull View itemView) {
            super(itemView);
            tv_msg = itemView.findViewById(R.id.tv_chat_other);
            answer1 = itemView.findViewById(R.id.answer1);
            answer1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), DialogWinner.class);
                    startActivity(intent);
                }
            });
        }
    }

    public class ViewHolderMe extends RecyclerView.ViewHolder {
        ImageView iv_profile;
        TextView tv_msg;

        public ViewHolderMe(View view) {
            super(view);
            iv_profile = view.findViewById(R.id.profile_image_me);
            tv_msg = view.findViewById(R.id.tv_chat);
        }
    }

    public class ViewHolderOponent extends RecyclerView.ViewHolder {
        ImageView iv_profile;
        TextView tv_msg;

        public ViewHolderOponent(View view) {
            super(view);
            iv_profile = view.findViewById(R.id.profile_image_oponent);
            tv_msg = view.findViewById(R.id.tv_chat_oponent);

        }
    }
}
