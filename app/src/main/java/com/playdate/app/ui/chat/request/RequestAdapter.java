//package com.playdate.app.ui.chat.request;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.playdate.app.R;
//import com.playdate.app.model.chat_models.ChatExample;
//import com.squareup.picasso.Picasso;
//
//import java.util.ArrayList;
//
//public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.MyViewHolder> {
//    private ArrayList<ChatExample> inboxList;
//    private Onclick itemClick;
//
//    public RequestAdapter(ArrayList<ChatExample> inboxList, Onclick itemClick) {
//        this.inboxList = inboxList;
//        this.itemClick = itemClick;
//    }
//
//    public void filterList(ArrayList<ChatExample> filteredList) {
//        inboxList = filteredList;
//        notifyDataSetChanged();
//    }
//
//
//    public static class MyViewHolder extends RecyclerView.ViewHolder {
//        public TextView title, msg;
//        public ImageView profile_image, img_accept, img_reject;
//        public RelativeLayout main_menu;
//
//        public MyViewHolder(View view) {
//            super(view);
//            title = view.findViewById(R.id.user_name);
//            msg = view.findViewById(R.id.txt_msg);
//            main_menu = view.findViewById(R.id.main_rl);
//            img_accept = view.findViewById(R.id.img_accept);
//            img_reject = view.findViewById(R.id.img_reject);
//            profile_image = view.findViewById(R.id.profile_image);
//        }
//    }
//
//    @Override
//    public RequestAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_list_row, parent, false);
//        return new MyViewHolder(itemView);
//    }
//
//    @Override
//    public void onBindViewHolder(RequestAdapter.MyViewHolder holder, int position) {
////        Inbox inbox = inboxList.get(position);
//        ChatExample example = inboxList.get(position);
//        holder.title.setText(example.getSenderName());
//        Picasso.get().load(example.getProfilePhoto()).into(holder.profile_image);
//
//
//        holder.img_accept.setOnClickListener(v -> {
//            inboxList.remove(position);
//            notifyDataSetChanged();
//        });
//
//        holder.img_reject.setOnClickListener(v -> {
//            inboxList.remove(position);
//            notifyDataSetChanged();
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        if (inboxList == null) {
//            return 0;
//        }
//        return inboxList.size();
//    }
//}