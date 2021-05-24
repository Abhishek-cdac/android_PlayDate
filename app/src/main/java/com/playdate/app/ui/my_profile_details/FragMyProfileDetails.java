package com.playdate.app.ui.my_profile_details;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.playdate.app.R;
import com.playdate.app.data.api.GetDataService;
import com.playdate.app.data.api.RetrofitClientInstance;
import com.playdate.app.model.Interest;
import com.playdate.app.model.InterestsMain;
import com.playdate.app.ui.dashboard.OnProfilePhotoChageListerner;
import com.playdate.app.ui.date.games.FragTimesUp1;
import com.playdate.app.ui.forgot_password.ForgotPasswordActivity;
import com.playdate.app.ui.interfaces.OnInnerFragmentClicks;
import com.playdate.app.ui.invite.InviteFriendActivity;
import com.playdate.app.ui.login.LoginActivity;
import com.playdate.app.ui.register.bio.BioActivity;
import com.playdate.app.ui.register.interest.InterestActivity;
import com.playdate.app.ui.register.profile.UploadProfileActivity;
import com.playdate.app.ui.register.username.UserNameActivity;
import com.playdate.app.util.customcamera.otalia.CameraActivity;
import com.playdate.app.util.session.SessionPref;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.playdate.app.data.api.RetrofitClientInstance.BASE_URL_IMAGE;
import static com.playdate.app.util.session.SessionPref.LoginVerified;

public class FragMyProfileDetails extends Fragment implements View.OnClickListener {
    public FragMyProfileDetails() {
    }

    ImageView iv_dark_mode;
    ImageView profile_image;
    ImageView iv_reset_pass;
    ImageView iv_edit_bio;
    TextView txt_bio;
    ImageView iv_change_bio_video;
    TextView txt_user_name, logout;
    TextView txt_interetsed;
    TextView txt_invite;
    TextView txt_upgrade;
    SessionPref pref;
    RelativeLayout saved_rl;

    private GoogleApiClient googleApiClient;
    GoogleSignInClient mGoogleSignInClient;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_my_details, container, false);
        pref = SessionPref.getInstance(getActivity());
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestId()
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);


        iv_edit_bio = view.findViewById(R.id.iv_edit_bio);
        txt_bio = view.findViewById(R.id.txt_bio_detail);
        iv_reset_pass = view.findViewById(R.id.iv_reset_pass);
        iv_change_bio_video = view.findViewById(R.id.iv_change_bio_video);
        profile_image = view.findViewById(R.id.profile_image);
        txt_interetsed = view.findViewById(R.id.txt_interetsed);
        ImageView iv_edit_username = view.findViewById(R.id.iv_edit_username);
        ImageView iv_interest = view.findViewById(R.id.iv_interest);
        iv_dark_mode = view.findViewById(R.id.iv_dark_mode);
        txt_user_name = view.findViewById(R.id.txt_user_name);
        txt_invite = view.findViewById(R.id.txt_invite);
        txt_upgrade = view.findViewById(R.id.txt_upgrade);
        logout = view.findViewById(R.id.logout);
        TextView txt_change_photo = view.findViewById(R.id.txt_change_photo);

        profile_image.setOnClickListener(this);
        iv_change_bio_video.setOnClickListener(this);
        iv_edit_username.setOnClickListener(this);
        txt_change_photo.setOnClickListener(this);
        iv_edit_username.setOnClickListener(this);
        iv_interest.setOnClickListener(this);
        iv_dark_mode.setOnClickListener(this);
        logout.setOnClickListener(this);
        iv_reset_pass.setOnClickListener(this);
        txt_invite.setOnClickListener(this);
        txt_upgrade.setOnClickListener(this);
        iv_edit_bio.setOnClickListener(this);


        setValues();


        return view;

    }

    ArrayList<Interest> lst_interest;

//    private void getInterest() {
//
//
//        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
//        Map<String, String> hashMap = new HashMap<>();
//        hashMap.put("limit", "50");// format 1990-08-12
//        hashMap.put("pageNo", "1");// format 1990-08-12
////        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(this);
////        pd.show();
//        SessionPref pref = SessionPref.getInstance(getActivity());
////        Toast.makeText(this, ""+pref.getStringVal(SessionPref.LoginUsertoken), Toast.LENGTH_SHORT).show();
//
//
//        Call<InterestsMain> call = service.interested("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
//        call.enqueue(new Callback<InterestsMain>() {
//            @Override
//            public void onResponse(Call<InterestsMain> call, Response<InterestsMain> response) {
////                pd.cancel();
//                if (response.code() == 200) {
//                    assert response.body() != null;
//                    if (response.body().getStatus() == 1) {
//                        String finalInterest = "";
//                        lst_interest = response.body().getLst();
//                        if (lst_interest == null) {
//                            lst_interest = new ArrayList<>();
//                        }
//
//
//
//
//
//
//                        for (int i = 0; i < lst_interest.size(); i++) {
//                            for (String s : interestList) {
//                                if (s.trim().equals(lst_interest.get(i).get_id())) {
//                                    if (finalInterest.isEmpty()) {
//                                        finalInterest = lst_interest.get(i).getName();
//                                    } else {
//                                        finalInterest = finalInterest + "," + lst_interest.get(i).getName();
//                                    }
//                                }
//                            }
//                        }
//
//
//
//
//                    } else {
//
//                    }
//                } else {
//
//
//                }
//
//
//            }
//
//            @Override
//            public void onFailure(Call<InterestsMain> call, Throwable t) {
//                t.printStackTrace();
//            }
//        });
//
//
//    }

    private void setValues() {
        try {
            SessionPref pref = SessionPref.getInstance(getActivity());
            txt_user_name.setText(pref.getStringVal(SessionPref.LoginUserusername));
            txt_bio.setText(pref.getStringVal(SessionPref.LoginUserpersonalBio));

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
            txt_interetsed.setText(pref.getStringVal(SessionPref.LoginUserinterested));
//            getInterest();
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
        } else if (id == R.id.iv_edit_username) {
            Intent mIntent = new Intent(getActivity(), UserNameActivity.class);
            mIntent.putExtra("fromProfile", true);
            startActivityForResult(mIntent, 408);
        } else if (id == R.id.iv_change_bio_video) {
            Intent mIntent = new Intent(getActivity(), CameraActivity.class);
            mIntent.putExtra("fromProfile", true);
            startActivityForResult(mIntent, 408);
        } else if (id == R.id.iv_reset_pass) {
            Intent mIntent = new Intent(getActivity(), ForgotPasswordActivity.class);
            mIntent.putExtra("fromProfile", true);
            startActivityForResult(mIntent, 408);
        } else if (id == R.id.iv_interest) {
            Intent mIntent = new Intent(getActivity(), InterestActivity.class);
            mIntent.putExtra("fromProfile", true);
            startActivityForResult(mIntent, 409);
        } else if (id == R.id.iv_dark_mode) {
            if (iv_dark_mode.getRotation() == 180) {
                iv_dark_mode.setImageResource(R.drawable.dark_mode);
                iv_dark_mode.setRotation(0);
            } else {
                iv_dark_mode.setImageResource(R.drawable.dark_mode_sel);
                iv_dark_mode.setRotation(180);
            }

        } else if (id == R.id.txt_invite) {
            startActivity(new Intent(getActivity(), InviteFriendActivity.class));
        } else if (id == R.id.logout) {
            showYesNoDialog();
        }else if (id == R.id.txt_upgrade) {
            OnInnerFragmentClicks frag = (OnInnerFragmentClicks) getActivity();
            frag.ReplaceFrag(new FragUpgradePremiun());
        } else if (id == R.id.iv_edit_bio) {
            Intent mIntent = new Intent(getActivity(), BioActivity.class);
            mIntent.putExtra("fromProfile", true);
            startActivityForResult(mIntent, 409);
        }


    }

    private void showYesNoDialog() {
        new AlertDialog.Builder(getActivity())

                .setInverseBackgroundForced(true)
                .setMessage("Are you sure you want to logout from app?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, id) -> outFromApp())
                .setNegativeButton("No", null)
                .show();
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
                SessionPref.getInstance(getActivity()).saveBoolKeyVal(LoginVerified, false);
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
                .addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getActivity(), "LogOut", Toast.LENGTH_LONG).show();
                        SessionPref.getInstance(getActivity()).saveBoolKeyVal(LoginVerified, false);
                        SessionPref.logout(getActivity());
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        setValues();
        OnProfilePhotoChageListerner inf = (OnProfilePhotoChageListerner) getActivity();
        inf.updateImage();
    }

}
