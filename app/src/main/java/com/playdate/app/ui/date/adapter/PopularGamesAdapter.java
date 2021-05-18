package com.playdate.app.ui.date.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.model.PopularGames;

import java.util.ArrayList;

public class PopularGamesAdapter extends RecyclerView.Adapter<PopularGamesAdapter.ViewHolder> {
    ArrayList<PopularGames> list = new ArrayList<>();

    public PopularGamesAdapter() {
        list.add(new PopularGames("The Spirit Animal", R.drawable.game1));
        list.add(new PopularGames("Guess My Drawing", R.drawable.game2));
        list.add(new PopularGames("one word answer", R.drawable.game3));
        list.add(new PopularGames("36 question", R.drawable.game1));
        list.add(new PopularGames("The Spirit Animal", R.drawable.game2));
        list.add(new PopularGames("The Spirit Animal", R.drawable.game1));
    }

    @NonNull
    @Override
    public PopularGamesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_popular_games, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularGamesAdapter.ViewHolder holder, int position) {
        holder.iv_games_image.setImageResource(list.get(position).getGame_image());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_games_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_games_image = itemView.findViewById(R.id.iv_games_image);
        }
    }
}
