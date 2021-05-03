package com.playdate.app.ui.anonymous_question;

import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    ArrayList<Comments> list = new ArrayList<>();
    boolean selected = false;
    int selected_index = -1;

    public CommentAdapter() {
        list.add(new Comments("MyronEvans", "hey"));
        list.add(new Comments("MyronEvans", "all goood, whats up?"));
        list.add(new Comments("MyronEvans", "helllo everyone"));
        list.add(new Comments("MyronEvans", "hey"));

    }

    @NonNull
    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_comment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.ViewHolder holder, int position) {
        holder.name.setText(list.get(position).getName());
        holder.desc.setText(list.get(position).getComment());

        if (selected_index == position) {
            Log.d("selected_index", String.valueOf(selected_index));

            Log.d("selected_ index", String.valueOf(selected));

            if (selected) {
                Log.d("selected", String.valueOf(selected_index));
                holder.relativeLayout.setBackgroundColor(Color.parseColor("#88000000"));
                holder.name.setTextColor(Color.parseColor("#ffffff"));
                holder.desc.setTextColor(Color.parseColor("#ffffff"));
                holder.time.setTextColor(Color.parseColor("#ffffff"));
                holder.like.setTextColor(Color.parseColor("#ffffff"));
                holder.reply.setTextColor(Color.parseColor("#ffffff"));
                holder.delete.setVisibility(View.VISIBLE);


            } else {
                Log.d("selected", String.valueOf(selected_index));
                holder.relativeLayout.setBackgroundColor(Color.parseColor("#ffffff"));
                holder.name.setTextColor(Color.parseColor("#000000"));
                holder.desc.setTextColor(Color.parseColor("#000000"));
                holder.time.setTextColor(Color.parseColor("#000000"));
                holder.like.setTextColor(Color.parseColor("#000000"));
                holder.reply.setTextColor(Color.parseColor("#000000"));
                holder.delete.setVisibility(View.GONE);

            }
        }
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
            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selected_index = getAdapterPosition();
                    if (!selected) {
                        Log.d("selected_click", String.valueOf(selected));
                        selected = true;
                        notifyDataSetChanged();
                        Log.d("selected_click_after", String.valueOf(selected));


                    } else {
                        Log.d("selected_click", String.valueOf(selected));
                        selected = false;
                        notifyDataSetChanged();
                    }

                }
            });

        }
    }
}
