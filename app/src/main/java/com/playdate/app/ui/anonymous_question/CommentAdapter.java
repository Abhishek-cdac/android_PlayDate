package com.playdate.app.ui.anonymous_question;

import android.app.Activity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    ArrayList<Comments> list = new ArrayList<>();
    //    boolean selected = false;
//    int selected_index = -1;
    Context mContext;


    onCommentDelete ref;

    public CommentAdapter(AnonymousQuestionActivity activity) {
        ref = activity;
        list.add(new Comments("MyronEvans", "hey", false, false));
        list.add(new Comments("MyronEvans", "all goood, whats up?", false, false));
        list.add(new Comments("MyronEvans", "helllo everyone", false, false));
        list.add(new Comments("MyronEvans", "hey", false, false));
    }

    @NonNull
    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_comment, parent, false);
        mContext = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.ViewHolder holder, int position) {
        holder.name.setText(list.get(position).getName());
        holder.desc.setText(list.get(position).getComment());


//        if (selected_index == position) {

        if (list.get(position).isSelected) {
            holder.relativeLayout.setBackgroundColor(Color.parseColor("#88000000"));
            holder.name.setTextColor(mContext.getResources().getColor(R.color.white));
            holder.desc.setTextColor(mContext.getResources().getColor(R.color.white));
            holder.time.setTextColor(mContext.getResources().getColor(R.color.white));
            holder.like.setTextColor(mContext.getResources().getColor(R.color.white));
            holder.reply.setTextColor(mContext.getResources().getColor(R.color.white));
            holder.delete.setVisibility(View.VISIBLE);


        } else {
            holder.relativeLayout.setBackgroundColor(mContext.getResources().getColor(R.color.white));
            holder.name.setTextColor(mContext.getResources().getColor(R.color.black));
            holder.desc.setTextColor(mContext.getResources().getColor(R.color.black));
            holder.time.setTextColor(mContext.getResources().getColor(R.color.black));
            holder.like.setTextColor(mContext.getResources().getColor(R.color.black));
            holder.reply.setTextColor(mContext.getResources().getColor(R.color.black));
            holder.delete.setVisibility(View.GONE);

        }
//        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, desc, like, reply, time;
        RelativeLayout relativeLayout;
        ImageView delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.comment_name);
            desc = itemView.findViewById(R.id.comment_desc);
            like = itemView.findViewById(R.id.comment_like);
            reply = itemView.findViewById(R.id.comment_reply);
            time = itemView.findViewById(R.id.comment_time);
            delete = itemView.findViewById(R.id.dustbin);
            relativeLayout = itemView.findViewById(R.id.card_comment);
            name.setTypeface(Typeface.DEFAULT_BOLD);


            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int selected_index = getAdapterPosition();
                    list.get(selected_index).setDeleted(true);

                    commentdeleted(selected_index);

                }
            });
            relativeLayout.setOnLongClickListener(v -> {
                int selected_index = getAdapterPosition();
                if (!list.get(selected_index).isSelected) {

                    for (int i = 0; i < list.size(); i++) {
                        if (selected_index != i) {
                            list.get(i).setSelected(false);
                        } else {
                            list.get(selected_index).setSelected(true);
                        }

                    }

                    notifyDataSetChanged();
                } else {
                    list.get(selected_index).setSelected(false);
                    notifyDataSetChanged();
                }
                return true;
            });


        }


    }

    private void commentdeleted(int selected_index) {
        if (list.get(selected_index).isDeleted) {
            FragmentManager fragmentManager = ((AppCompatActivity) mContext).getSupportFragmentManager();
            FragCommentDeleted deleted = new FragCommentDeleted();
            deleted.show(fragmentManager, "comment deleted");
            list.remove(selected_index);
            notifyDataSetChanged();
            ref.ChangeCount(list.size());
        } else {
            //code for undo
        }

    }
}
