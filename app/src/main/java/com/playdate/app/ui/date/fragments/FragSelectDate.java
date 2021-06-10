package com.playdate.app.ui.date.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.playdate.app.R;
import com.playdate.app.ui.interfaces.OnInnerFragmentClicks;

import java.util.Objects;

public class FragSelectDate extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_select_date, container, false);
        RelativeLayout rl_inperson = view.findViewById(R.id.in_person);
        RelativeLayout rl_virtual = view.findViewById(R.id.virtual);

        rl_inperson.setOnClickListener(v -> {
            OnInnerFragmentClicks frag = (OnInnerFragmentClicks) getActivity();
            Objects.requireNonNull(frag).ReplaceFrag(new FragLocationTracing());
        });

        rl_virtual.setOnClickListener(v -> {
            OnInnerFragmentClicks frag = (OnInnerFragmentClicks) getActivity();
            Objects.requireNonNull(frag).ReplaceFrag(new FragRestaurantSelection());
        });

        return view;
    }

}
