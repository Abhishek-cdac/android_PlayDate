package com.playdate.app.ui.date.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.playdate.app.R;
import com.playdate.app.ui.date.fragments.FragSelectPartner;
import com.playdate.app.ui.interfaces.OnInnerFragmentClicks;

public class FragIntroScreen extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_date_intro, container, false);
        TextView tv_create_date = view.findViewById(R.id.tv_create_date);
        TextView tv_accept_date = view.findViewById(R.id.tv_accept_date);

        tv_create_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnInnerFragmentClicks frag = (OnInnerFragmentClicks) getActivity();
                frag.ReplaceFrag(new FragSelectPartner());
//                startActivity(new Intent(IntroScreen.this, SelectPartner.class));
            }
        });

        return view;
    }
}
