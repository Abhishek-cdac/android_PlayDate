package com.playdate.app.ui.chat;

import android.content.Context;
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

import java.util.ArrayList;

public class ChattingAdapter extends RecyclerView.Adapter<ChattingAdapter.MyViewHolder> {

    ArrayList<ChatList> lst_msgs;
    Context mcontext;
    Onclick itemClick;
    private FragInbox frag;
    int selectedIndex = -1;
    int selectedToDelete = -1;

    //    Bundle bundle = new Bundle();
    String name, image = null;

    LandingBottomSheet bottomSheet;
    Picasso picasso;
    SessionPref pref;

    public ChattingAdapter(ArrayList<ChatList> inboxList, Onclick itemClick, FragInbox frag) {
        this.lst_msgs = inboxList;
        this.itemClick = itemClick;
        this.frag = frag;
        picasso = Picasso.get();
        pref = SessionPref.getInstance(frag.getActivity());
    }

    public ChattingAdapter(String name, String image) {
        this.name = name;
        this.image = image;
        addtoList(name, image);
    }

    public ChattingAdapter() {

    }

//    @Override
//    public Filter getFilter() {
//        return new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence constraint) {
//                String charsequence = constraint.toString();
//                if (charsequence.isEmpty()) {
//                    search_list = inboxList;
//                } else {
//                    ArrayList<ChatExample> filteredList = new ArrayList<>();
//                    for (ChatExample row : inboxList) {
//                        Log.d("ROW of sendername", row.getSenderName());
//                        if (row.getSenderName().toLowerCase().contains(charsequence.toLowerCase())) {
//                            filteredList.add(row);
//                        }
//                    }
//                    search_list = filteredList;
//                }
//                FilterResults results = new FilterResults();
//                results.values = search_list;
//                return results;
//            }
//
//            @Override
//            protected void publishResults(CharSequence constraint, FilterResults results) {
//                search_list = (ArrayList<ChatExample>) results.values;
//                notifyDataSetChanged();
//
//            }
//        };
//    }

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
            if (!pref.getStringVal(SessionPref.LoginUserID).equals(lst_msgs.get(position).getLstFrom().get(0).getUserId())) {
                holder.user_name.setText(lst_msgs.get(position).getLstFrom().get(0).getUsername());
                picasso.load(lst_msgs.get(position).getLstFrom().get(0).getProfilePicPath()).placeholder(R.drawable.profile).into(holder.profile_image);
                if(lst_msgs.get(position).getLstFrom().get(0).getOnlineStatus().toLowerCase().equals("online")){
                    holder.img_active.setVisibility(View.VISIBLE);
                }else{
                    holder.img_active.setVisibility(View.GONE);
                }



            } else if (!pref.getStringVal(SessionPref.LoginUserID).equals(lst_msgs.get(position).getLstToUser().get(0).getUserId())) {
                holder.user_name.setText(lst_msgs.get(position).getLstToUser().get(0).getUsername());
                picasso.load(lst_msgs.get(position).getLstToUser().get(0).getProfilePicPath()).placeholder(R.drawable.profile).into(holder.profile_image);
                if(lst_msgs.get(position).getLstToUser().get(0).getOnlineStatus().toLowerCase().equals("online")){
                    holder.img_active.setVisibility(View.VISIBLE);
                }else{
                    holder.img_active.setVisibility(View.GONE);
                }
            }

            holder.msg.setText(lst_msgs.get(position).getLastMsg().get(0).getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }


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
        public TextView user_name, msg, txt_notify, txt_time;
        public ImageView profile_image, img_more, img_active;
        public RelativeLayout main_menu;
        public LinearLayout ll_chat_details;
        public LinearLayout main_ll;

        public MyViewHolder(View view) {
            super(view);
            txt_time = (TextView) view.findViewById(R.id.txt_time);
            user_name = (TextView) view.findViewById(R.id.user_name);
            txt_notify = (TextView) view.findViewById(R.id.txt_notify);
            msg = (TextView) view.findViewById(R.id.txt_msg);
            main_menu = view.findViewById(R.id.main_rl);
            img_more = view.findViewById(R.id.img_more);
            profile_image = view.findViewById(R.id.profile_image);
            ll_chat_details = view.findViewById(R.id.ll_chat_details);
            img_active = view.findViewById(R.id.img_active);
            main_ll = view.findViewById(R.id.main_ll);

            main_ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedIndex = getAdapterPosition();
                    Log.d("ONClick Event", "onClick:");
                    frag.onClickEvent(selectedIndex);
                }
            });


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
