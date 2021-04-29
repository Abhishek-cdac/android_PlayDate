package com.playdate.app.ui.dashboard.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.model.Suggestions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SuggestedFriendAdapter extends RecyclerView.Adapter<SuggestedFriendAdapter.ViewHolder> {
    ArrayList<Suggestions> suggestions_list = new ArrayList<>();

    public SuggestedFriendAdapter() {
        suggestions_list.add(new Suggestions("adreena helen", "https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/4p3a7420-copy-1524689604.jpg", false, false));
        suggestions_list.add(new Suggestions("gomes helen", "https://images.saymedia-content.com/.image/t_share/MTc0MDkwNjUxNDc2OTYwODM0/5-instagram-models-you-should-be-following.png", false, true));
        suggestions_list.add(new Suggestions("jonn den", "https://images.saymedia-content.com/.image/t_share/MTc1MDE0NzI4MTg2OTk2NTIz/5-instagram-models-you-should-be-following.png", false, true));
        suggestions_list.add(new Suggestions("Ramsphy k", "https://s29588.pcdn.co/wp-content/uploads/sites/2/2018/08/Claire-Abbott-1.jpg.optimal.jpg", true, false));
        suggestions_list.add(new Suggestions("adreena helen", "https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/4p3a7420-copy-1524689604.jpg", false, true));
        suggestions_list.add(new Suggestions("gomes helen", "https://images.saymedia-content.com/.image/t_share/MTc0MDkwNjUxNDc2OTYwODM0/5-instagram-models-you-should-be-following.png", true, false));
        suggestions_list.add(new Suggestions("jonn den", "https://images.saymedia-content.com/.image/t_share/MTc1MDE0NzI4MTg2OTk2NTIz/5-instagram-models-you-should-be-following.png", false, false));
        suggestions_list.add(new Suggestions("Ramsphy k", "https://s29588.pcdn.co/wp-content/uploads/sites/2/2018/08/Claire-Abbott-1.jpg.optimal.jpg", true, true));
        suggestions_list.add(new Suggestions("adreena helen", "https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/4p3a7420-copy-1524689604.jpg", false, false));
        suggestions_list.add(new Suggestions("gomes helen", "https://images.saymedia-content.com/.image/t_share/MTc0MDkwNjUxNDc2OTYwODM0/5-instagram-models-you-should-be-following.png", false, true));
        suggestions_list.add(new Suggestions("jonn den", "https://images.saymedia-content.com/.image/t_share/MTc1MDE0NzI4MTg2OTk2NTIz/5-instagram-models-you-should-be-following.png", false, true));
        suggestions_list.add(new Suggestions("Ramsphy k", "https://s29588.pcdn.co/wp-content/uploads/sites/2/2018/08/Claire-Abbott-1.jpg.optimal.jpg", true, false));
    }

    Context mcontext;

    @NonNull
    @Override
    public SuggestedFriendAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_suggested, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SuggestedFriendAdapter.ViewHolder holder, int position) {
        holder.name.setText(suggestions_list.get(position).getName());
//        holder.image.setImageResource(suggestions_list.get(position).getImageUrl());

        Picasso.get().load(suggestions_list.get(position).getImageUrl())
                .placeholder(R.drawable.cupertino_activity_indicator)
                .into(holder.image);

        if (suggestions_list.get(position).isRequestSent()) {
            holder.request.setImageResource(R.drawable.sent_request_sel);
        } else {
            holder.request.setImageResource(R.drawable.sent_request);
        }

        if (suggestions_list.get(position).isPremium()) {
            holder.diamond.setVisibility(View.VISIBLE);
        } else {
            holder.diamond.setVisibility(View.GONE);

        }


    }

    @Override
    public int getItemCount() {
        return suggestions_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView image, request, diamond;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name_friend);
            image = itemView.findViewById(R.id.image_friend);
            request = itemView.findViewById(R.id.friend_request);
            diamond = itemView.findViewById(R.id.diamond);

            request.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    suggestions_list.get(getPosition()).setRequestSent(true);
                    SuggestedFriendAdapter.this.notifyDataSetChanged();
                }
            });


            


        }
    }
}
