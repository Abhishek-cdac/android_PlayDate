package com.playdate.app.ui.notification_screen;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.model.NotificationData;
import com.playdate.app.ui.chat.request.Onclick;

import java.util.ArrayList;
import java.util.List;

public class FragNewNotificationAdapter extends RecyclerView.Adapter<FragNewNotificationAdapter.ViewHolder> {
    List<NotificationData> notification_list = new ArrayList<>();
    Onclick itemClick;
    String userId;
    String notifiationId;
    String requestId;


    public FragNewNotificationAdapter(List<NotificationData> lst_notifications, Onclick itemClick) {
        this.notification_list = lst_notifications;
        this.itemClick = itemClick;

    }

    Context mcontext;

    @NonNull
    @Override
    public FragNewNotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_notification_type_1, parent, false);
        return new FragNewNotificationAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FragNewNotificationAdapter.ViewHolder holder, int position) {

        holder.tv_desc.setText(notification_list.get(position).getNotificationMessage());
        Log.e("UserName.", "" + notification_list.get(position).getFriendRequest().get(position).getUserInfo().get(position).getUsername());
        holder.tv_name.setText(notification_list.get(position).getFriendRequest().get(position).getUserInfo().get(position).getUsername());
        requestId = notification_list.get(position).getFriendRequest().get(position).getRequestId();
        notifiationId = notification_list.get(position).getNotificationId();
        userId = notification_list.get(position).getUserID();
    }

    @Override
    public int getItemCount() {
        return notification_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView profile_image, icons, iv_right, iv_cross;
        TextView tv_name, tv_desc;
        RelativeLayout rl_request;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profile_image = itemView.findViewById(R.id.profile_image_1);
            icons = itemView.findViewById(R.id.iv_icon_1);
            tv_name = itemView.findViewById(R.id.tv_name_noti);
            tv_desc = itemView.findViewById(R.id.tv_desc_noti);
            rl_request = itemView.findViewById(R.id.rl_request);
            iv_right = itemView.findViewById(R.id.iv_right);
            iv_cross = itemView.findViewById(R.id.iv_cross);
            icons.setVisibility(View.GONE);

            iv_right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("requestId",""+requestId);
                    itemClick.onItemClicks(v, getAdapterPosition(), 20, requestId);

                }
            });
            iv_cross.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("requestId",""+requestId);


                    itemClick.onItemClicks(v, getAdapterPosition(), 21, requestId);

                  //  removeAt(getAdapterPosition());
                }
            });

        }
    }

    private void removeAt(int adapterPosition) {


        notification_list.remove(adapterPosition);
        notifyItemRemoved(adapterPosition);
        notifyItemRangeChanged(adapterPosition, notification_list.size());


    }


}
