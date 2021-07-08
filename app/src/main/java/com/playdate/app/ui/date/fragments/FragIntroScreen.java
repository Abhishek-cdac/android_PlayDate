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
import com.playdate.app.ui.interfaces.OnBackPressed;

import com.playdate.app.ui.interfaces.OnInnerFragmentClicks;

import java.util.Objects;

public class FragIntroScreen extends Fragment {
    public FragIntroScreen() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_date_intro, container, false);
        TextView tv_create_date = view.findViewById(R.id.tv_create_date);
        Button tv_accept_date = view.findViewById(R.id.tv_accept_date);
        ImageView cancel = view.findViewById(R.id.cancel);

        tv_create_date.setOnClickListener(v -> {
            OnInnerFragmentClicks frag = (OnInnerFragmentClicks) getActivity();
            assert frag != null;
            frag.ReplaceFrag(new FragSelectDate(false));
        });

        tv_accept_date.setOnClickListener(v -> {
            OnInnerFragmentClicks frag = (OnInnerFragmentClicks) getActivity();
            assert frag != null;
            frag.ReplaceFrag(new FragAcceptDatePartner());
        });
        cancel.setOnClickListener(v -> {
            try {
                OnBackPressed inf = (OnBackPressed) getActivity();
                Objects.requireNonNull(inf).onBack();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return view;
    }
}


