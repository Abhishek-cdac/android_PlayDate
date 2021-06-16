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
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.model.Notification;
import com.playdate.app.model.NotificationData;
import com.playdate.app.ui.chat.request.Onclick;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FragNotificationTypeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int LIKED = 0;
    public static final int FRIENDREQUEST = 1;
    public static final int MATCHED = 2;
    public static final int COMMENT = 3;

    private final Onclick itemClick;
    private String requestId;
    private final Context mcontext;

    private final Picasso picasso;

    private ArrayList<NotificationData> notification_list = new ArrayList<>();


    public FragNotificationTypeAdapter(FragmentActivity activity, ArrayList<NotificationData> lst_notifications, Onclick itemClick) {
        this.mcontext = activity;
        this.notification_list = lst_notifications;
        this.itemClick = itemClick;
        picasso = Picasso.get();

    }

    @Override
    public int getItemCount() {
        return notification_list.size();
    }

    @Override
    public int getItemViewType(int position) {

        switch (notification_list.get(position).getPatternID()) {
            case "FeedLike":
                return LIKED;
            case "FeedComment":
                return COMMENT;
            case "Match":
                return MATCHED;
            case "Friend":
                return FRIENDREQUEST;
            default:
                return -1;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());


        if (viewType == LIKED) {
            view = inflater.inflate(R.layout.row_notification_type_2, parent, false);
            viewHolder = new ViewHolderLiked(view);

        } else if (viewType == MATCHED) {

            view = inflater.inflate(R.layout.row_notification_type_1, parent, false);
            viewHolder = new ViewHolder(view);
        } else if (viewType == FRIENDREQUEST) {
            view = inflater.inflate(R.layout.row_notification_type_1, parent, false);
            viewHolder = new ViewHolder(view);
        } else if (viewType == COMMENT) {

            view = inflater.inflate(R.layout.row_notification_type_4, parent, false);
            viewHolder = new ViewHolderComment(view);
        }


        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == MATCHED) {
            ViewHolder viewHolderMatched = (ViewHolder) holder;
            if (notification_list.get(position).getFriendRequest() != null) {

                if (notification_list.get(position).getFriendRequest().size() > 0) {
                    if (notification_list.get(position).getFriendRequest().get(0).getStatus().toLowerCase().equals("pending")) {

                        viewHolderMatched.rl_notification.setVisibility(View.VISIBLE);

//                    String notifiationId = notification_list.get(position).getNotificationId();
//                    String userId = notification_list.get(position).getUserID();
                        viewHolderMatched.tv_desc.setText(notification_list.get(position).getNotificationMessage());
                        viewHolderMatched.tv_name.setText(notification_list.get(position).getFriendRequest().get(0).getUserInfo().get(0).getUsername());
                        requestId = notification_list.get(position).getFriendRequest().get(0).getRequestId();
                        Log.e("requestId", "" + requestId);
                        picasso.load(notification_list.get(position).getFriendRequest().get(0).getUserInfo().get(0).getProfilePicPath())
                                //.placeholder(R.drawable.cupertino_activity_indicator)
                                .placeholder(R.drawable.profile)
                                .fit()
                                .centerCrop()
                                .into(viewHolderMatched.profile_image);

                        if (notification_list.get(position).isSelected) {
                            viewHolderMatched.rl_notification.setBackgroundColor(mcontext.getResources().getColor(R.color.color_pink_dull));
                        } else {
                            viewHolderMatched.rl_notification.setBackgroundColor(mcontext.getResources().getColor(R.color.white));

                        }

                        viewHolderMatched.rl_notification.setOnLongClickListener(v -> {
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


            viewHolderMatched.iv_right.setOnClickListener(v -> {

                try {
                    itemClick.onItemClicks(v, position, 24, requestId);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            });
            viewHolderMatched.iv_cross.setOnClickListener(v -> {
                try {
                    Log.e("requestId", "" + requestId);

                    itemClick.onItemClicks(v, position, 25, requestId);
                } catch (Exception e) {
                    e.printStackTrace();
                }


                //  removeAt(getAdapterPosition());
            });


        }
        else if (holder.getItemViewType() == LIKED) {

            ViewHolderLiked viewHolderLiked = (ViewHolderLiked) holder;
            viewHolderLiked.tv_name.setText(notification_list.get(position).getmUserInformation().get(0).getUsername());

            Picasso.get().load(notification_list.get(position).getmUserInformation().get(0).getProfilePicPath())
                    .placeholder(R.drawable.cupertino_activity_indicator)
                    .into(viewHolderLiked.profile_image_3);
          /*  Picasso.get().load.(position).get())
                    .placeholder(R.drawable.cupertino_activity_indicator)
                    .into(viewHolderLiked.profile_image);*/
            viewHolderLiked.tv_desc.setText(notification_list.get(position).getNotificationMessage());


        }
        else if (holder.getItemViewType() == COMMENT) {
            ViewHolderComment viewHolderComment = (ViewHolderComment) holder;
            viewHolderComment.tv_name.setText(notification_list.get(position).getmUserInformation().get(0).getUsername());

            Picasso.get().load(notification_list.get(position).getmUserInformation().get(0).getProfilePicPath())
                    .placeholder(R.drawable.cupertino_activity_indicator)
                    .into(viewHolderComment.profile_image_3);
            viewHolderComment.tv_desc.setText(notification_list.get(position).getNotificationMessage());

        }
        else if (holder.getItemViewType() == FRIENDREQUEST) {
            ViewHolder viewHolderMatched = (ViewHolder) holder;
            if (notification_list.get(position).getFriendRequest() != null) {

                if (notification_list.get(position).getFriendRequest().size() > 0) {
                    if (notification_list.get(position).getFriendRequest().get(0).getStatus().toLowerCase().equals("pending")) {

                        viewHolderMatched.rl_notification.setVisibility(View.VISIBLE);

//                    String notifiationId = notification_list.get(position).getNotificationId();
//                    String userId = notification_list.get(position).getUserID();
                        viewHolderMatched.tv_desc.setText(notification_list.get(position).getNotificationMessage());
                        viewHolderMatched.tv_name.setText(notification_list.get(position).getFriendRequest().get(0).getUserInfo().get(0).getUsername());
                        requestId = notification_list.get(position).getFriendRequest().get(0).getRequestId();
                        Log.e("requestId", "" + requestId);
                        picasso.load(notification_list.get(position).getFriendRequest().get(0).getUserInfo().get(0).getProfilePicPath())
                                //.placeholder(R.drawable.cupertino_activity_indicator)
                                .placeholder(R.drawable.profile)
                                .fit()
                                .centerCrop()
                                .into(viewHolderMatched.profile_image);

                        if (notification_list.get(position).isSelected) {
                            viewHolderMatched.rl_notification.setBackgroundColor(mcontext.getResources().getColor(R.color.color_pink_dull));
                        } else {
                            viewHolderMatched.rl_notification.setBackgroundColor(mcontext.getResources().getColor(R.color.white));

                        }

                        viewHolderMatched.rl_notification.setOnLongClickListener(v -> {
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


            viewHolderMatched.iv_right.setOnClickListener(v -> {

                try {
                    itemClick.onItemClicks(v, position, 20, requestId);

                } catch (Exception e) {
                    e.printStackTrace();
                }


            });
            viewHolderMatched.iv_cross.setOnClickListener(v -> {
                try {
                    Log.e("requestId", "" + requestId);

                    itemClick.onItemClicks(v, position, 21, requestId);
                } catch (Exception e) {
                    e.printStackTrace();
                }


                //  removeAt(getAdapterPosition());
            });

        }
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

    public class ViewHolderLiked extends RecyclerView.ViewHolder {
        ImageView profile_image_3, iv_icon_3;
        TextView tv_name, tv_desc;


        public ViewHolderLiked(View view) {
            super(view);
            profile_image_3 = itemView.findViewById(R.id.profile_image_3);
            iv_icon_3 = itemView.findViewById(R.id.iv_icon_3);

            tv_name = itemView.findViewById(R.id.tv_name_noti);
            tv_desc = itemView.findViewById(R.id.tv_desc_noti);


        }
    }

    public class ViewHolderComment extends RecyclerView.ViewHolder {
        ImageView profile_image_3, iv_icon_3;
        TextView tv_name, tv_desc;


        public ViewHolderComment(View view) {
            super(view);
            profile_image_3 = itemView.findViewById(R.id.profile_image_3);
            iv_icon_3 = itemView.findViewById(R.id.iv_icon_3);

            tv_name = itemView.findViewById(R.id.tv_name_noti);
            tv_desc = itemView.findViewById(R.id.tv_desc_noti);


        }
    }
}
