package com.playdate.app.ui.notification_screen;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.model.NotificationData;
import com.playdate.app.ui.chat.request.Onclick;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FragNotificationTypeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int LIKED = 0;
    public static final int FRIENDREQUEST = 1;
    public static final int MATCHED = 2;
    public static final int MATCH_RQUEST = 4;
    public static final int DATE_PARTNER = 5;
    public static final int COMMENT = 3;
    public static final int RELATION_REQUEST = 6;
    public static final int FRIENDACCEPTED = 7;
    public static final int RELATIONACCEPTED = 8;

    private final Onclick itemClick;
    private String requestId;
    private String relationRequestId;
    private final Context mcontext;

    private final Picasso picasso;

    private ArrayList<NotificationData> notification_list;


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
            case "MatchRequest":
                return MATCH_RQUEST;
            case "Friend":
                return FRIENDREQUEST;
            case "DatePartner":
                return DATE_PARTNER;
            case "Relationship":
                return RELATION_REQUEST;
            case "RelationAccepted":
                return RELATIONACCEPTED;
            case "FriendAccepted":
                return FRIENDACCEPTED;
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

            view = inflater.inflate(R.layout.row_notification_type_5, parent, false);
            viewHolder = new ViewHolder(view);
        } else if (viewType == FRIENDREQUEST) {
            view = inflater.inflate(R.layout.row_notification_type_5, parent, false);
            viewHolder = new ViewHolder(view);
        } else if (viewType == COMMENT) {
            view = inflater.inflate(R.layout.row_notification_type_4, parent, false);
            viewHolder = new ViewHolderComment(view);
        } else if (viewType == MATCH_RQUEST) {
            view = inflater.inflate(R.layout.row_notification_type_5, parent, false);
            viewHolder = new ViewHolderMatchRequest(view);
        } else if (viewType == DATE_PARTNER) {
            view = inflater.inflate(R.layout.row_notification_type_5, parent, false);
            viewHolder = new ViewHolderMatchRequest(view);
        } else if (viewType == RELATION_REQUEST) {
            view = inflater.inflate(R.layout.row_notification_type_1, parent, false);
            viewHolder = new ViewHolderRelationRequest(view);
        } else if (viewType == FRIENDACCEPTED) {
            view = inflater.inflate(R.layout.row_notification_type_2, parent, false);
            viewHolder = new ViewHolderLiked(view);
        } else if (viewType == RELATIONACCEPTED) {
            view = inflater.inflate(R.layout.row_notification_type_2, parent, false);
            viewHolder = new ViewHolderLiked(view);
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

                        String name = notification_list.get(position).getFriendRequest().get(0).getUserInfo().get(0).getUsername();
                        String desc = notification_list.get(position).getNotificationMessage();

                        String sourceString = "<b>" + name + "</b> " + desc;
                        viewHolderMatched.tv_name.setText(Html.fromHtml(sourceString));

//                        viewHolderMatched.tv_desc.setText();
//                        viewHolderMatched.tv_name.setText();


                        requestId = notification_list.get(position).getFriendRequest().get(0).getRequestId();
                        Log.e("requestId", "" + requestId);
                        picasso.load(notification_list.get(position).getFriendRequest().get(0).getUserInfo().get(0).getProfilePicPath())
                                .placeholder(R.drawable.profile)
                                .fit()
                                .centerCrop()
                                .into(viewHolderMatched.profile_image);

                        if (notification_list.get(position).isSelected) {
                            viewHolderMatched.rl_notification.setBackgroundColor(mcontext.getResources().getColor(R.color.color_pink_dull));
                            viewHolderMatched.main_ll.setBackgroundColor(mcontext.getResources().getColor(R.color.color_pink_dull));

                        } else {
                            viewHolderMatched.rl_notification.setBackgroundColor(mcontext.getResources().getColor(R.color.backgroundColour));
                            viewHolderMatched.main_ll.setBackgroundColor(mcontext.getResources().getColor(R.color.backgroundColour));
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

///// FriendRequest
        } else if (holder.getItemViewType() == LIKED) {

            ViewHolderLiked viewHolderLiked = (ViewHolderLiked) holder;
            picasso.load(notification_list.get(position).getmUserInformation().get(0).getProfilePicPath()).placeholder(R.drawable.profile)
                    .into(viewHolderLiked.profile_image_3);


            String name = notification_list.get(position).getmUserInformation().get(0).getUsername();
            String desc = notification_list.get(position).getNotificationMessage();

            String sourceString = "<b>" + name + "</b> " + desc;
            viewHolderLiked.tv_name.setText(Html.fromHtml(sourceString));

//          viewHolderLiked.tv_name.setText(notification_list.get(position).getmUserInformation().get(0).getUsername());
//          viewHolderLiked.tv_desc.setText(notification_list.get(position).getNotificationMessage());

            if (notification_list.get(position).getmPostInfo().get(0).getMedia().get(0).getMediaType().equals("Image")) {
                picasso.load(notification_list.get(position).getmPostInfo().get(0).getMedia().get(0).getMediaFullPath())
                        .fit()
                        .centerCrop()
                        .into(viewHolderLiked.iv_icon_3);
            } else if (notification_list.get(position).getmPostInfo().get(0).getMedia().get(0).getMediaType().equals("Video")) {
                picasso.load(notification_list.get(position).getmPostInfo().get(0).getMedia().get(0).getMediaThumbName())
                        .fit()
                        .centerCrop()
                        .into(viewHolderLiked.iv_icon_3);
            }

            if (notification_list.get(position).isSelected) {
                viewHolderLiked.rl_notification.setBackgroundColor(mcontext.getResources().getColor(R.color.color_pink_dull));
            } else {
                viewHolderLiked.rl_notification.setBackgroundColor(mcontext.getResources().getColor(R.color.backgroundColour));
            }

            viewHolderLiked.rl_notification.setOnLongClickListener(v -> {
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
//// LIKED
        }
        else if (holder.getItemViewType() == COMMENT) {
            ViewHolderComment viewHolderComment = (ViewHolderComment) holder;
            picasso.load(notification_list.get(position).getmUserInformation().get(0).getProfilePicPath()).placeholder(R.drawable.profile)
                    .into(viewHolderComment.profile_image_3);
            String name = notification_list.get(position).getmUserInformation().get(0).getUsername();
            String desc = notification_list.get(position).getNotificationMessage();

            String sourceString = "<b>" + name + "</b> " + desc;
            viewHolderComment.tv_name.setText(Html.fromHtml(sourceString));

//          viewHolderComment.tv_name.setText(notification_list.get(position).getmUserInformation().get(0).getUsername());
//          viewHolderComment.tv_desc.setText(notification_list.get(position).getNotificationMessage());

            if (notification_list.get(position).isSelected) {
                viewHolderComment.rl_notification.setBackgroundColor(mcontext.getResources().getColor(R.color.color_pink_dull));
            } else {
                viewHolderComment.rl_notification.setBackgroundColor(mcontext.getResources().getColor(R.color.backgroundColour));

            }

            viewHolderComment.rl_notification.setOnLongClickListener(v -> {
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

/// COMMENT
        }
        else if (holder.getItemViewType() == FRIENDREQUEST) {
            ViewHolder viewHolderMatched = (ViewHolder) holder;
            if (notification_list.get(position).getFriendRequest() != null) {

                if (notification_list.get(position).getFriendRequest().size() > 0) {
                    if (notification_list.get(position).getFriendRequest().get(0).getStatus().toLowerCase().equals("pending")) {

                        viewHolderMatched.rl_notification.setVisibility(View.VISIBLE);

//                    String notifiationId = notification_list.get(position).getNotificationId();
//                    String userId = notification_list.get(position).getUserID();

                        String name = notification_list.get(position).getFriendRequest().get(0).getUserInfo().get(0).getUsername();
                        String desc = notification_list.get(position).getNotificationMessage();

                        String sourceString = "<b>" + name + "</b> " + desc;
                        viewHolderMatched.tv_name.setText(Html.fromHtml(sourceString));
                        requestId = notification_list.get(position).getFriendRequest().get(0).getRequestId();
                        Log.e("requestId", "" + requestId);
                        picasso.load(notification_list.get(position).getFriendRequest().get(0).getUserInfo().get(0).getProfilePicPath())
                                .placeholder(R.drawable.profile)
                                .fit()
                                .centerCrop()
                                .into(viewHolderMatched.profile_image);

                        if (notification_list.get(position).isSelected) {
                            viewHolderMatched.rl_notification.setBackgroundColor(mcontext.getResources().getColor(R.color.color_pink_dull));
                            viewHolderMatched.main_ll.setBackgroundColor(mcontext.getResources().getColor(R.color.color_pink_dull));
                        } else {
                            viewHolderMatched.rl_notification.setBackgroundColor(mcontext.getResources().getColor(R.color.backgroundColour));
                            viewHolderMatched.main_ll.setBackgroundColor(mcontext.getResources().getColor(R.color.backgroundColour));

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
        else if (holder.getItemViewType() == MATCH_RQUEST) {
            ViewHolderMatchRequest viewHolderMatchRequest = (ViewHolderMatchRequest) holder;
            picasso.load(notification_list.get(position).getmUserInformation().get(0).getProfilePicPath()).placeholder(R.drawable.profile)
                    .into(viewHolderMatchRequest.profile_image);
//            viewHolderMatchRequest.tv_name.setText(notification_list.get(position).getmUserInformation().get(0).getUsername());
//            viewHolderMatchRequest.tv_desc.setText(notification_list.get(position).getNotificationMessage());

            String name = notification_list.get(position).getmUserInformation().get(0).getUsername();
            String desc = notification_list.get(position).getNotificationMessage();
            String sourceString = "<b>" + name + "</b> " + desc;
            viewHolderMatchRequest.tv_name.setText(Html.fromHtml(sourceString));


            if (notification_list.get(position).isSelected) {
                viewHolderMatchRequest.rl_notification.setBackgroundColor(mcontext.getResources().getColor(R.color.color_pink_dull));
                viewHolderMatchRequest.main_ll.setBackgroundColor(mcontext.getResources().getColor(R.color.color_pink_dull));
            } else {
                viewHolderMatchRequest.rl_notification.setBackgroundColor(mcontext.getResources().getColor(R.color.backgroundColour));
                viewHolderMatchRequest.main_ll.setBackgroundColor(mcontext.getResources().getColor(R.color.backgroundColour));

            }

            viewHolderMatchRequest.rl_notification.setOnLongClickListener(v -> {
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
            viewHolderMatchRequest.iv_right.setVisibility(View.GONE);
            viewHolderMatchRequest.iv_cross.setVisibility(View.GONE);

            viewHolderMatchRequest.iv_right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            viewHolderMatchRequest.iv_cross.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });


        }
        else if (holder.getItemViewType() == DATE_PARTNER) {
            ViewHolderMatchRequest viewHolderMatchRequest = (ViewHolderMatchRequest) holder;
            viewHolderMatchRequest.tv_desc.setVisibility(View.VISIBLE);

            String name = notification_list.get(position).getmUserInformation().get(0).getUsername();
            String desc = notification_list.get(position).getNotificationMessage();
            String sourceString = "<b>" + name + "</b> " + desc;
            viewHolderMatchRequest.tv_name.setText(Html.fromHtml(sourceString));


//          viewHolderMatchRequest.tv_name.setText(notification_list.get(position).getmUserInformation().get(0).getUsername());
            //   viewHolderMatchRequest.tv_desc.setText(notification_list.get(position).getNotificationMessage());
            picasso.load(notification_list.get(position).getmUserInformation().get(0).getProfilePicPath()).placeholder(R.drawable.profile)
                    .into(viewHolderMatchRequest.profile_image);

            if (notification_list.get(position).isSelected) {
                viewHolderMatchRequest.rl_notification.setBackgroundColor(mcontext.getResources().getColor(R.color.color_pink_dull));
                viewHolderMatchRequest.main_ll.setBackgroundColor(mcontext.getResources().getColor(R.color.color_pink_dull));
            } else {
                viewHolderMatchRequest.rl_notification.setBackgroundColor(mcontext.getResources().getColor(R.color.backgroundColour));
                viewHolderMatchRequest.main_ll.setBackgroundColor(mcontext.getResources().getColor(R.color.backgroundColour));


            }
            viewHolderMatchRequest.rl_notification.setOnLongClickListener(v -> {
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
            viewHolderMatchRequest.iv_right.setVisibility(View.GONE);
            viewHolderMatchRequest.iv_cross.setVisibility(View.GONE);
            viewHolderMatchRequest.iv_right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClick.onItemClicks(v, position, 24, requestId);
                }
            });

            viewHolderMatchRequest.iv_cross.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("requestId", "" + requestId);

                    itemClick.onItemClicks(v, position, 25, requestId);
                }
            });
        } else if (holder.getItemViewType() == FRIENDACCEPTED) {
            ViewHolderLiked viewHolderLiked = (ViewHolderLiked) holder;
            viewHolderLiked.iv_icon_3.setVisibility(View.GONE);

            String name = notification_list.get(position).getmUserInformation().get(0).getUsername();
            String desc = notification_list.get(position).getNotificationMessage();
            String sourceString = name + " " + desc;
            viewHolderLiked.tv_name.setText(Html.fromHtml(sourceString));

            picasso.load(notification_list.get(position).getmUserInformation().get(0).getProfilePicPath()).placeholder(R.drawable.profile)
                    .into(viewHolderLiked.profile_image_3);
        } else if (holder.getItemViewType() == RELATIONACCEPTED) {
            ViewHolderLiked viewHolderLiked = (ViewHolderLiked) holder;
            viewHolderLiked.iv_icon_3.setVisibility(View.GONE);

            String name = notification_list.get(position).getmUserInformation().get(0).getUsername();
            String desc = notification_list.get(position).getNotificationMessage();
            String sourceString = name + " " + desc;
            viewHolderLiked.tv_name.setText(Html.fromHtml(sourceString));


            picasso.load(notification_list.get(position).getmUserInformation().get(0).getProfilePicPath()).placeholder(R.drawable.profile)
                    .into(viewHolderLiked.profile_image_3);
        }else if (holder.getItemViewType() == RELATION_REQUEST) {
            ViewHolderRelationRequest viewHolderRelationRequest = (ViewHolderRelationRequest) holder;
            relationRequestId = notification_list.get(position).getmRelationRequest().get(0).getmRequestId();

            String name = notification_list.get(position).getmUserInformation().get(0).getUsername();
            String desc = notification_list.get(position).getNotificationMessage();
            String sourceString = "<b>" + name + "</b> " + desc;
            viewHolderRelationRequest.tv_name.setText(Html.fromHtml(sourceString));
            picasso.load(notification_list.get(position).getmUserInformation().get(0).getProfilePicPath()).placeholder(R.drawable.profile)
                    .into(viewHolderRelationRequest.profile_image);

            viewHolderRelationRequest.iv_right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        itemClick.onItemClicks(v, position, 30, relationRequestId);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            viewHolderRelationRequest.iv_cross.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Log.e("relationRequestId", "" + relationRequestId);
                        itemClick.onItemClicks(v, position, 31, relationRequestId);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView profile_image, icons, iv_right, iv_cross;
        TextView tv_name, tv_desc;
        LinearLayout main_ll;

        RelativeLayout rl_request, rl_notification;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            main_ll = itemView.findViewById(R.id.main_ll);
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

        RelativeLayout rl_notification;

        public ViewHolderLiked(View view) {
            super(view);
            profile_image_3 = itemView.findViewById(R.id.profile_image_3);
            iv_icon_3 = itemView.findViewById(R.id.iv_icon_3);
            rl_notification = itemView.findViewById(R.id.rl_header);

            tv_name = itemView.findViewById(R.id.tv_name_noti);
            tv_desc = itemView.findViewById(R.id.tv_desc_noti);


        }
    }

    public class ViewHolderComment extends RecyclerView.ViewHolder {
        ImageView profile_image_3, iv_icon_3;
        TextView tv_name, tv_desc;
        RelativeLayout rl_notification;


        public ViewHolderComment(View view) {
            super(view);
            profile_image_3 = itemView.findViewById(R.id.profile_image_3);
            iv_icon_3 = itemView.findViewById(R.id.iv_icon_3);
            rl_notification = itemView.findViewById(R.id.rl_header);

            tv_name = itemView.findViewById(R.id.tv_name_noti);
            tv_desc = itemView.findViewById(R.id.tv_desc_noti);


        }
    }

    public class ViewHolderMatchRequest extends RecyclerView.ViewHolder {
        ImageView profile_image, icons, iv_right, iv_cross;
        TextView tv_name, tv_desc;
        RelativeLayout rl_request, rl_notification;
        LinearLayout main_ll;

        public ViewHolderMatchRequest(@NonNull View itemView) {
            super(itemView);
            main_ll = itemView.findViewById(R.id.main_ll);
            profile_image = itemView.findViewById(R.id.profile_image_1);
            rl_notification = itemView.findViewById(R.id.rl_notification);
            icons = itemView.findViewById(R.id.iv_icon_1);
            tv_name = itemView.findViewById(R.id.tv_name_noti);
            tv_desc = itemView.findViewById(R.id.tv_desc_noti);
            rl_request = itemView.findViewById(R.id.rl_request);
            iv_right = itemView.findViewById(R.id.iv_right);
            iv_cross = itemView.findViewById(R.id.iv_cross);
            //  icons.setVisibility(View.GONE);
            icons.setImageResource(R.drawable.playdate_pink);
        }

    }


public class ViewHolderRelationRequest extends RecyclerView.ViewHolder {
    ImageView profile_image, icons, iv_right, iv_cross;
    TextView tv_name, tv_desc;
    RelativeLayout rl_request, rl_notification;


    public ViewHolderRelationRequest(@NonNull View itemView) {
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
}