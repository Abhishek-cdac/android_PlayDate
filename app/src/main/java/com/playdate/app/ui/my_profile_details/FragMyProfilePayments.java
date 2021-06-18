package com.playdate.app.ui.my_profile_details;

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
import com.playdate.app.util.session.SessionPref;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.playdate.app.data.api.RetrofitClientInstance.BASE_URL_IMAGE;


public class FragMyProfilePayments extends Fragment implements View.OnClickListener {
    public FragMyProfilePayments() {
    }
    private TextView email;
    private TextView txt_phone;
    private TextView txt_gender;
    private TextView DOB;
    private TextView txt_relationship;
    private TextView interestin;
    private CircleImageView profile_image;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.frag_my_details_payments, container, false);

//        Button btn_add_payment = view.findViewById(R.id.btn_add_payment);
//        ConstraintLayout cl_page = view.findViewById(R.id.cl_page);
//
//        int height = new CommonClass().getScreenHeight(getActivity());
//
//
//        int m1= (int) getResources().getDimension(R.dimen._15sdp);
//        int m2= (int) getResources().getDimension(R.dimen._10sdp);
//        int m3= (int) getResources().getDimension(R.dimen._20sdp);
//        int m4= (int) getResources().getDimension(R.dimen._20sdp);
//        int m5= (int) getResources().getDimension(R.dimen._20sdp);
//        int m6= (int) getResources().getDimension(R.dimen._75sdp);
//
//        cl_page.getLayoutParams().height=height-(m1+m2+m3+m4+m5+m6);


//        btn_add_payment.setOnClickListener(v -> {
//            OnInnerFragmentClicks frag = (OnInnerFragmentClicks) getActivity();
//            frag.ReplaceFrag(new NewPaymentMethod());
//        });

//        PaymentsAdapter adapter = new PaymentsAdapter();
//        RecyclerView recycler_payment = view.findViewById(R.id.recycler_payment);
//        recycler_payment.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
//        recycler_payment.setAdapter(adapter);


        View view = inflater.inflate(R.layout.frag_my_details_payments, container, false);
        SessionPref pref = SessionPref.getInstance(getActivity());
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
        if (pref.getStringVal(SessionPref.LoginUserrelationship).equals("Single")) {

            txt_change_photo.setText("Change profile photo");
            txt_relationship.setText("Single");
        } else {
            txt_change_photo.setText("Change couple profile photo");
            txt_relationship.setText("In a relationship");

        }

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
        Picasso picasso = Picasso.get();
        String img = pref.getStringVal(SessionPref.LoginUserprofilePic);
        if (img.contains("http")) {
            picasso.load(img)
                    .into(profile_image);
        } else {
            picasso.load(BASE_URL_IMAGE + img)
                    .into(profile_image);
        }
    }

    @Override
    public void onClick(View v) {

    }




}