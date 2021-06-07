package com.playdate.app.ui;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;

import java.util.ArrayList;

public class SampleAdapter extends RecyclerView.Adapter<SampleAdapter.ViewHolder> {
    ArrayList<SampleModel> arrlist;
    Context mcontext;

    public SampleAdapter(ArrayList<SampleModel> arrlist, Context mcontext) {
        this.arrlist = arrlist;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public SampleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_type_me, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SampleAdapter.ViewHolder holder, int position) {
        if (arrlist.size() == 0) {
            Toast.makeText(mcontext, "List is Empty", Toast.LENGTH_SHORT).show();
        }else{
            holder.chat_image.setVisibility(View.VISIBLE);
            holder.chat_image.setImageBitmap(arrlist.get(position).getBitmap());
            notifyDataSetChanged();
        }


    }

    @Override
    public int getItemCount() {
        return arrlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView chat_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            chat_image = itemView.findViewById(R.id.chat_image);
        }
    }
}
