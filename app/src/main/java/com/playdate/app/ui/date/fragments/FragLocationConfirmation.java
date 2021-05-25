package com.playdate.app.ui.date.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.playdate.app.R;
import com.playdate.app.ui.date.games.FragGameMenu;
import com.playdate.app.ui.interfaces.OnInnerFragmentClicks;

public class FragLocationConfirmation extends Fragment {

    public FragLocationConfirmation() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_location_confirmed, container, false);

        ImageView image_restaurant;
        TextView tv_details;
        Button btn_proceed;

        image_restaurant = view.findViewById(R.id.image_restaurant);
        tv_details = view.findViewById(R.id.tv_details);
        btn_proceed = view.findViewById(R.id.btn_proceed);

        btn_proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnInnerFragmentClicks frag = (OnInnerFragmentClicks) getActivity();
                frag.ReplaceFrag(new FragGameMenu());


            }
        });

        return view;
    }

}
