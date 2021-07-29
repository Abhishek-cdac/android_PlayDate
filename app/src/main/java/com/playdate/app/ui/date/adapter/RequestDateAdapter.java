package com.playdate.app.ui.date.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.model.CreateDateGetMyPartnerReqData;

import com.playdate.app.ui.chat.request.Onclick;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RequestDateAdapter extends RecyclerView.Adapter<RequestDateAdapter.MyViewHolder> {
    ArrayList<CreateDateGetMyPartnerReqData> lst_getReqPartnerDetail;
    Onclick itemClick;
//    FragInbox ref;
    String requestId;

    public RequestDateAdapter(ArrayList<CreateDateGetMyPartnerReqData> lst_getReqPartnerDetail, Onclick itemClick) {
        this.lst_getReqPartnerDetail = lst_getReqPartnerDetail;
        this.itemClick = itemClick;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView profile_image, img_accept, img_reject;
        public RelativeLayout main_menu;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.user_name);

            main_menu = view.findViewById(R.id.main_rl);
            img_accept = view.findViewById(R.id.img_accept);
            img_reject = view.findViewById(R.id.img_reject);
            profile_image = view.findViewById(R.id.profile_image);

        }
    }

    @Override
    public RequestDateAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_date_list_row, parent, false);
        return new RequestDateAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RequestDateAdapter.MyViewHolder holder, int position) {
        CreateDateGetMyPartnerReqData example = lst_getReqPartnerDetail.get(position);
        holder.title.setText(example.getUsername());
        Picasso.get().load(example.getProfilePicPath()).into(holder.profile_image);
        requestId = example.getRequestId();

        holder.img_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                itemClick.onItemClicks(v, position, 12, requestId);

                notifyDataSetChanged();
            }
        });

        holder.img_reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClick.onItemClicks(v, position, 13, requestId);

                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return lst_getReqPartnerDetail.size();
    }
}