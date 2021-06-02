package com.playdate.app.ui.my_profile_details;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.playdate.app.couple.ui.register.couplebio.CoupleBioActivity;
import com.playdate.app.data.api.GetDataService;
import com.playdate.app.data.api.RetrofitClientInstance;
import com.playdate.app.model.GetProfileDetails;
import com.playdate.app.model.GetProileDetailData;
import com.playdate.app.model.Interest;
import com.playdate.app.model.InterestsMain;
import com.playdate.app.ui.blockuser.BlockUserActivity;
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
import com.playdate.app.util.common.TransparentProgressDialog;
import com.playdate.app.util.customcamera.otalia.CameraActivity;
import com.playdate.app.util.session.SessionPref;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

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
    TextView txt_change_photo;
    ImageView iv_dark_mode;
    ImageView profile_image;
    ImageView iv_reset_pass;
    ImageView iv_edit_bio, iv_edit_couple_bio;
    TextView txt_bio;
    ImageView iv_change_bio_video;
    TextView txt_user_name, logout, txt_username;
    TextView txt_interetsed;
    TextView txt_invite, invite_partner, leave_partner, txt_HowWeMet_detail, txt_change_bio_video;
    TextView txt_upgrade;
    SessionPref pref;
    RelativeLayout saved_rl, create_relation_rl, leave_relation_rl, change_bio_rl, change_howwemet_rl, change_bio_video_rl;

    private GoogleApiClient googleApiClient;
    GoogleSignInClient mGoogleSignInClient;

    private ArrayList<GetProileDetailData> lst_getPostDetail;
    String inviteCode;
    String inviteLink;
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

        callAPI();
        setInIt(view);

        setValues();


        return view;

    }

    private void setInIt(View view) {

        iv_edit_couple_bio = view.findViewById(R.id.iv_edit_couple_bio);
        leave_relation_rl = view.findViewById(R.id.leave_relation_rl);
        change_bio_video_rl = view.findViewById(R.id.change_bio_video_rl);
        txt_change_bio_video = view.findViewById(R.id.txt_change_bio_video);
        txt_HowWeMet_detail = view.findViewById(R.id.txt_howwemet_detail);
        change_howwemet_rl = view.findViewById(R.id.change_howwemet_rl);
        change_bio_rl = view.findViewById(R.id.change_bio_rl);
        leave_partner = view.findViewById(R.id.leave_partner);
        invite_partner = view.findViewById(R.id.invite_partner);
        create_relation_rl = view.findViewById(R.id.create_relation_rl);
        iv_edit_bio = view.findViewById(R.id.iv_edit_bio);
        txt_bio = view.findViewById(R.id.txt_bio_detail);

        iv_reset_pass = view.findViewById(R.id.iv_reset_pass);
        iv_change_bio_video = view.findViewById(R.id.iv_change_bio_video);
        profile_image = view.findViewById(R.id.profile_image);
        txt_interetsed = view.findViewById(R.id.txt_interetsed);
        ImageView iv_edit_username = view.findViewById(R.id.iv_edit_username);
        ImageView iv_interest = view.findViewById(R.id.iv_interest);
        TextView txt_blocked = view.findViewById(R.id.txt_blocked);
        iv_dark_mode = view.findViewById(R.id.iv_dark_mode);
        txt_user_name = view.findViewById(R.id.txt_user_name);
        txt_username = view.findViewById(R.id.txt_username);
        txt_invite = view.findViewById(R.id.txt_invite);
        txt_upgrade = view.findViewById(R.id.txt_upgrade);
        logout = view.findViewById(R.id.logout);
         txt_change_photo = view.findViewById(R.id.txt_change_photo);


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
        txt_blocked.setOnClickListener(this);


        setValues();

        iv_edit_couple_bio.setOnClickListener(this);

        if (pref.getStringVal(SessionPref.LoginUserrelationship).equals("Single")) {
            txt_username.setText("Username");
            txt_change_photo.setText("Change profile photo");
            create_relation_rl.setVisibility(View.VISIBLE);
            invite_partner.setVisibility(View.VISIBLE);
            leave_partner.setVisibility(View.GONE);
            leave_relation_rl.setVisibility(View.GONE);
            change_howwemet_rl.setVisibility(View.GONE);
            txt_HowWeMet_detail.setVisibility(View.GONE);
            txt_bio.setVisibility(View.VISIBLE);
            change_bio_rl.setVisibility(View.VISIBLE);
            txt_change_bio_video.setVisibility(View.VISIBLE);
            change_bio_video_rl.setVisibility(View.VISIBLE);
        } else {
            txt_change_photo.setText("Change couple profile photo");
            txt_username.setText("Couple Username");
            create_relation_rl.setVisibility(View.GONE);
            invite_partner.setVisibility(View.GONE);
            leave_partner.setVisibility(View.VISIBLE);
            leave_relation_rl.setVisibility(View.VISIBLE);
            change_howwemet_rl.setVisibility(View.VISIBLE);
            txt_bio.setVisibility(View.GONE);
            txt_HowWeMet_detail.setVisibility(View.VISIBLE);
            change_bio_rl.setVisibility(View.GONE);
            change_bio_video_rl.setVisibility(View.GONE);
            txt_change_bio_video.setVisibility(View.GONE);
        }
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
        if (id == R.id.txt_blocked ) {
            Intent mIntent = new Intent(getActivity(), BlockUserActivity.class);
            mIntent.putExtra("fromProfile", true);
            startActivityForResult(mIntent, 407);
        }else if (id == R.id.profile_image || id == R.id.txt_change_photo) {
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
        } else if (id == R.id.iv_edit_couple_bio) {
            Intent mIntent = new Intent(getActivity(), CoupleBioActivity.class);
            mIntent.putExtra("fromProfile", true);
            startActivityForResult(mIntent, 410);
        } else if (id == R.id.iv_dark_mode) {
            if (iv_dark_mode.getRotation() == 180) {
                iv_dark_mode.setImageResource(R.drawable.dark_mode);
                iv_dark_mode.setRotation(0);
            } else {
                iv_dark_mode.setImageResource(R.drawable.dark_mode_sel);
                iv_dark_mode.setRotation(180);
            }

        } else if (id == R.id.txt_invite) {

            Intent intent = new Intent(getActivity(), InviteFriendActivity.class);
            intent.putExtra("inviteCode", inviteCode);
            intent.putExtra("inviteLink", inviteLink);
            startActivity(intent);
        } else if (id == R.id.logout) {
            showYesNoDialog();
        } else if (id == R.id.txt_upgrade) {
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

    private void callAPI() {
        SessionPref pref = SessionPref.getInstance(getActivity());

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();

        hashMap.put("userId", pref.getStringVal(SessionPref.LoginUserID));

        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(getActivity());
        pd.show();
//        Toast.makeText(this, ""+pref.getStringVal(SessionPref.LoginUsertoken), Toast.LENGTH_SHORT).show();


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
                        inviteCode = String.valueOf(lst_getPostDetail.get(0).getInviteCode());
                        inviteLink = String.valueOf(lst_getPostDetail.get(0).getInviteLink());
                        Log.e("inviteCode", "" + inviteCode);
                        Log.e("inviteLink", "" + inviteLink);
                    } else {
                        // clsCommon.showDialogMsgfrag(getActivity(), "PlayDate", response.body().getMessage(), "Ok");
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        //   clsCommon.showDialogMsgfrag(getActivity(), "PlayDate", jObjError.getString("message").toString(), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }

                }


            }

            @Override
            public void onFailure(Call<GetProfileDetails> call, Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
