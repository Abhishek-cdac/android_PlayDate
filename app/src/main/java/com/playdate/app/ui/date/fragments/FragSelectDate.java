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
import com.playdate.app.ui.date.fragments.FragLocationTracing;
import com.playdate.app.ui.interfaces.OnInnerFragmentClicks;

public class FragSelectDate extends Fragment {
    RelativeLayout rl_inperson, rl_virtual;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_select_date, container, false);
        rl_inperson = view.findViewById(R.id.in_person);
        rl_virtual = view.findViewById(R.id.virtual);

        rl_inperson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnInnerFragmentClicks frag = (OnInnerFragmentClicks) getActivity();
                frag.ReplaceFrag(new FragLocationTracing());
//                startActivity(new Intent(getActivity(),LocationTracing.class));
            }
        });

        rl_virtual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
////////rest
            }
        });

        return view;
    }

//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_select_date);
//        rl_inperson = findViewById(R.id.in_person);
//        rl_virtual = findViewById(R.id.virtual);
//
//        rl_inperson.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(SelectDateActivity.this,LocationTracing.class));
//            }
//        });
//    }
}
