package com.playdate.app.ui.card_swipe;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.model.TinderSwipe;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class TinderSwipeAdapter extends RecyclerView.Adapter<TinderSwipeAdapter.ViewHolder> {

//    private List<TinderSwipe> list;

//    public TinderSwipeAdapter(List<TinderSwipe> list) {
//        this.list = list;
//    }

    List<TinderSwipe> tinder_list = new ArrayList<>();

    public TinderSwipeAdapter() {

        tinder_list.add(new TinderSwipe("https://s29588.pcdn.co/wp-content/uploads/sites/2/2018/08/Claire-Abbott-1.jpg.optimal.jpg", "adreena helen", "Dancing", "23", false));
        tinder_list.add(new TinderSwipe("https://images.saymedia-content.com/.image/t_share/MTc1MDE0NzI4MTg2OTk2NTIz/5-instagram-models-you-should-be-following.png", "gomes helen", "Singing", "27", true));
        tinder_list.add(new TinderSwipe("https://images.saymedia-content.com/.image/t_share/MTc0MDkwNjUxNDc2OTYwODM0/5-instagram-models-you-should-be-following.png", "jonn den", "Travelling", "29", false));
        tinder_list.add(new TinderSwipe("https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/4p3a7420-copy-1524689604.jpg", "Ramsphy k", "Blogging", "18", true));
        tinder_list.add(new TinderSwipe("https://s29588.pcdn.co/wp-content/uploads/sites/2/2018/08/Claire-Abbott-1.jpg.optimal.jpg", "adreena helen", "Dancing and Blogging", "20", false));
        tinder_list.add(new TinderSwipe("https://images.saymedia-content.com/.image/t_share/MTc1MDE0NzI4MTg2OTk2NTIz/5-instagram-models-you-should-be-following.png", "gomes helen", "Travelling & Blogging", "24", true));
        tinder_list.add(new TinderSwipe("https://images.saymedia-content.com/.image/t_share/MTc1MDE0NzI4MTg2OTk2NTIz/5-instagram-models-you-should-be-following.png", "gomes helen", "Travelling & Blogging", "24", true));
        tinder_list.add(new TinderSwipe("https://images.saymedia-content.com/.image/t_share/MTc1MDE0NzI4MTg2OTk2NTIz/5-instagram-models-you-should-be-following.png", "gomes helen", "Travelling & Blogging", "24", false));
        tinder_list.add(new TinderSwipe("https://images.saymedia-content.com/.image/t_share/MTc1MDE0NzI4MTg2OTk2NTIz/5-instagram-models-you-should-be-following.png", "gomes helen", "Travelling & Blogging", "24", false));


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.tinder_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TinderSwipeAdapter.ViewHolder holder, int position) {
        holder.setData(tinder_list.get(position));
    }

    @Override
    public int getItemCount() {
        return tinder_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name, age, hobby;
        ImageView message;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_name);
            age = itemView.findViewById(R.id.item_age);
            hobby = itemView.findViewById(R.id.item_hobby);
            image = itemView.findViewById(R.id.item_image);
            message = itemView.findViewById(R.id.item_message);
        }

        void setData(TinderSwipe tinderSwipe) {
            Picasso.get()
                    .load(tinderSwipe.getImage())
                    .fit()
                    .centerCrop()
                    .into(image);
            name.setText(tinderSwipe.getName());
            age.setText(tinderSwipe.getAge());
            hobby.setText(tinderSwipe.getHobby());

            if (tinderSwipe.isMessage()) {
                message.setVisibility(View.VISIBLE);
            } else {
                message.setVisibility(View.GONE);
            }
        }
    }


    public List<TinderSwipe> getList() {
        return tinder_list;
    }

    public void setList(List<TinderSwipe> list) {
        this.tinder_list = list;
    }
}

