package com.playdate.app.ui.my_profile_details;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.playdate.app.R;
import com.playdate.app.ui.register.interest.InterestActivity;
import com.playdate.app.ui.register.profile.UploadProfileActivity;
import com.playdate.app.ui.register.username.UserNameActivity;

public class FragMyProfileDetails extends Fragment implements View.OnClickListener {
    ImageView iv_dark_mode;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_my_details, container, false);
        ImageView profile_image = view.findViewById(R.id.profile_image);
        ImageView iv_edit_username = view.findViewById(R.id.iv_edit_username);
        ImageView iv_interest = view.findViewById(R.id.iv_interest);
        iv_dark_mode = view.findViewById(R.id.iv_dark_mode);
        TextView txt_change_photo = view.findViewById(R.id.txt_change_photo);
        profile_image.setOnClickListener(this);
        iv_edit_username.setOnClickListener(this);
        txt_change_photo.setOnClickListener(this);
        iv_edit_username.setOnClickListener(this);
        iv_interest.setOnClickListener(this);
        iv_dark_mode.setOnClickListener(this);
        return view;

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.profile_image || id == R.id.txt_change_photo) {
            Intent mIntent = new Intent(getActivity(), UploadProfileActivity.class);
            mIntent.putExtra("fromProfile", true);
            startActivityForResult(mIntent, 407);
        } else if (id == R.id.iv_edit_username) {
            Intent mIntent = new Intent(getActivity(), UserNameActivity.class);
            mIntent.putExtra("fromProfile", true);
            startActivityForResult(mIntent, 408);
        }else if (id == R.id.iv_interest) {
            Intent mIntent = new Intent(getActivity(), InterestActivity.class);
            mIntent.putExtra("fromProfile", true);
            startActivityForResult(mIntent, 409);
        }else if(id==R.id.iv_dark_mode){
            if(iv_dark_mode.getRotation()==180){
                iv_dark_mode.setImageResource(R.drawable.dark_mode);
                iv_dark_mode.setRotation(0);
            }else{
                iv_dark_mode.setImageResource(R.drawable.dark_mode_sel);
                iv_dark_mode.setRotation(180);
            }

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

}
