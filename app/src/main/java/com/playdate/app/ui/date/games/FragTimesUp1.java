package com.playdate.app.ui.date.games;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.playdate.app.R;
import com.playdate.app.ui.interfaces.OnInnerFragmentClicks;
import com.playdate.app.ui.restaurant.RestaurantActivity;
import com.playdate.app.util.customcamera.otalia.GameCamera;

public class FragTimesUp1 extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_times_up_1, container, false);
        Button btn_keep_playing;
        Button btn_finish_date;

        btn_finish_date = view.findViewById(R.id.btn_finish_date);
        btn_keep_playing = view.findViewById(R.id.btn_keep_playing);

        btn_keep_playing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnInnerFragmentClicks frag = (OnInnerFragmentClicks) getActivity();
                frag.ReplaceFrag(new FragTimesUp2());
            }
        });

        btn_finish_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), GameCamera
                        .class));
            }
        });

        return view;
    }
}
