package com.playdate.app.ui.coupons.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.model.CouponStore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CouponStoreAdapter extends RecyclerView.Adapter<CouponStoreAdapter.ViewHolder> {
    ArrayList<CouponStore> coupon_list = new ArrayList<>();

    public CouponStoreAdapter() {
        coupon_list.add(new CouponStore("Tiki Village", "Enjoy 15% OFF on our specials meals and drinks", "100", "https://www.google.co.in/imgres?imgurl=https%3A%2F%2Fwww.patnabeats.com%2Fwp-content%2Fuploads%2F2018%2F12%2FLazeez-Gold-3.jpg&imgrefurl=https%3A%2F%2Fwww.patnabeats.com%2Fhave-an-amazing-experience-of-lazeez-cuisines-in-a-gufaa-themed-restaurant-in-patna%2F&tbnid=FJg_OjSYjHeSTM&vet=12ahUKEwij-uD8yMPwAhVCSisKHclCCDUQMygAegUIARDQAQ..i&docid=w0ba22AtaqsSGM&w=1024&h=678&q=restaurant&ved=2ahUKEwij-uD8yMPwAhVCSisKHclCCDUQMygAegUIARDQAQ"));
        coupon_list.add(new CouponStore("Rico's Cuisine", "Enjoy 25% OFF on our specials meals and drinks", "150", "https://www.google.co.in/imgres?imgurl=https%3A%2F%2Fcdn.theculturetrip.com%2Fimages%2F56-3975727-14446622424de74525756848968809b23630e10d8f.jpg&imgrefurl=https%3A%2F%2Ftheculturetrip.com%2Fasia%2Findia%2Farticles%2Fthe-10-best-restaurants-in-patna-india%2F&tbnid=A__rtOszU_1RhM&vet=12ahUKEwij-uD8yMPwAhVCSisKHclCCDUQMygCegUIARDUAQ..i&docid=9LpPulynQuqkTM&w=668&h=447&q=restaurant&ved=2ahUKEwij-uD8yMPwAhVCSisKHclCCDUQMygCegUIARDUAQ"));
        coupon_list.add(new CouponStore("Fine Taste", "Enjoy 30% OFF on our specials meals and drinks", "200", "https://www.google.co.in/imgres?imgurl=https%3A%2F%2Fupload.wikimedia.org%2Fwikipedia%2Fcommons%2Fthumb%2F6%2F62%2FBarbieri_-_ViaSophia25668.jpg%2F1200px-Barbieri_-_ViaSophia25668.jpg&imgrefurl=https%3A%2F%2Fen.wikipedia.org%2Fwiki%2FRestaurant&tbnid=_ltPgRFsyntrzM&vet=12ahUKEwij-uD8yMPwAhVCSisKHclCCDUQMygFegUIARDaAQ..i&docid=GJpv6Vju4A3OkM&w=1200&h=1034&q=restaurant&ved=2ahUKEwij-uD8yMPwAhVCSisKHclCCDUQMygFegUIARDaAQ"));
        coupon_list.add(new CouponStore("Burger Land", "Enjoy 15% OFF on our specials meals and drinks", "100", "https://www.google.co.in/imgres?imgurl=https%3A%2F%2Fupload.wikimedia.org%2Fwikipedia%2Fcommons%2Fe%2Fef%2FRestaurant_N%25C3%25A4sinneula.jpg&imgrefurl=https%3A%2F%2Fen.wikipedia.org%2Fwiki%2FRevolving_restaurant&tbnid=gWSoOympoRKg5M&vet=12ahUKEwij-uD8yMPwAhVCSisKHclCCDUQMygKegUIARDkAQ..i&docid=_KBXH-kPR3cExM&w=4500&h=2900&q=restaurant&ved=2ahUKEwij-uD8yMPwAhVCSisKHclCCDUQMygKegUIARDkAQ"));
        coupon_list.add(new CouponStore("Luigi's Pub", "Enjoy 15% OFF on our specials meals and drinks", "50", "https://www.google.co.in/imgres?imgurl=https%3A%2F%2Fmedia-cdn.tripadvisor.com%2Fmedia%2Fphoto-s%2F17%2F75%2F3f%2Fd1%2Frestaurant-in-valkenswaard.jpg&imgrefurl=https%3A%2F%2Fwww.tripadvisor.in%2FRestaurant_Review-g652290-d15594274-Reviews-Restaurant_EDEN-Valkenswaard_North_Brabant_Province.html&tbnid=_Fucc8yFVdP4fM&vet=12ahUKEwij-uD8yMPwAhVCSisKHclCCDUQMygPegUIARDuAQ..i&docid=pkjbd6bFuF9ipM&w=550&h=367&q=restaurant&ved=2ahUKEwij-uD8yMPwAhVCSisKHclCCDUQMygPegUIARDuAQ"));
        coupon_list.add(new CouponStore("The Diner", "Enjoy 35% OFF on our specials meals and drinks", "75", "https://www.google.co.in/imgres?imgurl=https%3A%2F%2Fwww.patnabeats.com%2Fwp-content%2Fuploads%2F2018%2F12%2FLazeez-Gold-3.jpg&imgrefurl=https%3A%2F%2Fwww.patnabeats.com%2Fhave-an-amazing-experience-of-lazeez-cuisines-in-a-gufaa-themed-restaurant-in-patna%2F&tbnid=FJg_OjSYjHeSTM&vet=12ahUKEwij-uD8yMPwAhVCSisKHclCCDUQMygAegUIARDQAQ..i&docid=w0ba22AtaqsSGM&w=1024&h=678&q=restaurant&ved=2ahUKEwij-uD8yMPwAhVCSisKHclCCDUQMygAegUIARDQAQ"));
        coupon_list.add(new CouponStore("Tiki Village", "Enjoy 15% OFF on our specials meals and drinks", "100", "https://www.google.co.in/imgres?imgurl=https%3A%2F%2Fupload.wikimedia.org%2Fwikipedia%2Fcommons%2Fe%2Fef%2FRestaurant_N%25C3%25A4sinneula.jpg&imgrefurl=https%3A%2F%2Fen.wikipedia.org%2Fwiki%2FRevolving_restaurant&tbnid=gWSoOympoRKg5M&vet=12ahUKEwij-uD8yMPwAhVCSisKHclCCDUQMygKegUIARDkAQ..i&docid=_KBXH-kPR3cExM&w=4500&h=2900&q=restaurant&ved=2ahUKEwij-uD8yMPwAhVCSisKHclCCDUQMygKegUIARDkAQ"));
    }

    @NonNull
    @Override
    public CouponStoreAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_coupon_store, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CouponStoreAdapter.ViewHolder holder, int position) {
        holder.rest_name.setText(coupon_list.get(position).getRest_name());
        holder.discount_desc.setText(coupon_list.get(position).getDiscount_desc());
        holder.points.setText(coupon_list.get(position).getPoints());
        Picasso.get().load(R.drawable.restaurant_1)
                .placeholder(R.drawable.cupertino_activity_indicator).into(holder.iv_image);
//        Picasso.get().load(coupon_list.get(position).getImage())
//                .placeholder(R.drawable.cupertino_activity_indicator).into(holder.iv_image);

    }

    @Override
    public int getItemCount() {
        return coupon_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_image;
        TextView rest_name, discount_desc, points;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_image = itemView.findViewById(R.id.iv_image);
            rest_name = itemView.findViewById(R.id.rest_name);
            discount_desc = itemView.findViewById(R.id.discount_desc);
            points = itemView.findViewById(R.id.points);
        }
    }
}
