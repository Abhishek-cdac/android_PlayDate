package com.playdate.app.ui.my_profile_details.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.ui.my_profile_details.FragInstaLikeProfile;
import com.playdate.app.ui.my_profile_details.FragMyUploadMedia;
import com.playdate.app.ui.social.model.PostDetails;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class InstaPhotosAdapter extends RecyclerView.Adapter<InstaPhotosAdapter.ViewHolder> {


    public static boolean isLocked = false;
    Context mContext;
    FragmentActivity activity;
    ArrayList<PostDetails> lst;
    FragMyUploadMedia frag;

    public InstaPhotosAdapter(FragmentActivity activity, ArrayList<PostDetails> lst, FragMyUploadMedia frag) {
        this.activity = activity;
        this.lst = lst;
        this.frag = frag;
    }


    Picasso picasso;

    @NonNull
    @Override
    public InstaPhotosAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_photos, null);
        mContext = parent.getContext();
        picasso = Picasso.get();
        return new InstaPhotosAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InstaPhotosAdapter.ViewHolder holder, int position) {
        if (isLocked) {
            holder.iv_payment.setImageResource(R.drawable.ic_pink_lock);
//            holder.iv_payment.setImageResource(R.drawable.pink_timelapse);
            // holder.iv_chat.setImageResource(R.drawable.chat_black);
            holder.card_grid.setCardBackgroundColor(mContext.getResources().getColor(R.color.black_back));
            holder.iv_payment.getLayoutParams().width = (int) mContext.getResources().getDimension(R.dimen._30sdp);
            holder.iv_payment.getLayoutParams().height = (int) mContext.getResources().getDimension(R.dimen._30sdp);
        } else {
            if(lst.get(position).getPostType().equals("Question")){

            }else if(lst.get(position).getPostType().equals("Normal")){
                if (!lst.get(position).getPostMedia().get(0).getMediaType().equals("Video")) {
                    picasso.load(lst.get(position).getPostMedia().get(0).getMediaFullPath())
                            .into(holder.iv_payment);
                } else {
                    holder.iv_play.setVisibility(View.VISIBLE);
                    picasso.load(lst.get(position).getPostMedia().get(0).getMediaThumbName())
                            .into(holder.iv_payment);
                }
            }

            holder.card_grid.setCardBackgroundColor(mContext.getResources().getColor(R.color.white));
        }

    }

    @Override
    public int getItemCount() {
        return lst.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_payment;
        ImageView iv_play;
        //        ImageView iv_chat;
        CardView card_grid;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_payment = itemView.findViewById(R.id.iv_payment);
            iv_play = itemView.findViewById(R.id.iv_play);
            card_grid = itemView.findViewById(R.id.card_grid);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isLocked) {
                        frag.setView(1, getAbsoluteAdapterPosition());
                    }

                }
            });
//            iv_payment.setOnClickListener(view -> fragInstaLikeProfile.onTypeChange(1));
        }
    }
}


