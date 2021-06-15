package com.playdate.app.ui.notification_screen;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.model.FriendRequest;
import com.playdate.app.model.NotificationData;
import com.playdate.app.ui.chat.ChatBottomSheet;
import com.playdate.app.ui.chat.request.Onclick;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FragNewNotificationAdapter extends RecyclerView.Adapter<FragNewNotificationAdapter.ViewHolder> {
    private ArrayList<NotificationData> notification_list = new ArrayList<>();
    private final Onclick itemClick;
    private String requestId;
    private final Context mcontext;

    private final Picasso picasso;

    public FragNewNotificationAdapter(FragmentActivity activity, ArrayList<NotificationData> lst_notifications, Onclick itemClick) {
        this.mcontext = activity;
        this.notification_list = lst_notifications;
        this.itemClick = itemClick;
        picasso = Picasso.get();

    }


    @NonNull
    @Override
    public FragNewNotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_notification_type_1, parent, false);
        return new FragNewNotificationAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FragNewNotificationAdapter.ViewHolder holder, int position) {
        if (notification_list.get(position).getFriendRequest() != null) {

            if (notification_list.get(position).getFriendRequest().size() > 0) {
                if (notification_list.get(position).getFriendRequest().get(0).getStatus().toLowerCase().equals("pending")) {

                    holder.rl_notification.setVisibility(View.VISIBLE);

//                    String notifiationId = notification_list.get(position).getNotificationId();
//                    String userId = notification_list.get(position).getUserID();
                    holder.tv_desc.setText(notification_list.get(position).getNotificationMessage());
                    holder.tv_name.setText(notification_list.get(position).getFriendRequest().get(0).getUserInfo().get(0).getUsername());
                    requestId = notification_list.get(position).getFriendRequest().get(0).getRequestId();
                    Log.e("requestId", "" + requestId);
                    picasso.load(notification_list.get(position).getFriendRequest().get(0).getUserInfo().get(0).getProfilePicPath())
                            //.placeholder(R.drawable.cupertino_activity_indicator)
                            .placeholder(R.drawable.profile)
                            .fit()
                            .centerCrop()
                            .into(holder.profile_image);

                    if (notification_list.get(position).isSelected) {
                        holder.rl_notification.setBackgroundColor(mcontext.getResources().getColor(R.color.color_pink_dull));
                    } else {
                        holder.rl_notification.setBackgroundColor(mcontext.getResources().getColor(R.color.white));

                    }

                    holder.rl_notification.setOnLongClickListener(v -> {
                        Log.e("relativeLayout", "relativeLayout" + position);
                        if (!notification_list.get(position).isSelected) {

                            for (int i = 0; i < notification_list.size(); i++) {
                                if (position != i) {
                                    notification_list.get(i).setSelected(false);
                                } else {
                                    notification_list.get(position).setSelected(true);
                                    itemClick.onItemClicks(v, position, 11, notification_list.get(position).getNotificationId(), notification_list.get(position).getUserID());
                                }
                            }

                            notifyDataSetChanged();


                        } else {
                            notification_list.get(position).setSelected(false);
                            notifyDataSetChanged();
                        }
                        return true;
                    });
                } else {
                    //    iv_send_request.setImageResource(R.drawable.sent_request);
                }


            }
        }

//        try {
//            if (notification_list.get(position).getFriendRequest() != null) {
//                if (notification_list.get(position).getFriendRequest().size() == 0) {
//                    List<FriendRequest> lst = new ArrayList<>();
//                    FriendRequest fr = new FriendRequest();
//                    fr.setStatus("Pending");
//                    lst.add(fr);
//                    notification_list.get(position).setFriendRequest(lst);
//    //                    itemClick.onItemClicks(v, position, 10, userId);
//                    notifyDataSetChanged();
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        holder.iv_right.setOnClickListener(v -> {

            try {
                if (notification_list.get(position).getPatternID().equals("Friend")) {
                    itemClick.onItemClicks(v, position, 20, requestId);
                } else if (notification_list.get(position).getPatternID().equals("Match")) {
                    itemClick.onItemClicks(v, position ,24, requestId);
                }
                else if(notification_list.get(position).getPatternID().equals("FeedComment")){

                }  else if(notification_list.get(position).getPatternID().equals("FeedLike")){

                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        });
        holder.iv_cross.setOnClickListener(v -> {
            try {
                Log.e("requestId", "" + requestId);

                if (notification_list.get(position).getPatternID().equals("Friend")) {
                    itemClick.onItemClicks(v, position, 21, requestId);
                } else if (notification_list.get(position).getPatternID().equals("Match")) {
                    itemClick.onItemClicks(v, position, 25, requestId);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            //  removeAt(getAdapterPosition());
        });


    }

    @Override
    public int getItemCount() {

        return notification_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView profile_image, icons, iv_right, iv_cross;
        TextView tv_name, tv_desc;
        RelativeLayout rl_request, rl_notification;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profile_image = itemView.findViewById(R.id.profile_image_1);
            rl_notification = itemView.findViewById(R.id.rl_notification);
            icons = itemView.findViewById(R.id.iv_icon_1);
            tv_name = itemView.findViewById(R.id.tv_name_noti);
            tv_desc = itemView.findViewById(R.id.tv_desc_noti);
            rl_request = itemView.findViewById(R.id.rl_request);
            iv_right = itemView.findViewById(R.id.iv_right);
            iv_cross = itemView.findViewById(R.id.iv_cross);
            icons.setVisibility(View.GONE);




        }
    }

    private void removeAt(int adapterPosition) {


        notification_list.remove(adapterPosition);
        notifyItemRemoved(adapterPosition);
        notifyItemRangeChanged(adapterPosition, notification_list.size());


    }


}
