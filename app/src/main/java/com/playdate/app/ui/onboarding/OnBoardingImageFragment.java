package com.playdate.app.ui.onboarding;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.playdate.app.R;

public class OnBoardingImageFragment extends Fragment {
    public OnBoardingImageFragment() {
    }

    public OnBoardingImageFragment(int position) {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.on_boarding, container, false);
        ImageView img_back = view.findViewById(R.id.img_back);
        img_back.setImageResource(R.drawable.couple1);

        return view;
    }
}
