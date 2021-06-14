package com.playdate.app.ui.date.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.model.RestaurentData;
import com.playdate.app.ui.date.fragments.FragRestaurantSelection;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RestaurantSelectionAdapter extends RecyclerView.Adapter<RestaurantSelectionAdapter.ViewHolder> {
    private ArrayList<RestaurentData> list = new ArrayList<>();
    private FragRestaurantSelection frag;

    int selectedposition = -1;
    Context mcontext;
    Picasso picasso;


    public RestaurantSelectionAdapter(FragmentActivity activity, ArrayList<RestaurentData> lst_getRestaurentsDetail, FragRestaurantSelection frag) {

        this.mcontext = activity;
        this.list = lst_getRestaurentsDetail;
        picasso = Picasso.get();
        this.frag = frag;
    }


    @NonNull
    @Override
    public RestaurantSelectionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_date_restaurant_selection, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantSelectionAdapter.ViewHolder holder, int position) {

        picasso.load(list.get(position).getImage())
                .fit()
                .centerCrop()
                .into(holder.iv_rest);


        try {
            if (selectedposition == position) {
                holder.cl_rest.setBackgroundResource(R.drawable.btn_pink_filled);
                frag.restSelected();

            } else {
                holder.cl_rest.setBackgroundResource(R.drawable.login_button);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        Log.e("restaurants", "" + list.size());
        return list.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_rest;
        ConstraintLayout cl_rest;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_rest = itemView.findViewById(R.id.iv_rest);
            cl_rest = itemView.findViewById(R.id.cl_rest);

            cl_rest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedposition = getAdapterPosition();
                    notifyDataSetChanged();

                }
            });
        }
    }
}
