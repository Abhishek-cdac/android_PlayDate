package com.playdate.app.business.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.playdate.app.R;
import com.playdate.app.business.businessbio.BusinessBioActivity;
import com.playdate.app.data.api.GetDataService;
import com.playdate.app.data.api.RetrofitClientInstance;
import com.playdate.app.model.GetProfileDetails;
import com.playdate.app.model.GetProileDetailData;
import com.playdate.app.ui.dashboard.OnProfilePhotoChageListerner;
import com.playdate.app.ui.forgot_password.ForgotPasswordActivity;
import com.playdate.app.ui.login.LoginActivity;
import com.playdate.app.ui.register.age_verification.AgeVerifiationActivity;
import com.playdate.app.ui.register.bio.BioActivity;
import com.playdate.app.ui.register.gender.GenderSelActivity;
import com.playdate.app.ui.register.interestin.InterestActivity;
import com.playdate.app.ui.register.profile.UploadProfileActivity;
import com.playdate.app.ui.register.relationship.RelationActivity;
import com.playdate.app.util.common.TransparentProgressDialog;
import com.playdate.app.util.session.SessionPref;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.playdate.app.data.api.RetrofitClientInstance.BASE_URL_IMAGE;
import static com.playdate.app.util.session.SessionPref.LoginVerified;

public class FragBusinessProfile extends Fragment implements View.OnClickListener {
    public FragBusinessProfile() {
    }


    private TextView email;
    private TextView txt_phone;
//    private TextView txt_gender;
    private TextView DOB;
    private TextView txt_user_name;
//    private TextView interestin;
    private CircleImageView profile_image;
    private SessionPref pref;
    private ArrayList<GetProileDetailData> lst_getPostDetail;
    String mobile;
    private ImageView iv_dark_mode;
    boolean state = false;
    private GoogleSignInClient mGoogleSignInClient;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lay_business_profile, container, false);
        pref = SessionPref.getInstance(getActivity());
        email = view.findViewById(R.id.email);
        txt_phone = view.findViewById(R.id.txt_phone);
//        txt_gender = view.findViewById(R.id.txt_gender);
        DOB = view.findViewById(R.id.DOB);
        txt_user_name = view.findViewById(R.id.txt_user_name);
//        txt_relationship = view.findViewById(R.id.txt_relationship);
//        interestin = view.findViewById(R.id.interestin);
        profile_image = view.findViewById(R.id.profile_image);
        iv_dark_mode = view.findViewById(R.id.iv_dark_mode);
        ImageView iv_edit_mail = view.findViewById(R.id.iv_edit_mail);
        ImageView iv_dob = view.findViewById(R.id.iv_dob);
        ImageView iv_reset_pass = view.findViewById(R.id.iv_reset_pass);
        ImageView iv_edit_bio = view.findViewById(R.id.iv_edit_bio);
        TextView logout = view.findViewById(R.id.logout);
        iv_edit_bio.setOnClickListener(this);
        logout.setOnClickListener(this);
//        ImageView iv_relationship = view.findViewById(R.id.iv_relationship);
//        ImageView iv_sex_orientation = view.findViewById(R.id.iv_sex_orientation);
        TextView txt_change_photo = view.findViewById(R.id.txt_change_photo);
//        ImageView iv_gender = view.findViewById(R.id.iv_gender);
        profile_image.setOnClickListener(this);
//        iv_gender.setOnClickListener(this);
//        iv_sex_orientation.setOnClickListener(this);
        iv_dob.setOnClickListener(this);
        iv_edit_mail.setOnClickListener(this);
//        iv_relationship.setOnClickListener(this);
        txt_change_photo.setOnClickListener(this);
        txt_change_photo.setText("Change profile photo");

        iv_dark_mode.setOnClickListener(this);
        iv_reset_pass.setOnClickListener(this);
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
    private void imageChange(boolean state) {
        if (state) {

            iv_dark_mode.setImageResource(R.drawable.dark_mode_sel);
            iv_dark_mode.setRotation(180);
            pref.saveBoolKeyVal(SessionPref.DARKMODE, true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        } else {
            iv_dark_mode.setImageResource(R.drawable.dark_mode);
            iv_dark_mode.setRotation(0);
            pref.saveBoolKeyVal(SessionPref.DARKMODE, false);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        }
    }
    private void setValues() {
        try {
            SessionPref pref = SessionPref.getInstance(getActivity());
            email.setText(pref.getStringVal(SessionPref.LoginUseremail));
            txt_user_name.setText(pref.getStringVal(SessionPref.LoginUserusername));
            if (mobile != null) ;
            mobile = pref.getStringVal(SessionPref.LoginUserphoneNo);
            String temp;
            temp = mobile.substring(0, 3);
            String temp1;
            temp1 = mobile.substring(3, 6);
            String temp2;
            temp2 = mobile.substring(6, 10);
            txt_phone.setText(temp + "-" + temp1 + "-" + temp2);
            //txt_gender.setText(pref.getStringVal(SessionPref.LoginUsergender));
            String[] s = pref.getStringVal(SessionPref.LoginUserbirthDate).split("T");
            DOB.setText(s[0]);
//            txt_relationship.setText(pref.getStringVal(SessionPref.LoginUserrelationship));
//            interestin.setText(pref.getStringVal(SessionPref.LoginUserinterestedIn));
            Picasso picasso = Picasso.get();
            String img = pref.getStringVal(SessionPref.LoginUserprofilePic);
            if (img.contains("http")) {
                picasso.load(img)
                        .into(profile_image);
            } else {
                picasso.load(BASE_URL_IMAGE + img)
                        .into(profile_image);
            }


            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestId()
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build();
            mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
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
        } else if (id == R.id.iv_dob) {
            Intent mIntent = new Intent(getActivity(), AgeVerifiationActivity.class);
            mIntent.putExtra("CurrentDOB", DOB.getText().toString());
            mIntent.putExtra("fromProfile", true);
            startActivityForResult(mIntent, 408);
        }  else if (id == R.id.iv_edit_mail) {
            Intent mIntent = new Intent(getActivity(), ForgotPasswordActivity.class);
            mIntent.putExtra("fromProfile", true);
            startActivityForResult(mIntent, 408);
        } else if (id == R.id.iv_dark_mode) {


            if (iv_dark_mode.getRotation() == 180) {
                state = false;
                imageChange(state);

            } else {
                state = true;
                imageChange(state);
            }

        } else if (id == R.id.iv_reset_pass) {
            Intent mIntent = new Intent(getActivity(), ForgotPasswordActivity.class);
            mIntent.putExtra("fromProfile", true);
            startActivityForResult(mIntent, 408);
        } else if (id == R.id.iv_edit_bio) {
            Intent mIntent = new Intent(getActivity(), BusinessBioActivity.class);
            mIntent.putExtra("fromProfile", true);
            startActivityForResult(mIntent, 409);
        } else if (id == R.id.logout) {
            showYesNoDialog();
        }
    }

    private void outFromApp() {
        switch (pref.getStringVal(SessionPref.LoginUserSourceType)) {
            case "Direct": {
                SessionPref.logout(getActivity());
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
                break;
            }
            case "Facebook": {

                LoginManager.getInstance().logOut();
                pref.saveBoolKeyVal(LoginVerified, false);
                SessionPref.logout(getActivity());
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
                break;
            }
            case "Google":

                signOut();
                break;
        }
    }
    private void signOut() {


        mGoogleSignInClient.signOut()
                .addOnCompleteListener(getActivity(), task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getActivity(), "LogOut", Toast.LENGTH_LONG).show();
                        pref.saveBoolKeyVal(LoginVerified, false);
                        SessionPref.logout(getActivity());
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                });
    }
    private void showYesNoDialog() {
        new AlertDialog.Builder(getActivity())

                .setInverseBackgroundForced(true)
                .setMessage("Are you sure you want to logout from PlayDate?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, id) -> outFromApp())
                .setNegativeButton("No", null)
                .show();
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
