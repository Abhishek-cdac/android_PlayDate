package com.playdate.app.ui.chat;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.model.chat_models.ChatList;
import com.playdate.app.ui.chat.request.FragInbox;
import com.playdate.app.ui.chat.request.Onclick;
import com.playdate.app.util.session.SessionPref;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class ChattingAdapter extends RecyclerView.Adapter<ChattingAdapter.MyViewHolder> {

    ArrayList<ChatList> lst_msgs;
    Context mcontext;
    Onclick itemClick;
    private FragInbox frag;
    //    int selectedIndex = -1;
    int selectedToDelete = -1;

    //    Bundle bundle = new Bundle();
    String name, image = null;

    LandingBottomSheet bottomSheet;
    Picasso picasso;
    SessionPref pref;
    String todaysDate;

    public ChattingAdapter(ArrayList<ChatList> inboxList, Onclick itemClick, FragInbox frag) {
        this.lst_msgs = inboxList;
        this.itemClick = itemClick;
        this.frag = frag;
        picasso = Picasso.get();
        pref = SessionPref.getInstance(frag.getActivity());
        try {
            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            todaysDate = df.format(c);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ChattingAdapter(String name, String image) {
        this.name = name;
        this.image = image;
        addtoList(name, image);
    }

    public ChattingAdapter() {

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

    @NonNull
    @Override
    public ChattingAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.inbox_list_row, parent, false);
        mcontext = parent.getContext();
        return new ChattingAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        try {
            holder.user_name.setText(lst_msgs.get(position).getLstFrom().get(0).getUsername());
            if (lst_msgs.get(position).getUnreadChat() == 0) {
                holder.txt_count.setVisibility(View.GONE);
            } else {
                holder.txt_count.setText("" + lst_msgs.get(position).getUnreadChat());
                holder.txt_count.setVisibility(View.VISIBLE);
            }


            picasso.load(lst_msgs.get(position).getLstFrom().get(0).getProfilePicPath()).placeholder(R.drawable.profile).into(holder.profile_image);
            if (lst_msgs.get(position).getLstFrom().get(0).getOnlineStatus().toLowerCase().equals("online")) {
                holder.img_active.setVisibility(View.VISIBLE);
            } else {
                holder.img_active.setVisibility(View.GONE);
            }


            try {
                String msgTemp = lst_msgs.get(position).getLastMsg().get(0).getMessage();
                int p = Integer.parseInt(msgTemp);
                holder.msg.setText(new String(Character.toChars(p)));

                holder.msg.setBackground(null);
                holder.msg.setBackgroundColor(Color.WHITE);

            } catch (Exception e) {
                e.printStackTrace();
                holder.msg.setText(lst_msgs.get(position).getLastMsg().get(0).getMessage());
            }


            String timeFormat = lst_msgs.get(position).getLastMsg().get(0).getEntryDate();
            timeFormat = timeFormat.replace("T", " ");

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            df.setTimeZone(TimeZone.getTimeZone("GTC"));
            Date date = null;
            try {
                date = df.parse(timeFormat);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            df.setTimeZone(TimeZone.getDefault());
            String formattedDate = df.format(date);

            if (formattedDate.contains(todaysDate)) {

                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat format2 = new SimpleDateFormat("hh:mm aa");

                try {
                    date = format1.parse(formattedDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                holder.txt_time.setText(format2.format(date));

            } else if (findDayDiff(formattedDate) == 1) {
                holder.txt_time.setText("Yesterday");
            } else {
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy");

                try {
                    date = format1.parse(formattedDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                holder.txt_time.setText(format2.format(date));
            }

            holder.main_ll.setOnClickListener(v -> {

                Log.d("ONClick Event", "onClick:");
                frag.onClickEvent(lst_msgs.get(position));
            });

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private int findDayDiff(String formattedDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf.parse(formattedDate);
            Date date1 = sdf.parse(todaysDate);
            if (date == null || date1 == null)
                return 0;

            return ((int) ((date.getTime() - date1.getTime()) / (1000 * 60 * 60 * 24))) * -1;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public void addtoList(String name, String imageUrl) {
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return lst_msgs.size();
    }

    public void filterList(ArrayList<ChatList> filteredList) {
        lst_msgs = filteredList;
        Log.d("CHAttingAdapterClass", "CHAttingAdapterClass");
        notifyDataSetChanged();

    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView user_name, msg, txt_count, txt_time;
        public ImageView profile_image, img_more, img_active;
        public RelativeLayout main_menu;
        public LinearLayout ll_chat_details;
        public LinearLayout main_ll;

        public MyViewHolder(View view) {
            super(view);
            txt_time = view.findViewById(R.id.txt_time);
            user_name = view.findViewById(R.id.user_name);
            txt_count = view.findViewById(R.id.txt_count);
            msg = view.findViewById(R.id.txt_msg);
            main_menu = view.findViewById(R.id.main_rl);
            img_more = view.findViewById(R.id.img_more);
            profile_image = view.findViewById(R.id.profile_image);
            ll_chat_details = view.findViewById(R.id.ll_chat_details);
            img_active = view.findViewById(R.id.img_active);
            main_ll = view.findViewById(R.id.main_ll);


            main_ll.setOnLongClickListener(v -> {
                selectedToDelete = getAdapterPosition();
                showBottomSheet(selectedToDelete);
                notifyDataSetChanged();
                return true;
            });

        }
    }


    private void showBottomSheet(int selectedToDelete) {
        FragmentManager fragmentManager = ((AppCompatActivity) mcontext).getSupportFragmentManager();
        bottomSheet = new LandingBottomSheet(this, selectedToDelete, "landing");
        bottomSheet.show(fragmentManager, "ModalBottomSheet");
    }

}
