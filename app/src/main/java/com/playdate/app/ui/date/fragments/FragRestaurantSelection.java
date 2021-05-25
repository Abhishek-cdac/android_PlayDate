package com.playdate.app.ui.date.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.ui.date.adapter.RestaurantSelectionAdapter;
import com.playdate.app.ui.date.games.FragTimesUp2;
import com.playdate.app.ui.interfaces.OnInnerFragmentClicks;

public class FragRestaurantSelection extends Fragment implements restaurantSelecteListener {
    public FragRestaurantSelection() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_select_restaurant, container, false);
        RecyclerView rv_restaurant = view.findViewById(R.id.rv_restaurant);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rv_restaurant.setLayoutManager(manager);

        RestaurantSelectionAdapter adapter = new RestaurantSelectionAdapter(this);
        rv_restaurant.setAdapter(adapter);


        return view;
    }

    @Override
    public void restSelected() {
        OnInnerFragmentClicks frag = (OnInnerFragmentClicks) getActivity();
        frag.ReplaceFrag(new FragLocationConfirmation());
    }
}

interface restaurantSelecteListener {
    void restSelected();
}
