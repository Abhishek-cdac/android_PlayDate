package com.playdate.app.ui.my_profile_details;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.playdate.app.R;
import com.playdate.app.ui.forgot_password.ForgotPassword;
import com.playdate.app.ui.register.age_verification.AgeVerifiationActivity;
import com.playdate.app.ui.register.interestin.InterestActivity;
import com.playdate.app.ui.register.profile.UploadProfileActivity;
import com.playdate.app.ui.register.relationship.RelationActivity;

public class FragMyProfilePersonal extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_my_personal,container,false);
        ImageView profile_image = view.findViewById(R.id.profile_image);
        ImageView iv_edit_mail = view.findViewById(R.id.iv_edit_mail);
        ImageView iv_dob = view.findViewById(R.id.iv_dob);
        ImageView iv_relationship = view.findViewById(R.id.iv_relationship);
        ImageView iv_sex_orientation = view.findViewById(R.id.iv_sex_orientation);
        TextView txt_change_photo = view.findViewById(R.id.txt_change_photo);
        ImageView iv_gender = view.findViewById(R.id.iv_gender);
        profile_image.setOnClickListener(this);
        iv_gender.setOnClickListener(this);
        iv_sex_orientation.setOnClickListener(this);
        iv_dob.setOnClickListener(this);
        iv_edit_mail.setOnClickListener(this);
        iv_relationship.setOnClickListener(this);
        txt_change_photo.setOnClickListener(this);
        return view;

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.profile_image || id == R.id.txt_change_photo) {
            Intent mIntent = new Intent(getActivity(), UploadProfileActivity.class);
            mIntent.putExtra("fromProfile", true);
            startActivityForResult(mIntent, 407);
        }else if(id==R.id.iv_gender){
            Intent mIntent = new Intent(getActivity(), InterestActivity.class);
            mIntent.putExtra("fromProfile", true);
            startActivityForResult(mIntent, 408);
        }else if(id==R.id.iv_dob){
            Intent mIntent = new Intent(getActivity(), AgeVerifiationActivity.class);
            mIntent.putExtra("fromProfile", true);
            startActivityForResult(mIntent, 408);
        }else if(id==R.id.iv_sex_orientation){
            Intent mIntent = new Intent(getActivity(), InterestActivity.class);
            mIntent.putExtra("fromProfile", true);
            startActivityForResult(mIntent, 408);
        }else if(id==R.id.iv_relationship){
            Intent mIntent = new Intent(getActivity(), RelationActivity.class);
            mIntent.putExtra("fromProfile", true);
            startActivityForResult(mIntent, 408);
        }else if(id==R.id.iv_edit_mail){
            Intent mIntent = new Intent(getActivity(), ForgotPassword.class);
            mIntent.putExtra("fromProfile", true);
            startActivityForResult(mIntent, 408);
        }
    }
}
