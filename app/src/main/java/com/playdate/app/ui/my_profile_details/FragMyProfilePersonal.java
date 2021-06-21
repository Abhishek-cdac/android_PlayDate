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
import com.playdate.app.data.api.GetDataService;
import com.playdate.app.data.api.RetrofitClientInstance;
import com.playdate.app.model.GetProfileDetails;
import com.playdate.app.model.GetProileDetailData;
import com.playdate.app.ui.dashboard.OnProfilePhotoChageListerner;
import com.playdate.app.ui.forgot_password.ForgotPasswordActivity;
import com.playdate.app.ui.register.age_verification.AgeVerifiationActivity;
import com.playdate.app.ui.register.gender.GenderSelActivity;
import com.playdate.app.ui.register.interestin.InterestActivity;
import com.playdate.app.ui.register.profile.UploadProfileActivity;
import com.playdate.app.ui.register.relationship.RelationActivity;
import com.playdate.app.util.common.TransparentProgressDialog;
import com.playdate.app.util.session.SessionPref;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.playdate.app.data.api.RetrofitClientInstance.BASE_URL_IMAGE;

public class FragMyProfilePersonal extends Fragment implements View.OnClickListener {
    private TextView email;
    private TextView txt_phone;
    private TextView txt_gender;
    private TextView DOB;
    private TextView txt_relationship;
    private TextView interestin;
    private CircleImageView profile_image;
    private SessionPref pref;
    private ArrayList<GetProileDetailData> lst_getPostDetail;

    public FragMyProfilePersonal() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_my_personal, container, false);
        pref = SessionPref.getInstance(getActivity());
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
        txt_change_photo.setText("Change profile photo");

        setValues();
        callAPI();
        return view;

    }

    private void callAPI() {

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();

        hashMap.put("userId", pref.getStringVal(SessionPref.LoginUserID));

        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(getActivity());
        pd.show();


        Call<GetProfileDetails> call = service.getProfileDetails("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new Callback<GetProfileDetails>() {
            @Override
            public void onResponse(Call<GetProfileDetails> call, Response<GetProfileDetails> response) {
                pd.cancel();
                if (response.code() == 200) {
                    if (response.body().getStatus() == 1) {
                        lst_getPostDetail = (ArrayList<GetProileDetailData>) response.body().getData();
                        if (lst_getPostDetail == null) {
                            lst_getPostDetail = new ArrayList<>();
                        }
                        pref.saveStringKeyVal(SessionPref.LoginUserrelationship, lst_getPostDetail.get(0).getRelationship());
                        if (pref.getStringVal(SessionPref.LoginUserrelationship).equals("Single")) {
                            txt_relationship.setText("Single");
                        } else {
                            txt_relationship.setText("In a relationship");
                        }
                    }
                }


            }

            @Override
            public void onFailure(Call<GetProfileDetails> call, Throwable t) {
                t.printStackTrace();
                pd.cancel();
            }
        });
    }

    private void setValues() {
        try {
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
            Picasso picasso = Picasso.get();
            String img = pref.getStringVal(SessionPref.LoginUserprofilePic);
            if (img.contains("http")) {
                picasso.load(img)
                        .into(profile_image);
            } else {
                picasso.load(BASE_URL_IMAGE + img)
                        .into(profile_image);
            }
        } catch (Exception e) {
            e.printStackTrace();
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
            mIntent.putExtra("CurrentDOB", DOB.getText().toString());
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
        try {
            setValues();
            OnProfilePhotoChageListerner inf = (OnProfilePhotoChageListerner) getActivity();
            inf.updateImage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
