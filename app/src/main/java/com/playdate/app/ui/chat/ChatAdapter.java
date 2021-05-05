package com.playdate.app.ui.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.model.Chat;

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
        chat_list.add(new Chat("According to Forbes", "https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/4p3a7420-copy-1524689604.jpg", OTHER));
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

    }


    public class ViewHolderOther extends RecyclerView.ViewHolder {
        public ViewHolderOther(@NonNull View itemView) {
            super(itemView);
        }
    }

    private class ViewHolderMe extends RecyclerView.ViewHolder {
        public ViewHolderMe(View view) {
            super(view);
        }
    }

    private class ViewHolderOponent extends RecyclerView.ViewHolder {
        public ViewHolderOponent(View view) {
            super(view);
        }
    }
}
