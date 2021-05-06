package com.playdate.app.ui.notification_screen;

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
import com.playdate.app.model.Notification;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FragNotificationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    public static final int LIKED = 0;
    public static final int FRNDREQUEST = 1;
    public static final int MATCHED = 2;
    ArrayList<Notification> list = new ArrayList<>();


    public FragNotificationAdapter() {
        list.add(new Notification("https://images.saymedia-content.com/.image/t_share/MTc0MDkwNjUxNDc2OTYwODM0/5-instagram-models-you-should-be-following.png", "KayleJennar", "", MATCHED, ""));
        list.add(new Notification("https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/4p3a7420-copy-1524689604.jpg", "NetalliSMith", "", LIKED, "M"));
        list.add(new Notification("https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/4p3a7420-copy-1524689604.jpg", "NetalliSmith", "https://blog.ainfluencer.com/wp-content/uploads/2021/01/Cinta-Laura-868x1024.jpg", LIKED, "L"));
        list.add(new Notification("https://images.saymedia-content.com/.image/t_share/MTc1MDE0NzI4MTg2OTk2NTIz/5-instagram-models-you-should-be-following.png", "MaryJo719", "https://blog.ainfluencer.com/wp-content/uploads/2021/01/Cinta-Laura-868x1024.jpg", LIKED, "L"));
        list.add(new Notification("https://hips.hearstapps.com/esquireuk.cdnds.net/17/17/elizabeth-turner.jpg", "CassieJonas", "", LIKED, "M"));
        list.add(new Notification("https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/4p3a7420-copy-1524689604.jpg", "NetaliaSmith", "", LIKED, "A"));
        list.add(new Notification("https://images.saymedia-content.com/.image/t_share/MTc1MDE0NzI4MTg2OTk2NTIz/5-instagram-models-you-should-be-following.png", "CristinaXO", "", LIKED, "F"));
        list.add(new Notification("https://hips.hearstapps.com/esquireuk.cdnds.net/17/17/elizabeth-turner.jpg", "KayleJennar", "https://blog.ainfluencer.com/wp-content/uploads/2021/01/Cinta-Laura-868x1024.jpg", LIKED, "L"));
    }

    @NonNull


    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        int type = list.get(position).getNoti_type();
        switch (type) {
            case LIKED:
                return LIKED;
            case MATCHED:
                return MATCHED;
        }
        return LIKED;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder viewHolder = null;
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == LIKED) {
            view = inflater.inflate(R.layout.row_notification_type_1, parent, false);
            viewHolder = new ViewHolderLiked(view);
        } else {
            view = inflater.inflate(R.layout.row_notification_type_3, parent, false);
            viewHolder = new ViewHolderMatched(view);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == MATCHED) {
            ViewHolderMatched viewHolderMatched = (ViewHolderMatched) holder;
            Picasso.get().load(list.get(position).getProfileImage())
                    .placeholder(R.drawable.cupertino_activity_indicator)
                    .into(viewHolderMatched.profile_image);
            viewHolderMatched.tv_name.setText(list.get(position).getName());

        } else {
            ViewHolderLiked viewHolderLiked = (ViewHolderLiked) holder;
            Picasso.get().load(list.get(position).getProfileImage())
                    .placeholder(R.drawable.cupertino_activity_indicator)
                    .into(viewHolderLiked.profile_image);
            viewHolderLiked.tv_name.setText(list.get(position).getName());

            if (list.get(position).getInnerType() == "M") {
                viewHolderLiked.tv_desc.setText("messaged you");
                viewHolderLiked.icons.setImageResource(R.drawable.message_icon);
                viewHolderLiked.rl_request.setVisibility(View.GONE);

            } else if (list.get(position).getInnerType() == "L") {
                viewHolderLiked.tv_desc.setText("liked your photo");
                Picasso.get().load(list.get(position).getLikedPhoto())
                        .placeholder(R.drawable.cupertino_activity_indicator)
                        .into(viewHolderLiked.icons);
                viewHolderLiked.icons.setMinimumHeight(20);
                viewHolderLiked.icons.setMinimumWidth(10);

                viewHolderLiked.rl_request.setVisibility(View.GONE);

            } else if (list.get(position).getInnerType() == "A") {
                viewHolderLiked.tv_desc.setText("asked your out");
                viewHolderLiked.icons.setImageResource(R.drawable.playdate_pink);
                viewHolderLiked.rl_request.setVisibility(View.GONE);

            } else {
                viewHolderLiked.tv_desc.setText("sent you a friend request");
                viewHolderLiked.icons.setVisibility(View.GONE);
                viewHolderLiked.rl_request.setVisibility(View.VISIBLE);

            }


        }

    }

    public class ViewHolderLiked extends RecyclerView.ViewHolder {
        ImageView profile_image, icons;
        TextView tv_name, tv_desc;
        RelativeLayout rl_request;

        public ViewHolderLiked(View view) {
            super(view);
            profile_image = view.findViewById(R.id.profile_image_1);
            icons = view.findViewById(R.id.iv_icon_1);
            tv_name = view.findViewById(R.id.tv_name_noti);
            tv_desc = view.findViewById(R.id.tv_desc_noti);
            rl_request = view.findViewById(R.id.rl_request);
        }
    }

    public class ViewHolderMatched extends RecyclerView.ViewHolder {
        ImageView profile_image, icons;
        TextView tv_name, tv_desc;

        public ViewHolderMatched(View view) {
            super(view);
            profile_image = view.findViewById(R.id.profile_image_3);
            icons = view.findViewById(R.id.iv_icon_3);
            tv_name = view.findViewById(R.id.tv_name_noti);
            tv_desc = view.findViewById(R.id.tv_desc_noti);

        }
    }
}
