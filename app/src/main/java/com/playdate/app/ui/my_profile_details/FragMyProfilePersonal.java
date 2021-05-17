package com.playdate.app.ui.my_profile_details;

import android.content.Intent;
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
import com.playdate.app.ui.dashboard.OnProfilePhotoChageListerner;
import com.playdate.app.ui.forgot_password.ForgotPasswordActivity;
import com.playdate.app.ui.register.age_verification.AgeVerifiationActivity;
import com.playdate.app.ui.register.gender.GenderSelActivity;
import com.playdate.app.ui.register.interestin.InterestActivity;
import com.playdate.app.ui.register.profile.UploadProfileActivity;
import com.playdate.app.ui.register.relationship.RelationActivity;
import com.playdate.app.util.session.SessionPref;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.playdate.app.data.api.RetrofitClientInstance.BASE_URL_IMAGE;

public class FragMyProfilePersonal extends Fragment implements View.OnClickListener {
    TextView email;
    TextView txt_phone;
    TextView txt_gender;
    TextView DOB;
    TextView txt_relationship;
    TextView interestin;
    CircleImageView profile_image;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_my_personal, container, false);
        email = view.findViewById(R.id.email);
        txt_phone = view.findViewById(R.id.txt_phone);
        txt_gender = view.findViewById(R.id.txt_gender);
        DOB = view.findViewById(R.id.DOB);
        txt_relationship = view.findViewById(R.id.txt_relationship);
        interestin = view.findViewById(R.id.interestin);
        profile_image = view.findViewById(R.id.profile_image);

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


        setValues();


        return view;

    }

    private void setValues() {
        SessionPref pref = SessionPref.getInstance(getActivity());
        email.setText(pref.getStringVal(SessionPref.LoginUseremail));
        String mobile = pref.getStringVal(SessionPref.LoginUserphoneNo);
        String temp = mobile.substring(0, 3);
        String temp1 = mobile.substring(3, 6);
        String temp2 = mobile.substring(6, 10);
        txt_phone.setText(temp + "-" + temp1 + "-" + temp2);
        txt_gender.setText(pref.getStringVal(SessionPref.LoginUsergender));
        String[] s = pref.getStringVal(SessionPref.LoginUserbirthDate).split("T");
        DOB.setText(s[0]);
        txt_relationship.setText(pref.getStringVal(SessionPref.LoginUserrelationship));
        interestin.setText(pref.getStringVal(SessionPref.LoginUserinterestedIn));

        String img = pref.getStringVal(SessionPref.LoginUserprofilePic);
        if (img.contains("http")) {
            Picasso.get().load(img)
                    .placeholder(R.drawable.cupertino_activity_indicator)
                    .into(profile_image);
        } else {
            Picasso.get().load(BASE_URL_IMAGE + img)
                    .placeholder(R.drawable.cupertino_activity_indicator)
                    .into(profile_image);
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.profile_image || id == R.id.txt_change_photo) {
            Intent mIntent = new Intent(getActivity(), UploadProfileActivity.class);
            mIntent.putExtra("fromProfile", true);
            startActivityForResult(mIntent, 407);
        } else if (id == R.id.iv_gender) {
            Intent mIntent = new Intent(getActivity(), GenderSelActivity.class);
            mIntent.putExtra("fromProfile", true);
            startActivityForResult(mIntent, 408);
        } else if (id == R.id.iv_dob) {
            Intent mIntent = new Intent(getActivity(), AgeVerifiationActivity.class);
            mIntent.putExtra("CurrentDOB",DOB.getText().toString());
            mIntent.putExtra("fromProfile", true);
            startActivityForResult(mIntent, 408);
        } else if (id == R.id.iv_sex_orientation) {
            Intent mIntent = new Intent(getActivity(), InterestActivity.class);
            mIntent.putExtra("Selected", interestin.getText().toString());
            mIntent.putExtra("fromProfile", true);

            startActivityForResult(mIntent, 408);
        } else if (id == R.id.iv_relationship) {
            Intent mIntent = new Intent(getActivity(), RelationActivity.class);
            mIntent.putExtra("Selected", txt_relationship.getText().toString());
            mIntent.putExtra("fromProfile", true);
            startActivityForResult(mIntent, 408);
        } else if (id == R.id.iv_edit_mail) {
            Intent mIntent = new Intent(getActivity(), ForgotPasswordActivity.class);
            mIntent.putExtra("fromProfile", true);
            startActivityForResult(mIntent, 408);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        setValues();
        OnProfilePhotoChageListerner inf= (OnProfilePhotoChageListerner) getActivity();
        inf.updateImage();
    }
}
