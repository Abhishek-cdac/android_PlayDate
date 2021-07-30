package com.playdate.app.ui.chat;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.playdate.app.R;
import com.playdate.app.model.chat_models.ChatList;
import com.playdate.app.ui.chat.request.Onclick;
import com.playdate.app.ui.chat.request.RequestChatFragment;
import com.playdate.app.util.session.SessionPref;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

public class ChattingAdapter extends RecyclerView.Adapter<ChattingAdapter.MyViewHolder> {

    private ArrayList<ChatList> lst_msgs;
    private Context mContext;
    private Onclick itemClick;
    private final RequestChatFragment frag;
    private LandingBottomSheet bottomSheet;
    private final Picasso picasso;
    private String todaysDate;
    private final String MyID;
    private final SimpleDateFormat sdf;
    private final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
    public ChattingAdapter(ArrayList<ChatList> inboxList, Onclick itemClick, RequestChatFragment frag) {
        this.lst_msgs = inboxList;
        this.itemClick = itemClick;
        this.frag = frag;
        picasso = Picasso.get();


        SessionPref pref = SessionPref.getInstance(frag.getActivity());
        MyID = pref.getStringVal(SessionPref.LoginUserID);
        try {
            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            todaysDate = df.format(c);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        format2 = new SimpleDateFormat("hh:mm aa");
//        format3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        format4 = new SimpleDateFormat("dd/MM/yyyy");
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        //    private final SimpleDateFormat format1;
        //    private final SimpleDateFormat format2;
        //    private final SimpleDateFormat format3;
        //    private final SimpleDateFormat format4;
        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        df1.setTimeZone(TimeZone.getTimeZone("GTC"));
    }


    public void Refresh() {
        frag.onResume();
    }

    public void deleteChat(int index) {
        if (null != lst_msgs) {
            lst_msgs.remove(index);
            notifyDataSetChanged();
            bottomSheet.dismiss();
        }
    }


    public void dismissSheet() {
        bottomSheet.dismiss();
        notifyDataSetChanged();
    }

    public void updateList(int index) {
        lst_msgs.remove(index);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ChattingAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.inbox_list_row, parent, false);
        mContext = parent.getContext();
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        try {
            holder.user_name.setText(lst_msgs.get(position).getLstFrom().get(0).getUsername());
            if (lst_msgs.get(position).getUnreadChat() == 0) {
                holder.txt_count.setVisibility(View.GONE);
                holder.img_more.setVisibility(View.VISIBLE);
            } else {
                String text = "" + lst_msgs.get(position).getUnreadChat();
                holder.txt_count.setText(text);
                holder.txt_count.setVisibility(View.VISIBLE);
                holder.img_more.setVisibility(View.GONE);
            }


            picasso.load(lst_msgs.get(position).getLstFrom().get(0).getProfilePicPath()).placeholder(R.drawable.profile).into(holder.profile_image);
            if (lst_msgs.get(position).getLstFrom().get(0).getOnlineStatus().toLowerCase().equals("online")) {
                holder.img_active.setVisibility(View.VISIBLE);
            } else {
                holder.img_active.setVisibility(View.GONE);
            }


            try {

                String msgType = lst_msgs.get(position).getLastMsg().get(0).getMessageType().toLowerCase();
                if (msgType.equals("text")) {
                    holder.msg.setText(lst_msgs.get(position).getLastMsg().get(0).getMessage());
                } else if (msgType.equals("emoji")) {
                    int p = Integer.parseInt(lst_msgs.get(position).getLastMsg().get(0).getMessage());
                    holder.msg.setText(new String(Character.toChars(p)));

                    holder.msg.setBackground(null);
                    holder.msg.setBackgroundColor(Color.WHITE);

                } else {
                    boolean isME = lst_msgs.get(position).getLastMsg().get(0).getLstUser().get(0).getUserId().equals(MyID);

                    String text;
                    if (isME) {
                        text = "You sent ";
                    } else {
                        text = "You received ";
                    }
                    String mediaType = lst_msgs.get(position).getLastMsg().get(0).getLstMedia().get(0).getMediaType();
                    switch (mediaType.toLowerCase()) {
                        case "video":
                            holder.msg.setText((text + "a video"));
                            break;
                        case "image":
                            holder.msg.setText((text + "an image"));
                            break;
                        case "audio":
                            holder.msg.setText((text + "an audio"));

                            break;
                        case "location":
                            holder.msg.setText((text + "a location"));
                            break;
                    }

                }

            } catch (Exception e) {
                e.printStackTrace();

            }

            String timeFormat = lst_msgs.get(position).getLastMsg().get(0).getEntryDate();

            timeFormat = timeFormat.replace("T", " ");

            df.setTimeZone(TimeZone.getTimeZone("GTC"));
            Date date = null;
            try {
                date = df.parse(timeFormat);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            df.setTimeZone(TimeZone.getDefault());
            String formattedDate = df.format(Objects.requireNonNull(date));

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date ddd = null;
            try {
                ddd = sdf.parse(formattedDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            long millis = Objects.requireNonNull(ddd).getTime();
            String text = TimeAgo.using(millis);
            holder.txt_time.setText(text.toLowerCase());


//            try {
//                String timeFormat = lst_msgs.get(position).getLastMsg().get(0).getEntryDate();
//                timeFormat = timeFormat.replace("T", " ");
//
//
//                Date date = null;
//                try {
//                    date = df.parse(timeFormat);
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//                df.setTimeZone(TimeZone.getDefault());
//                String formattedDate = df.format(date);
//
////                if (formattedDate.contains(todaysDate)) {
////
////                    try {
////                        date = format1.parse(formattedDate);
////                    } catch (ParseException e) {
////                        e.printStackTrace();
////                    }
////                    holder.txt_time.setText(format2.format(date));
////
////                } else if (findDayDiff(formattedDate) == 1) {
////                    holder.txt_time.setText("Yesterday");
////                } else {
////                    try {
////                        date = format3.parse(formattedDate);
////                    } catch (ParseException e) {
////                        e.printStackTrace();
////                    }
////                    holder.txt_time.setText(format4.format(date));
////                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

            holder.main_ll.setOnClickListener(v -> {

                frag.onClickEvent(lst_msgs.get(position));
            });


            holder.main_ll.setOnLongClickListener(v -> {
                String chatId = lst_msgs.get(position).getChatId();
//                Log.d("DAte", "onBindViewHolder: " + lst_msgs.get(position).getLastMsg().get(0).getEntryDate());
                showBottomSheet(position, lst_msgs.get(position).getLstFrom().get(0).getUserId(), chatId);
                notifyItemChanged(position);
                return false;
            });


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

//    private int findDayDiff(String formattedDate) {
//
//        try {
//            Date date = sdf.parse(formattedDate);
//            Date date1 = sdf.parse(todaysDate);
//            if (date == null || date1 == null)
//                return 0;
//
//            return ((int) ((date.getTime() - date1.getTime()) / (1000 * 60 * 60 * 24))) * -1;
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        return 0;
//    }


    @Override
    public int getItemCount() {
        if (null == lst_msgs) {
            return 0;
        }
        return lst_msgs.size();
    }

    public void filterList(ArrayList<ChatList> filteredList) {
        lst_msgs = filteredList;
        notifyDataSetChanged();

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView user_name, msg, txt_count, txt_time;
        public ImageView profile_image, img_more, img_active;
        public RelativeLayout main_rl;
        public LinearLayout ll_chat_details;
        public LinearLayout main_ll;

        public MyViewHolder(View itemView) {
            super(itemView);
            txt_time = itemView.findViewById(R.id.txt_time);
            user_name = itemView.findViewById(R.id.user_name);
            txt_count = itemView.findViewById(R.id.txt_count);
            msg = itemView.findViewById(R.id.txt_msg);
            main_rl = itemView.findViewById(R.id.main_rl);
            img_more = itemView.findViewById(R.id.img_more);
            profile_image = itemView.findViewById(R.id.profile_image);
            ll_chat_details = itemView.findViewById(R.id.ll_chat_details);
            img_active = itemView.findViewById(R.id.img_active);
            main_ll = itemView.findViewById(R.id.main_ll);


        }
    }


    private void showBottomSheet(int selectedToDelete, String toUserId, String chatId) {
        FragmentManager fragmentManager = ((AppCompatActivity) mContext).getSupportFragmentManager();
        bottomSheet = new LandingBottomSheet(this, selectedToDelete, "landing", toUserId, chatId);
        bottomSheet.show(fragmentManager, "ModalBottomSheet");
    }

}
