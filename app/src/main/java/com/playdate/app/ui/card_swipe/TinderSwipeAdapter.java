package com.playdate.app.ui.card_swipe;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.model.Interest;
import com.playdate.app.model.MatchListUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class TinderSwipeAdapter extends RecyclerView.Adapter<TinderSwipeAdapter.ViewHolder> {

    ArrayList<Interest> lst_interest;
    List<MatchListUser> tinder_list;

    public TinderSwipeAdapter(List<MatchListUser> tinder_list, ArrayList<Interest> lst_interest) {
        this.tinder_list = tinder_list;
        this.lst_interest = lst_interest;
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
        ImageView image, maximise;
        TextView name, age, hobby;
        ImageView message;
        ImageView item_premium;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_name);
            age = itemView.findViewById(R.id.item_age);
            hobby = itemView.findViewById(R.id.item_hobby);
            image = itemView.findViewById(R.id.item_image);
            message = itemView.findViewById(R.id.item_message);
            item_premium = itemView.findViewById(R.id.item_premium);
            maximise = itemView.findViewById(R.id.item_fullScreen);
            maximise.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    image.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

                }
            });
        }

        void setData(MatchListUser user) {
            Picasso.get()
                    .load(user.getProfilePicPath())
                    .fit()
                    .centerCrop()
                    .into(image);
            name.setText(user.getFullName());
            age.setText("" + user.getAge());

            String ints = "";
            if (null != lst_interest) {
                for (int i = 0; i < lst_interest.size(); i++) {
                    for (int j = 0; j < user.getInterested().size(); j++) {
                        if (lst_interest.get(i).get_id().equals(user.getInterested().get(j))) {
                            if (ints.isEmpty()) {
                                ints = lst_interest.get(i).getName();
                            } else {
                                ints = ints +","+ lst_interest.get(i).getName();
                            }

                            break;
                        }
                    }
                }
            }


            hobby.setText(ints);

            if (user.getPaymentMode().equals("1")) {
                item_premium.setVisibility(View.VISIBLE);
            } else {
                item_premium.setVisibility(View.INVISIBLE);
            }
        }
    }


    public List<MatchListUser> getList() {
        return tinder_list;
    }

    public void setList(List<MatchListUser> list) {
        this.tinder_list = list;
    }
}

