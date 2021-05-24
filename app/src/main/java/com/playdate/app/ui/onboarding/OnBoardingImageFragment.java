package com.playdate.app.ui.onboarding;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.playdate.app.R;

import java.util.zip.Inflater;

public class OnBoardingImageFragment extends Fragment {
    public OnBoardingImageFragment() {
    }

    int position;

    public OnBoardingImageFragment(int position) {
        this.position = position;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.on_boarding, container, false);
        ImageView img_back = view.findViewById(R.id.img_back);
        //  img_back.setImageResource(R.drawable.couple1);
        switch (position) {
            case 0:
                img_back.setImageResource(R.drawable.couple1);
                break;
            case 1:
                img_back.setImageResource(R.drawable.couple1);
                break;
            case 2:
                img_back.setImageResource(R.drawable.couple1);
                break;
            case 3:
                img_back.setImageResource(R.drawable.couple1);
                break;

        }

        return view;
    }
}
