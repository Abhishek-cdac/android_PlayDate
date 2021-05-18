package com.playdate.app.ui.date.games;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.ui.date.adapter.PopularGamesAdapter;

public class FragPopularGames extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_popular_games, container, false);

        RecyclerView rv_popular_games = view.findViewById(R.id.rv_popular_games);
        RecyclerView.LayoutManager manager = new GridLayoutManager(getContext(), 2);
        rv_popular_games.setLayoutManager(manager);

        PopularGamesAdapter adapter = new PopularGamesAdapter();
        rv_popular_games.setAdapter(adapter);


        return view;
    }
}
