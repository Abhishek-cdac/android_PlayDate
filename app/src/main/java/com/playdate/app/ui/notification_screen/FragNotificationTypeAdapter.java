package com.playdate.app.ui.notification_screen;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
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

import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.playdate.app.R;
import com.playdate.app.model.NotificationData;
import com.playdate.app.ui.chat.request.Onclick;
import com.playdate.app.ui.date.DateBaseActivity;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

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
    public static final int CHAT_REQUEST = 9;
    public static final int CHATACCEPTED = 10;
    public static final int POSTTAGGED = 11;
    public static final int VIEWMORE = 100;
    public boolean showLoadmore = true;
    private final Onclick itemClick;
    private String relationRequestId;
    private final Context mContext;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
    private final Picasso picasso;

    private final ArrayList<NotificationData> notification_list;

    private final FragNotification fragNotification;

    public FragNotificationTypeAdapter(FragmentActivity activity, ArrayList<NotificationData> lst_notifications, Onclick itemClick, FragNotification fragNotification) {
        this.mContext = activity;
        this.notification_list = lst_notifications;
        this.itemClick = itemClick;
        picasso = Picasso.get();
        this.fragNotification = fragNotification;

    }

    @Override
    public int getItemCount() {
        if (notification_list == null)
            return 0;
        return notification_list.size();
    }

    @Override
    public int getItemViewType(int position) {

        switch (notification_list.get(position).getPatternID()) {
            case "FeedLike":
                return LIKED;
            case "FeedComment":
                return COMMENT;
            case "Match":  //"sent you match request
                return MATCHED;
            case "MatchRequest":
                return MATCH_RQUEST;
            case "Friend":  //"sent you friend request
                return FRIENDREQUEST;
            case "DatePartner":
                return DATE_PARTNER;
            case "Relationship":
                return RELATION_REQUEST;
            case "RelationAccepted":
                return RELATIONACCEPTED;
            case "FriendAccepted":
                return FRIENDACCEPTED;
            case "ChatRequest": // sent you chat request
                return CHAT_REQUEST;
            case "ChatAccepted":  // Chat request accepted successfully
                return CHATACCEPTED;
            case "Post": // post tagged you
                return POSTTAGGED;
            case "ViewMore": // post tagged you
                return VIEWMORE;
            default:
                return -1;
        }
    }

    @NonNull
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
            view = inflater.inflate(R.layout.row_notification_type_6, parent, false);
            viewHolder = new ViewHolderLiked(view);
        } else if (viewType == RELATIONACCEPTED) {
            view = inflater.inflate(R.layout.row_notification_type_6, parent, false);
            viewHolder = new ViewHolderLiked(view);
        } else if (viewType == CHAT_REQUEST) {
            view = inflater.inflate(R.layout.row_notification_type_5, parent, false);
            viewHolder = new ViewHolderChatRequest(view);
        } else if (viewType == CHATACCEPTED) {
            view = inflater.inflate(R.layout.row_notification_type_6, parent, false);
            viewHolder = new ViewHolderLiked(view);
        } else if (viewType == POSTTAGGED) {
            view = inflater.inflate(R.layout.row_notification_type_2, parent, false);
            viewHolder = new ViewHolderTagged(view);
        } else if (viewType == VIEWMORE) {
            view = inflater.inflate(R.layout.row_view_more, parent, false);
            viewHolder = new ViewHolderMore(view);
        }

        return viewHolder;

    }

    String timeToSetOld = "";

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        String text = null;
        String timeToSet = null;
        try {
            if (notification_list.get(position).getEntryDate() != null) {
                String timeFormat = notification_list.get(position).getEntryDate();
                timeFormat = timeFormat.replace("T", " ");

                sdf.setTimeZone(TimeZone.getTimeZone("GTC"));
                Date date = null;
                try {
                    date = sdf.parse(timeFormat);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                sdf.setTimeZone(TimeZone.getDefault());
                String formattedDate = sdf.format(Objects.requireNonNull(date));


                Date ddd = null;
                try {
                    ddd = sdf.parse(formattedDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                long millis = Objects.requireNonNull(ddd).getTime();
                text = TimeAgo.using(millis);
                timeToSet = text.toLowerCase();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


//        String requestId;
        if (holder.getItemViewType() == MATCHED) {
            ViewHolder viewHolderMatched = (ViewHolder) holder;
            viewHolderMatched.tv_date.setText(timeToSet);
            if (Objects.requireNonNull(timeToSet).equals(timeToSetOld)) {
                viewHolderMatched.tv_date.setVisibility(View.GONE);
            } else {
                viewHolderMatched.tv_date.setVisibility(View.VISIBLE);

            }
            timeToSetOld = Objects.requireNonNull(text).toLowerCase();

            if (notification_list.get(position).getFriendRequest() != null) {

                if (notification_list.get(position).getFriendRequest().size() > 0) {
                    if (Objects.requireNonNull(notification_list.get(position).getFriendRequest().get(0).getStatus()).toLowerCase().equals("pending")) {

                        viewHolderMatched.rl_notification.setVisibility(View.VISIBLE);

                        String name = Objects.requireNonNull(notification_list.get(position).getFriendRequest().get(0).getUserInfo()).get(0).getUsername();
                        String desc = notification_list.get(position).getNotificationMessage();

                        String sourceString = "<b>" + name + "</b> " + desc;
                        viewHolderMatched.tv_name.setText(Html.fromHtml(sourceString));

//                        requestId = notification_list.get(position).getFriendRequest().get(0).getRequestId();
                        picasso.load(notification_list.get(position).getFriendRequest().get(0).getUserInfo().get(0).getProfilePicPath())
                                .placeholder(R.drawable.profile)
                                .fit()
                                .centerCrop()
                                .into(viewHolderMatched.profile_image);

                        if (notification_list.get(position).isSelected) {
                            viewHolderMatched.rl_notification.setBackgroundColor(mContext.getResources().getColor(R.color.color_pink_dull));
                            viewHolderMatched.main_ll.setBackgroundColor(mContext.getResources().getColor(R.color.color_pink_dull));

                        } else {
                            viewHolderMatched.rl_notification.setBackgroundColor(mContext.getResources().getColor(R.color.backgroundColour));
                            viewHolderMatched.main_ll.setBackgroundColor(mContext.getResources().getColor(R.color.backgroundColour));
                        }

                        viewHolderMatched.rl_notification.setOnLongClickListener(v -> {
                            if (!notification_list.get(position).isSelected) {

                                for (int i = 0; i < notification_list.size(); i++) {
                                    if (position != i) {
                                        notification_list.get(i).setSelected(false);
                                    } else {
                                        notification_list.get(position).setSelected(true);
                                        itemClick.onItemClicks(v, position, 11, notification_list.get(position).getNotificationId(), notification_list.get(position).getUserID());
                                    }
                                }
                            } else {
                                notification_list.get(position).setSelected(false);

                            }
                            notifyDataSetChanged();
                            return true;
                        });
                    }

                }
            }


            viewHolderMatched.iv_right.setOnClickListener(v -> {

                try {
                    itemClick.onItemClicks(v, position, 24, notification_list.get(position).getFriendRequest().get(0).getRequestId());
                } catch (Exception e) {
                    e.printStackTrace();
                }


            });
            viewHolderMatched.iv_cross.setOnClickListener(v -> {
                try {

                    itemClick.onItemClicks(v, position, 25, notification_list.get(position).getFriendRequest().get(0).getRequestId());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

// FriendRequest
        } else if (holder.getItemViewType() == LIKED) {

            ViewHolderLiked viewHolderLiked = (ViewHolderLiked) holder;
            viewHolderLiked.tv_date.setText(timeToSet);
            picasso.load(notification_list.get(position).getmUserInformation().get(0).getProfilePicPath())
                    .placeholder(R.drawable.profile)
                    .into(viewHolderLiked.profile_image_3);

            if (Objects.equals(timeToSet, timeToSetOld)) {
                viewHolderLiked.tv_date.setVisibility(View.GONE);
            } else {
                viewHolderLiked.tv_date.setVisibility(View.VISIBLE);

            }
            timeToSetOld = Objects.requireNonNull(text).toLowerCase();

            String name = notification_list.get(position).getmUserInformation().get(0).getUsername();
            String desc = notification_list.get(position).getNotificationMessage();

            String sourceString = "<b>" + name + "</b> " + desc;
            viewHolderLiked.tv_name.setText(Html.fromHtml(sourceString));

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
                viewHolderLiked.rl_notification.setBackgroundColor(mContext.getResources().getColor(R.color.color_pink_dull));
            } else {
                viewHolderLiked.rl_notification.setBackgroundColor(mContext.getResources().getColor(R.color.backgroundColour));
            }

            viewHolderLiked.rl_notification.setOnLongClickListener(v -> {
                if (!notification_list.get(position).isSelected) {

                    for (int i = 0; i < notification_list.size(); i++) {
                        if (position != i) {
                            notification_list.get(i).setSelected(false);
                        } else {
                            notification_list.get(position).setSelected(true);
                            itemClick.onItemClicks(v, position, 11, notification_list.get(position).getNotificationId(), notification_list.get(position).getUserID());
                        }
                    }

                    notifyItemChanged(position);


                } else {
                    notification_list.get(position).setSelected(false);
                    notifyDataSetChanged();
                }
                return true;
            });
//// LIKED
        } else if (holder.getItemViewType() == COMMENT) {
            ViewHolderComment viewHolderComment = (ViewHolderComment) holder;
            viewHolderComment.tv_date.setText(timeToSet);
            if (Objects.equals(timeToSet, timeToSetOld)) {
                viewHolderComment.tv_date.setVisibility(View.GONE);
            } else {
                viewHolderComment.tv_date.setVisibility(View.VISIBLE);

            }
            timeToSetOld = Objects.requireNonNull(text).toLowerCase();
            picasso.load(notification_list.get(position).getmUserInformation().get(0).getProfilePicPath()).placeholder(R.drawable.profile)
                    .into(viewHolderComment.profile_image_3);
            String name = notification_list.get(position).getmUserInformation().get(0).getUsername();
            String desc = notification_list.get(position).getNotificationMessage();

            String sourceString = "<b>" + name + "</b> " + desc;
            viewHolderComment.tv_name.setText(Html.fromHtml(sourceString));

            if (notification_list.get(position).isSelected) {
                viewHolderComment.rl_notification.setBackgroundColor(mContext.getResources().getColor(R.color.color_pink_dull));
            } else {
                viewHolderComment.rl_notification.setBackgroundColor(mContext.getResources().getColor(R.color.backgroundColour));

            }

            viewHolderComment.rl_notification.setOnLongClickListener(v -> {
                if (!notification_list.get(position).isSelected) {

                    for (int i = 0; i < notification_list.size(); i++) {
                        if (position != i) {
                            notification_list.get(i).setSelected(false);
                        } else {
                            notification_list.get(position).setSelected(true);
                            itemClick.onItemClicks(v, position, 11, notification_list.get(position).getNotificationId(), notification_list.get(position).getUserID());
                        }
                    }




                } else {
                    notification_list.get(position).setSelected(false);
                }
                notifyDataSetChanged();
                return true;
            });

/// COMMENT
        } else if (holder.getItemViewType() == FRIENDREQUEST) {
            ViewHolder viewHolderMatched = (ViewHolder) holder;
            viewHolderMatched.tv_date.setText(timeToSet);

            if (Objects.equals(timeToSet, timeToSetOld)) {
                viewHolderMatched.tv_date.setVisibility(View.GONE);
            } else {
                viewHolderMatched.tv_date.setVisibility(View.VISIBLE);

            }
            timeToSetOld = Objects.requireNonNull(text).toLowerCase();

            if (notification_list.get(position).getFriendRequest() != null) {

                if (notification_list.get(position).getFriendRequest().size() > 0) {
                    if (notification_list.get(position).getFriendRequest().get(0).getStatus().toLowerCase().equals("pending")) {

                        viewHolderMatched.rl_notification.setVisibility(View.VISIBLE);

                        String name = notification_list.get(position).getFriendRequest().get(0).getUserInfo().get(0).getUsername();
                        String desc = notification_list.get(position).getNotificationMessage();

                        String sourceString = "<b>" + name + "</b> " + desc;
                        viewHolderMatched.tv_name.setText(Html.fromHtml(sourceString));
//                        requestId = notification_list.get(position).getFriendRequest().get(0).getRequestId();
                        picasso.load(notification_list.get(position).getFriendRequest().get(0).getUserInfo().get(0).getProfilePicPath())
                                .placeholder(R.drawable.profile)
                                .fit()
                                .centerCrop()
                                .into(viewHolderMatched.profile_image);

                        if (notification_list.get(position).isSelected) {
                            viewHolderMatched.rl_notification.setBackgroundColor(mContext.getResources().getColor(R.color.color_pink_dull));
                            viewHolderMatched.main_ll.setBackgroundColor(mContext.getResources().getColor(R.color.color_pink_dull));
                        } else {
                            viewHolderMatched.rl_notification.setBackgroundColor(mContext.getResources().getColor(R.color.backgroundColour));
                            viewHolderMatched.main_ll.setBackgroundColor(mContext.getResources().getColor(R.color.backgroundColour));

                        }


                        viewHolderMatched.rl_notification.setOnLongClickListener(v -> {
//                            Log.e("relativeLayout", "relativeLayout" + position);
                            if (!notification_list.get(position).isSelected) {

                                for (int i = 0; i < notification_list.size(); i++) {
                                    if (position != i) {
                                        notification_list.get(i).setSelected(false);
                                    } else {
                                        notification_list.get(position).setSelected(true);
                                        itemClick.onItemClicks(v, position, 11, notification_list.get(position).getNotificationId(), notification_list.get(position).getUserID());
                                    }
                                }
                            } else {
                                notification_list.get(position).setSelected(false);
                            }
                            notifyDataSetChanged();
                            return true;
                        });
                    }


                }
            }


            viewHolderMatched.iv_right.setOnClickListener(v -> {

                try {
                    itemClick.onItemClicks(v, position, 20, notification_list.get(position).getFriendRequest().get(0).getRequestId());

                } catch (Exception e) {
                    e.printStackTrace();
                }


            });
            viewHolderMatched.iv_cross.setOnClickListener(v -> {
                try {
                    itemClick.onItemClicks(v, position, 21, notification_list.get(position).getFriendRequest().get(0).getRequestId());
                } catch (Exception e) {
                    e.printStackTrace();
                }


                //  removeAt(getAdapterPosition());
            });

        } else if (holder.getItemViewType() == MATCH_RQUEST) {
            ViewHolderMatchRequest viewHolderMatchRequest = (ViewHolderMatchRequest) holder;
            viewHolderMatchRequest.tv_date.setText(timeToSet);

            if (timeToSet.equals(timeToSetOld)) {
                viewHolderMatchRequest.tv_date.setVisibility(View.GONE);
            } else {
                viewHolderMatchRequest.tv_date.setVisibility(View.VISIBLE);

            }
            timeToSetOld = text.toLowerCase();

            picasso.load(notification_list.get(position).getmUserInformation().get(0).getProfilePicPath()).placeholder(R.drawable.profile)
                    .into(viewHolderMatchRequest.profile_image);
//            viewHolderMatchRequest.tv_name.setText(notification_list.get(position).getmUserInformation().get(0).getUsername());
//            viewHolderMatchRequest.tv_desc.setText(notification_list.get(position).getNotificationMessage());

            String name = notification_list.get(position).getmUserInformation().get(0).getUsername();
            String desc = notification_list.get(position).getNotificationMessage();
            String sourceString = "<b>" + name + "</b> " + desc;
            viewHolderMatchRequest.tv_name.setText(Html.fromHtml(sourceString));


            if (notification_list.get(position).isSelected) {
                viewHolderMatchRequest.rl_notification.setBackgroundColor(mContext.getResources().getColor(R.color.color_pink_dull));
                viewHolderMatchRequest.main_ll.setBackgroundColor(mContext.getResources().getColor(R.color.color_pink_dull));
            } else {
                viewHolderMatchRequest.rl_notification.setBackgroundColor(mContext.getResources().getColor(R.color.backgroundColour));
                viewHolderMatchRequest.main_ll.setBackgroundColor(mContext.getResources().getColor(R.color.backgroundColour));

            }

            viewHolderMatchRequest.rl_notification.setOnLongClickListener(v -> {
                if (!notification_list.get(position).isSelected) {

                    for (int i = 0; i < notification_list.size(); i++) {
                        if (position != i) {
                            notification_list.get(i).setSelected(false);
                        } else {
                            notification_list.get(position).setSelected(true);
                            itemClick.onItemClicks(v, position, 11, notification_list.get(position).getNotificationId(), notification_list.get(position).getUserID());
                        }
                    }
                } else {
                    notification_list.get(position).setSelected(false);
                }
                notifyDataSetChanged();
                return true;
            });
            viewHolderMatchRequest.iv_right.setVisibility(View.GONE);
            viewHolderMatchRequest.iv_cross.setVisibility(View.GONE);



        } else if (holder.getItemViewType() == DATE_PARTNER) {

            //need to be change from pawan's end

            ViewHolderMatchRequest viewHolderMatchRequest = (ViewHolderMatchRequest) holder;
            viewHolderMatchRequest.tv_date.setText(timeToSet);

            if (timeToSet.equals(timeToSetOld)) {
                viewHolderMatchRequest.tv_date.setVisibility(View.GONE);
            } else {
                viewHolderMatchRequest.tv_date.setVisibility(View.VISIBLE);

            }
            timeToSetOld = text.toLowerCase();

            picasso.load(notification_list.get(position).getmUserInformation().get(0).getProfilePicPath()).placeholder(R.drawable.profile)
                    .into(viewHolderMatchRequest.profile_image);

            String name = notification_list.get(position).getmUserInformation().get(0).getUsername();
            String desc = notification_list.get(position).getNotificationMessage();
            String sourceString = "<b>" + name + "</b> " + desc;
            viewHolderMatchRequest.tv_name.setText(Html.fromHtml(sourceString));


            if (notification_list.get(position).isSelected) {
                viewHolderMatchRequest.rl_notification.setBackgroundColor(mContext.getResources().getColor(R.color.color_pink_dull));
                viewHolderMatchRequest.main_ll.setBackgroundColor(mContext.getResources().getColor(R.color.color_pink_dull));
            } else {
                viewHolderMatchRequest.rl_notification.setBackgroundColor(mContext.getResources().getColor(R.color.backgroundColour));
                viewHolderMatchRequest.main_ll.setBackgroundColor(mContext.getResources().getColor(R.color.backgroundColour));

            }

            viewHolderMatchRequest.rl_notification.setOnLongClickListener(v -> {
                if (!notification_list.get(position).isSelected) {

                    for (int i = 0; i < notification_list.size(); i++) {
                        if (position != i) {
                            notification_list.get(i).setSelected(false);
                        } else {
                            notification_list.get(position).setSelected(true);
                            itemClick.onItemClicks(v, position, 11, notification_list.get(position).getNotificationId(), notification_list.get(position).getUserID());
                        }
                    }


                } else {
                    notification_list.get(position).setSelected(false);
                }
                notifyDataSetChanged();
                return true;
            });
            viewHolderMatchRequest.iv_right.setVisibility(View.GONE);
            viewHolderMatchRequest.iv_cross.setVisibility(View.GONE);

            viewHolderMatchRequest.icons.setVisibility(View.VISIBLE);

            viewHolderMatchRequest.rl_notification.setOnClickListener(v -> {
                if (notification_list.get(position).getNotificationText().equals("Date Request Invite")) {
                    DateBaseActivity.fromNotification = true;
                    mContext.startActivity(new Intent(mContext, DateBaseActivity.class));
                }
            });



        } else if (holder.getItemViewType() == FRIENDACCEPTED) {
            ViewHolderLiked viewHolderLiked = (ViewHolderLiked) holder;
            viewHolderLiked.tv_date.setText(timeToSet);


            if (timeToSet.equals(timeToSetOld)) {
                viewHolderLiked.tv_date.setVisibility(View.GONE);
            } else {
                viewHolderLiked.tv_date.setVisibility(View.VISIBLE);

            }
            timeToSetOld = text.toLowerCase();

            String name = notification_list.get(position).getmUserInformation().get(0).getUsername();
            String desc = notification_list.get(position).getNotificationMessage();
            String sourceString = "<b>" + name + "</b> " + desc;
            viewHolderLiked.tv_name.setText(Html.fromHtml(sourceString));

            picasso.load(notification_list.get(position).getmUserInformation().get(0).getProfilePicPath()).placeholder(R.drawable.profile)
                    .into(viewHolderLiked.profile_image_3);


            if (notification_list.get(position).isSelected) {
                viewHolderLiked.rl_notification.setBackgroundColor(mContext.getResources().getColor(R.color.color_pink_dull));
                viewHolderLiked.main_ll.setBackgroundColor(mContext.getResources().getColor(R.color.color_pink_dull));
            } else {
                viewHolderLiked.rl_notification.setBackgroundColor(mContext.getResources().getColor(R.color.backgroundColour));
                viewHolderLiked.main_ll.setBackgroundColor(mContext.getResources().getColor(R.color.backgroundColour));

            }
            viewHolderLiked.rl_notification.setOnLongClickListener(v -> {
                if (!notification_list.get(position).isSelected) {

                    for (int i = 0; i < notification_list.size(); i++) {
                        if (position != i) {
                            notification_list.get(i).setSelected(false);
                        } else {
                            notification_list.get(position).setSelected(true);
                            itemClick.onItemClicks(v, position, 11, notification_list.get(position).getNotificationId(), notification_list.get(position).getUserID());
                        }
                    }

                } else {
                    notification_list.get(position).setSelected(false);
                }
                notifyDataSetChanged();
                return true;
            });

        } else if (holder.getItemViewType() == RELATIONACCEPTED) {
            ViewHolderLiked viewHolderLiked = (ViewHolderLiked) holder;
            viewHolderLiked.tv_date.setText(timeToSet);

            if (timeToSet.equals(timeToSetOld)) {
                viewHolderLiked.tv_date.setVisibility(View.GONE);
            } else {
                viewHolderLiked.tv_date.setVisibility(View.VISIBLE);

            }
            timeToSetOld = text.toLowerCase();

            String name = notification_list.get(position).getmUserInformation().get(0).getUsername();
            String desc = notification_list.get(position).getNotificationMessage();
            String sourceString = "<b>" + name + "</b> " + desc;
            viewHolderLiked.tv_name.setText(Html.fromHtml(sourceString));

            picasso.load(notification_list.get(position).getmUserInformation().get(0).getProfilePicPath()).placeholder(R.drawable.profile)
                    .into(viewHolderLiked.profile_image_3);


            if (notification_list.get(position).isSelected) {
                viewHolderLiked.rl_notification.setBackgroundColor(mContext.getResources().getColor(R.color.color_pink_dull));
                viewHolderLiked.main_ll.setBackgroundColor(mContext.getResources().getColor(R.color.color_pink_dull));
            } else {
                viewHolderLiked.rl_notification.setBackgroundColor(mContext.getResources().getColor(R.color.backgroundColour));
                viewHolderLiked.main_ll.setBackgroundColor(mContext.getResources().getColor(R.color.backgroundColour));

            }

            viewHolderLiked.rl_notification.setOnLongClickListener(v -> {
                if (!notification_list.get(position).isSelected) {

                    for (int i = 0; i < notification_list.size(); i++) {
                        if (position != i) {
                            notification_list.get(i).setSelected(false);
                        } else {
                            notification_list.get(position).setSelected(true);
                            itemClick.onItemClicks(v, position, 11, notification_list.get(position).getNotificationId(), notification_list.get(position).getUserID());
                        }
                    }


                } else {
                    notification_list.get(position).setSelected(false);
                }
                notifyDataSetChanged();
                return true;
            });
        } else if (holder.getItemViewType() == RELATION_REQUEST) {
            ViewHolderRelationRequest viewHolderRelationRequest = (ViewHolderRelationRequest) holder;
            viewHolderRelationRequest.tv_date.setText(timeToSet);

            if (timeToSet.equals(timeToSetOld)) {
                viewHolderRelationRequest.tv_date.setVisibility(View.GONE);
            } else {
                viewHolderRelationRequest.tv_date.setVisibility(View.VISIBLE);

            }
            timeToSetOld = text.toLowerCase();

            relationRequestId = notification_list.get(position).getmRelationRequest().get(0).getmRequestId();

            String name = notification_list.get(position).getmUserInformation().get(0).getUsername();
            String desc = notification_list.get(position).getNotificationMessage();
            String sourceString = "<b>" + name + "</b> " + desc;
            viewHolderRelationRequest.tv_name.setText(Html.fromHtml(sourceString));
            picasso.load(notification_list.get(position).getmUserInformation().get(0).getProfilePicPath()).placeholder(R.drawable.profile)
                    .into(viewHolderRelationRequest.profile_image);

            viewHolderRelationRequest.iv_right.setOnClickListener(v -> {
                try {
                    itemClick.onItemClicks(v, position, 30, relationRequestId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            viewHolderRelationRequest.iv_cross.setOnClickListener(v -> {
                try {
                    itemClick.onItemClicks(v, position, 31, relationRequestId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } else if (holder.getItemViewType() == CHAT_REQUEST) {
            ViewHolderChatRequest viewHolderChatRequest = (ViewHolderChatRequest) holder;
            viewHolderChatRequest.tv_date.setText(timeToSet);

            if (timeToSet.equals(timeToSetOld)) {
                viewHolderChatRequest.tv_date.setVisibility(View.GONE);
            } else {
                viewHolderChatRequest.tv_date.setVisibility(View.VISIBLE);

            }
            timeToSetOld = text.toLowerCase();


            if (notification_list.get(position).getmChatRequest() != null) {

                if (notification_list.get(position).getmChatRequest().size() > 0) {
                    if (notification_list.get(position).getmChatRequest().get(0).getActiveStatus().toLowerCase().equals("pending")) {

                        viewHolderChatRequest.rl_notification.setVisibility(View.VISIBLE);

                        String name = notification_list.get(position).getmChatRequest().get(0).getUserInfo().get(0).getUsername();
                        String desc = notification_list.get(position).getNotificationMessage();

                        String sourceString = "<b>" + name + "</b> " + desc;
                        viewHolderChatRequest.tv_name.setText(Html.fromHtml(sourceString));
//                        requestId = notification_list.get(position).getmChatRequest().get(0).getRequestId();
                        picasso.load(notification_list.get(position).getmChatRequest().get(0).getUserInfo().get(0).getProfilePicPath())
                                .placeholder(R.drawable.profile)
                                .fit()
                                .centerCrop()
                                .into(viewHolderChatRequest.profile_image);

                        if (notification_list.get(position).isSelected) {
                            viewHolderChatRequest.rl_notification.setBackgroundColor(mContext.getResources().getColor(R.color.color_pink_dull));
                            viewHolderChatRequest.main_ll.setBackgroundColor(mContext.getResources().getColor(R.color.color_pink_dull));
                        } else {
                            viewHolderChatRequest.rl_notification.setBackgroundColor(mContext.getResources().getColor(R.color.backgroundColour));
                            viewHolderChatRequest.main_ll.setBackgroundColor(mContext.getResources().getColor(R.color.backgroundColour));

                        }


                        viewHolderChatRequest.rl_notification.setOnLongClickListener(v -> {
                            if (!notification_list.get(position).isSelected) {

                                for (int i = 0; i < notification_list.size(); i++) {
                                    if (position != i) {
                                        notification_list.get(i).setSelected(false);
                                    } else {
                                        notification_list.get(position).setSelected(true);
                                        itemClick.onItemClicks(v, position, 11, notification_list.get(position).getNotificationId(), notification_list.get(position).getUserID());
                                    }
                                }
                            } else {
                                notification_list.get(position).setSelected(false);
                            }
                            notifyDataSetChanged();
                            return true;
                        });
                    }


                }
                viewHolderChatRequest.iv_right.setOnClickListener(v -> {

                    try {

                        itemClick.onItemClicks(v, position, 32, notification_list.get(position).getmChatRequest().get(0).getRequestId());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                });
                viewHolderChatRequest.iv_cross.setOnClickListener(v -> {
                    try {

                        itemClick.onItemClicks(v, position, 33, notification_list.get(position).getmChatRequest().get(0).getRequestId());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //  removeAt(getAdapterPosition());
                });

            }


        } else if (holder.getItemViewType() == CHATACCEPTED) {
            ViewHolderLiked viewHolderLiked = (ViewHolderLiked) holder;
            viewHolderLiked.tv_date.setText(timeToSet);

            if (timeToSet.equals(timeToSetOld)) {
                viewHolderLiked.tv_date.setVisibility(View.GONE);
            } else {
                viewHolderLiked.tv_date.setVisibility(View.VISIBLE);

            }
            timeToSetOld = text.toLowerCase();


            String name = notification_list.get(position).getmUserInformation().get(0).getUsername();
            String desc = notification_list.get(position).getNotificationMessage();
            String sourceString = "<b>" + name + "</b> " + desc;
            viewHolderLiked.tv_name.setText(Html.fromHtml(sourceString));

            picasso.load(notification_list.get(position).getmUserInformation().get(0).getProfilePicPath()).placeholder(R.drawable.profile)
                    .into(viewHolderLiked.profile_image_3);


            if (notification_list.get(position).isSelected) {
                viewHolderLiked.rl_notification.setBackgroundColor(mContext.getResources().getColor(R.color.color_pink_dull));
                viewHolderLiked.main_ll.setBackgroundColor(mContext.getResources().getColor(R.color.color_pink_dull));
            } else {
                viewHolderLiked.rl_notification.setBackgroundColor(mContext.getResources().getColor(R.color.backgroundColour));
                viewHolderLiked.main_ll.setBackgroundColor(mContext.getResources().getColor(R.color.backgroundColour));

            }
            viewHolderLiked.rl_notification.setOnLongClickListener(v -> {
                if (!notification_list.get(position).isSelected) {

                    for (int i = 0; i < notification_list.size(); i++) {
                        if (position != i) {
                            notification_list.get(i).setSelected(false);
                        } else {
                            notification_list.get(position).setSelected(true);
                            itemClick.onItemClicks(v, position, 11, notification_list.get(position).getNotificationId(), notification_list.get(position).getUserID());
                        }
                    }


                } else {
                    notification_list.get(position).setSelected(false);
                }
                notifyDataSetChanged();
                return true;
            });

        } else if (holder.getItemViewType() == POSTTAGGED) {
            ViewHolderTagged viewHolderTagged = (ViewHolderTagged) holder;
            viewHolderTagged.tv_date.setText(timeToSet);


            if (timeToSet.equals(timeToSetOld)) {
                viewHolderTagged.tv_date.setVisibility(View.GONE);
            } else {
                viewHolderTagged.tv_date.setVisibility(View.VISIBLE);

            }
            timeToSetOld = Objects.requireNonNull(text).toLowerCase();

            picasso.load(notification_list.get(position).getmUserInformation().get(0).getProfilePicPath())
                    .placeholder(R.drawable.profile)
                    .into(viewHolderTagged.profile_image_3);


            String name = notification_list.get(position).getmUserInformation().get(0).getUsername();
            String desc = notification_list.get(position).getNotificationMessage();

            String sourceString = "<b>" + name + "</b> " + desc;
            viewHolderTagged.tv_name.setText(Html.fromHtml(sourceString));

            if (notification_list.get(position).getmPostInfo().get(0).getMedia().get(0).getMediaType().equals("Image")) {
                picasso.load(notification_list.get(position).getmPostInfo().get(0).getMedia().get(0).getMediaFullPath())
                        .fit()
                        .centerCrop()
                        .into(viewHolderTagged.iv_icon_3);
            } else if (notification_list.get(position).getmPostInfo().get(0).getMedia().get(0).getMediaType().equals("Video")) {
                picasso.load(notification_list.get(position).getmPostInfo().get(0).getMedia().get(0).getMediaThumbName())
                        .fit()
                        .centerCrop()
                        .into(viewHolderTagged.iv_icon_3);
            }

            if (notification_list.get(position).isSelected) {
                viewHolderTagged.rl_notification.setBackgroundColor(mContext.getResources().getColor(R.color.color_pink_dull));
            } else {
                viewHolderTagged.rl_notification.setBackgroundColor(mContext.getResources().getColor(R.color.backgroundColour));
            }

            viewHolderTagged.rl_notification.setOnLongClickListener(v -> {
                if (!notification_list.get(position).isSelected) {

                    for (int i = 0; i < notification_list.size(); i++) {
                        if (position != i) {
                            notification_list.get(i).setSelected(false);
                        } else {
                            notification_list.get(position).setSelected(true);
                            itemClick.onItemClicks(v, position, 11, notification_list.get(position).getNotificationId(), notification_list.get(position).getUserID());
                        }
                    }

                    notifyItemChanged(position);


                } else {
                    notification_list.get(position).setSelected(false);
                    notifyDataSetChanged();
                }
                return true;
            });
        } else if (holder.getItemViewType() == VIEWMORE) {
            ViewHolderMore viewHolderMore = (ViewHolderMore) holder;
            if (notification_list.size() > 8) {
                if (showLoadmore) {
                    viewHolderMore.txt_view_more.setVisibility(View.VISIBLE);
                } else {
                    viewHolderMore.txt_view_more.setVisibility(View.GONE);
                }
            } else {
                viewHolderMore.txt_view_more.setVisibility(View.GONE);
            }
            viewHolderMore.txt_view_more.setOnClickListener(view -> fragNotification.loadMore());


        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView profile_image;
        private final ImageView iv_right;
        private final ImageView iv_cross;
        private final TextView tv_name;
        //        private TextView tv_desc;
        private final LinearLayout main_ll;
        private final TextView tv_date;

        private final RelativeLayout rl_notification;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            main_ll = itemView.findViewById(R.id.main_ll);
            profile_image = itemView.findViewById(R.id.profile_image_1);
            rl_notification = itemView.findViewById(R.id.rl_notification);
            ImageView icons = itemView.findViewById(R.id.iv_icon_1);
            tv_name = itemView.findViewById(R.id.tv_name_noti);
            iv_right = itemView.findViewById(R.id.iv_right);
            iv_cross = itemView.findViewById(R.id.iv_cross);
            tv_date = itemView.findViewById(R.id.tv_date);
            icons.setVisibility(View.GONE);

        }
    }


    public static class ViewHolderChatRequest extends RecyclerView.ViewHolder {
        private final ImageView profile_image;
        private final ImageView iv_right;
        private final ImageView iv_cross;
        private final TextView tv_name;
        //        private TextView tv_desc;
        private final LinearLayout main_ll;
        private final TextView tv_date;
        //        private RelativeLayout rl_request;
        private final RelativeLayout rl_notification;

        public ViewHolderChatRequest(@NonNull View itemView) {
            super(itemView);
            main_ll = itemView.findViewById(R.id.main_ll);
            profile_image = itemView.findViewById(R.id.profile_image_1);
            rl_notification = itemView.findViewById(R.id.rl_notification);
            ImageView icons = itemView.findViewById(R.id.iv_icon_1);
            tv_name = itemView.findViewById(R.id.tv_name_noti);
            iv_right = itemView.findViewById(R.id.iv_right);
            iv_cross = itemView.findViewById(R.id.iv_cross);
            icons.setVisibility(View.GONE);
            tv_date = itemView.findViewById(R.id.tv_date);

        }
    }

    public static class ViewHolderLiked extends RecyclerView.ViewHolder {
        private final ImageView profile_image_3;
        private final ImageView iv_icon_3;
        private final TextView tv_name;
        //        private TextView tv_desc;
        private final LinearLayout main_ll;
        private final RelativeLayout rl_notification;
        private final TextView tv_date;

        public ViewHolderLiked(View view) {
            super(view);
            profile_image_3 = itemView.findViewById(R.id.profile_image_3);
            iv_icon_3 = itemView.findViewById(R.id.iv_icon_3);
            rl_notification = itemView.findViewById(R.id.rl_header);

            tv_name = itemView.findViewById(R.id.tv_name_noti);
//            tv_desc = itemView.findViewById(R.id.tv_desc_noti);
            main_ll = itemView.findViewById(R.id.main_ll);
            tv_date = itemView.findViewById(R.id.tv_date);

        }
    }

    public static class ViewHolderTagged extends RecyclerView.ViewHolder {
        private final ImageView profile_image_3;
        private final ImageView iv_icon_3;
        private final TextView tv_name;
        private final TextView tv_date;
        private final RelativeLayout rl_notification;

        public ViewHolderTagged(View view) {
            super(view);
            profile_image_3 = itemView.findViewById(R.id.profile_image_3);
            iv_icon_3 = itemView.findViewById(R.id.iv_icon_3);
            rl_notification = itemView.findViewById(R.id.rl_header);

            tv_name = itemView.findViewById(R.id.tv_name_noti);
            tv_date = itemView.findViewById(R.id.tv_date);

        }
    }

    public static class ViewHolderComment extends RecyclerView.ViewHolder {
        private final ImageView profile_image_3;
        //        private ImageView iv_icon_3;
        private final TextView tv_name;
        //        private TextView tv_desc;
        private final RelativeLayout rl_notification;
        private final TextView tv_date;

        public ViewHolderComment(View view) {
            super(view);
            profile_image_3 = itemView.findViewById(R.id.profile_image_3);
//            iv_icon_3 = itemView.findViewById(R.id.iv_icon_3);
            rl_notification = itemView.findViewById(R.id.rl_header);

            tv_name = itemView.findViewById(R.id.tv_name_noti);
//            tv_desc = itemView.findViewById(R.id.tv_desc_noti);

            tv_date = itemView.findViewById(R.id.tv_date);
        }
    }

    public static class ViewHolderMatchRequest extends RecyclerView.ViewHolder {

        private final ImageView profile_image;
        private final ImageView icons;
        private final ImageView iv_right;
        private final ImageView iv_cross;
        private final TextView tv_name;
        private final RelativeLayout rl_notification;
        private final LinearLayout main_ll;
        private final TextView tv_date;

        public ViewHolderMatchRequest(@NonNull View itemView) {
            super(itemView);
            main_ll = itemView.findViewById(R.id.main_ll);
            profile_image = itemView.findViewById(R.id.profile_image_1);
            rl_notification = itemView.findViewById(R.id.rl_notification);
            icons = itemView.findViewById(R.id.iv_icon_1);
            tv_name = itemView.findViewById(R.id.tv_name_noti);
            iv_right = itemView.findViewById(R.id.iv_right);
            iv_cross = itemView.findViewById(R.id.iv_cross);
            //  icons.setVisibility(View.GONE);
            icons.setImageResource(R.drawable.playdate_pink);
            tv_date = itemView.findViewById(R.id.tv_date);
        }

    }


    public static class ViewHolderRelationRequest extends RecyclerView.ViewHolder {
        private final ImageView profile_image;
        private final ImageView iv_right;
        private final ImageView iv_cross;
        private final TextView tv_name;
        private final TextView tv_date;

        public ViewHolderRelationRequest(@NonNull View itemView) {
            super(itemView);
            profile_image = itemView.findViewById(R.id.profile_image_1);
//            rl_notification = itemView.findViewById(R.id.rl_notification);
            ImageView icons = itemView.findViewById(R.id.iv_icon_1);
            tv_name = itemView.findViewById(R.id.tv_name_noti);
            iv_right = itemView.findViewById(R.id.iv_right);
            iv_cross = itemView.findViewById(R.id.iv_cross);
            icons.setVisibility(View.GONE);
            tv_date = itemView.findViewById(R.id.tv_date);

        }

    }


}