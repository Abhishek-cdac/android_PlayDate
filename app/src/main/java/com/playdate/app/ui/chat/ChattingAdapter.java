package com.playdate.app.ui.chat;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.model.ChattingGetChats;
import com.playdate.app.model.Interest;
import com.playdate.app.model.chat_models.ChatExample;
import com.playdate.app.model.chat_models.ChatMessage;
import com.playdate.app.ui.anonymous_question.AnonymousBottomSheet;
import com.playdate.app.ui.chat.request.FragInbox;
import com.playdate.app.ui.chat.request.Onclick;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ChattingAdapter extends RecyclerView.Adapter<ChattingAdapter.MyViewHolder> {

    ArrayList<ChatExample> inboxList;
    Context mcontext;
    Onclick itemClick;
    private FragInbox frag;
    int selectedIndex = -1;
    int selectedToDelete = -1;

//    Bundle bundle = new Bundle();
    String name, image = null;

    LandingBottomSheet bottomSheet;

    public ChattingAdapter(ArrayList<ChatExample> inboxList, Onclick itemClick, FragInbox frag) {
        this.inboxList = inboxList;
        this.itemClick = itemClick;
        this.frag = frag;
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
        if (null != inboxList) {
            inboxList.remove(index);
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
        holder.user_name.setText(inboxList.get(position).getSenderName());
        Picasso.get().load(inboxList.get(position).getProfilePhoto())
                .placeholder(R.drawable.cupertino_activity_indicator).into(holder.profile_image);
    }

    public void addtoList(String name, String imageUrl) {
        Log.d("AcceptedChattingAdapter", "name " + name + " image " + imageUrl);
//        inboxList.add(new ChatExample(name,imageUrl));
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return inboxList.size();
    }

    public void filterList(ArrayList<ChatExample> filteredList) {
        inboxList = filteredList;
        Log.d("CHAttingAdapterClass", "CHAttingAdapterClass");
        notifyDataSetChanged();

    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView user_name, msg, txt_notify, txt_time;
        public ImageView profile_image, img_more, iv_delete_chat;
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
//            iv_delete_chat = view.findViewById(R.id.iv_delete_chat);
            main_ll = view.findViewById(R.id.main_ll);

            main_ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedIndex = getAdapterPosition();
                    Log.d("ONClick Event", "onClick:");
                    frag.onClickEvent(selectedIndex);
                }
            });


            main_ll.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    selectedToDelete = getAdapterPosition();
                    showBottomSheet(selectedToDelete);
                    notifyDataSetChanged();
                    return true;
                }
            });

        }
    }


    private void showBottomSheet(int selectedToDelete) {
        FragmentManager fragmentManager = ((AppCompatActivity) mcontext).getSupportFragmentManager();
        bottomSheet = new LandingBottomSheet(this, selectedToDelete, "landing");
        bottomSheet.show(fragmentManager, "ModalBottomSheet");
    }

}
