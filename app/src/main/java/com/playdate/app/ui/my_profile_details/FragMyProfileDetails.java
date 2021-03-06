package com.playdate.app.ui.my_profile_details;

import static com.playdate.app.data.api.RetrofitClientInstance.BASE_URL_IMAGE;
import static com.playdate.app.util.session.SessionPref.LoginVerified;

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
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.playdate.app.R;
import com.playdate.app.couple.ui.register.couplebio.CoupleBioActivity;
import com.playdate.app.data.api.GetDataService;
import com.playdate.app.data.api.RetrofitClientInstance;
import com.playdate.app.model.CommonModel;
import com.playdate.app.model.FriendsListModel;
import com.playdate.app.model.GetCoupleProfileData;
import com.playdate.app.model.GetCoupleProfileModel;
import com.playdate.app.model.GetProfileDetails;
import com.playdate.app.model.GetProileDetailData;
import com.playdate.app.model.MatchListUser;
import com.playdate.app.ui.blockuser.BlockUserActivity;
import com.playdate.app.ui.dashboard.OnProfilePhotoChangeListerner;
import com.playdate.app.ui.dialogs.FriendDialog;
import com.playdate.app.ui.forgot_password.ForgotPasswordActivity;
import com.playdate.app.ui.interfaces.OnInnerFragmentClicks;
import com.playdate.app.ui.invite.InviteFriendActivity;
import com.playdate.app.ui.login.LoginActivity;
import com.playdate.app.ui.register.bio.BioActivity;
import com.playdate.app.ui.register.interest.InterestActivity;
import com.playdate.app.ui.register.profile.UploadProfileActivity;
import com.playdate.app.ui.register.username.UserNameActivity;
import com.playdate.app.util.common.CommonClass;
import com.playdate.app.util.common.TransparentProgressDialog;
import com.playdate.app.util.customcamera.otalia.CameraActivity;
import com.playdate.app.util.session.SessionPref;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragMyProfileDetails extends Fragment implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {
    public FragMyProfileDetails() {
    }

    private FragmentActivity mContext;
    private ImageView iv_dark_mode;
    private ImageView profile_image;
    private TextView txt_bio;
    private TextView txt_user_name;
    private TextView txt_interetsed;
    private SessionPref pref;
    private GoogleSignInClient mGoogleSignInClient;

    private ArrayList<GetProileDetailData> lst_getPostDetail;
    private String inviteCode;
    private String inviteLink;
    private CommonClass clsCommon;
    boolean state = false;
    private Picasso picasso;
    private RelativeLayout change_bio_video_rl;
    private TextView txt_change_bio_video;
    private TextView txt_HowWeMet_detail;
    private RelativeLayout change_howwemet_rl;
    private RelativeLayout change_bio_rl;
    private TextView leave_partner;
    private TextView invite_partner;
    private RelativeLayout create_relation_rl;
    private RelativeLayout leave_relation_rl;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_my_details, container, false);
        mContext = getActivity();
        pref = SessionPref.getInstance(mContext);
        clsCommon = CommonClass.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestId()
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(mContext, gso);

        callAPI();
        setInIt(view);
        setDarkModeSwitch();
        setValues();

        try {
            if (!pref.getStringVal(SessionPref.LoginUserrelationship).equals("Single")) {
                callCoupleAPI();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return view;

    }

    private ArrayList<GetCoupleProfileData> lst_getCoupleDetail;

    private void callCoupleAPI() {


        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("userId", pref.getStringVal(SessionPref.LoginUserID));
        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(mContext);
        pd.show();
        Call<GetCoupleProfileModel> call = service.getCoupleProfile("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new Callback<GetCoupleProfileModel>() {
            @Override
            public void onResponse(Call<GetCoupleProfileModel> call, Response<GetCoupleProfileModel> response) {
                pd.cancel();
                if (response.code() == 200) {
                    if (Objects.requireNonNull(response.body()).getStatus() == 1) {
                        lst_getCoupleDetail = (ArrayList<GetCoupleProfileData>) response.body().getData();
                        if (lst_getCoupleDetail == null) {
                            lst_getCoupleDetail = new ArrayList<>();
                        }
                        pref.saveStringKeyVal("LoginUserCoupleBio", lst_getCoupleDetail.get(0).getBio());
                        txt_HowWeMet_detail.setText(pref.getStringVal("LoginUserCoupleBio"));

                    }
                }


            }

            @Override
            public void onFailure(Call<GetCoupleProfileModel> call, Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(mContext, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void setInIt(View view) {
        picasso = Picasso.get();
        ImageView iv_edit_couple_bio = view.findViewById(R.id.iv_edit_couple_bio);
        leave_relation_rl = view.findViewById(R.id.leave_relation_rl);
        change_bio_video_rl = view.findViewById(R.id.change_bio_video_rl);
        txt_change_bio_video = view.findViewById(R.id.txt_change_bio_video);
        txt_HowWeMet_detail = view.findViewById(R.id.txt_howwemet_detail);
        change_howwemet_rl = view.findViewById(R.id.change_howwemet_rl);
        change_bio_rl = view.findViewById(R.id.change_bio_rl);
        leave_partner = view.findViewById(R.id.leave_partner);
        invite_partner = view.findViewById(R.id.invite_partner);
        create_relation_rl = view.findViewById(R.id.create_relation_rl);
        ImageView iv_edit_bio = view.findViewById(R.id.iv_edit_bio);
        txt_bio = view.findViewById(R.id.txt_bio_detail);

        ImageView iv_reset_pass = view.findViewById(R.id.iv_reset_pass);
        ImageView iv_change_bio_video = view.findViewById(R.id.iv_change_bio_video);
        profile_image = view.findViewById(R.id.profile_image);
        txt_interetsed = view.findViewById(R.id.txt_interetsed);
        ImageView iv_edit_username = view.findViewById(R.id.iv_edit_username);
        ImageView iv_interest = view.findViewById(R.id.iv_interest);
        TextView txt_blocked = view.findViewById(R.id.txt_blocked);
        iv_dark_mode = view.findViewById(R.id.iv_dark_mode);
        txt_user_name = view.findViewById(R.id.txt_user_name);
        TextView txt_username = view.findViewById(R.id.txt_username);
        TextView txt_invite = view.findViewById(R.id.txt_invite);
        TextView txt_upgrade = view.findViewById(R.id.txt_upgrade);
        TextView logout = view.findViewById(R.id.logout);
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
        txt_blocked.setOnClickListener(this);
        create_relation_rl.setOnClickListener(this);
        leave_relation_rl.setOnClickListener(this);

        setValues();
        iv_edit_couple_bio.setOnClickListener(this);
        txt_change_photo.setText(R.string.str_change_profile_photo);
        txt_username.setText("Username");

    }


    private void setValues() {
        try {
            txt_user_name.setText(pref.getStringVal(SessionPref.LoginUserusername));
            txt_bio.setText(pref.getStringVal(SessionPref.LoginUserpersonalBio));
            txt_HowWeMet_detail.setText(pref.getStringVal("LoginUserCoupleBio"));


            String img = pref.getStringVal(SessionPref.LoginUserprofilePic);
            if (img.contains("http")) {
                picasso.load(img).placeholder(R.drawable.profile)
                        .into(profile_image);
            } else {
                picasso.load(BASE_URL_IMAGE + img).placeholder(R.drawable.profile)
                        .into(profile_image);
            }
            txt_interetsed.setText(pref.getStringVal(SessionPref.LoginUserinterested));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.txt_blocked) {
            Intent mIntent = new Intent(mContext, BlockUserActivity.class);
            mIntent.putExtra("fromProfile", true);
            startActivityForResult(mIntent, 407);
        } else if (id == R.id.profile_image || id == R.id.txt_change_photo) {
            Intent mIntent = new Intent(mContext, UploadProfileActivity.class);
            mIntent.putExtra("fromProfile", true);
            startActivityForResult(mIntent, 407);
        } else if (id == R.id.iv_edit_username) {
            Intent mIntent = new Intent(mContext, UserNameActivity.class);
            mIntent.putExtra("fromProfile", true);
            startActivityForResult(mIntent, 408);
        } else if (id == R.id.iv_change_bio_video) {
            Intent mIntent = new Intent(mContext, CameraActivity.class);
            mIntent.putExtra("fromProfile", true);
            startActivityForResult(mIntent, 408);
        } else if (id == R.id.iv_reset_pass) {
            Intent mIntent = new Intent(mContext, ForgotPasswordActivity.class);
            mIntent.putExtra("fromProfile", true);
            startActivityForResult(mIntent, 408);
        } else if (id == R.id.iv_interest) {
            Intent mIntent = new Intent(mContext, InterestActivity.class);
            mIntent.putExtra("fromProfile", true);
            startActivityForResult(mIntent, 409);
        } else if (id == R.id.iv_edit_couple_bio) {
            if (lst_getCoupleDetail == null) {
                callCoupleAPI();
            } else {
                Intent mIntent = new Intent(mContext, CoupleBioActivity.class);
                mIntent.putExtra("fromProfile", true);
                mIntent.putExtra("coupleId", lst_getCoupleDetail.get(0).getCoupleId());
                startActivityForResult(mIntent, 410);
            }

        } else if (id == R.id.iv_dark_mode) {
            state = iv_dark_mode.getRotation() != 180;
            imageChange(state);
        } else if (id == R.id.txt_invite) {

            Intent intent = new Intent(mContext, InviteFriendActivity.class);
            intent.putExtra("inviteCode", inviteCode);
            intent.putExtra("inviteLink", inviteLink);
            startActivity(intent);

        } else if (id == R.id.logout) {
            showYesNoDialog();
        } else if (id == R.id.txt_upgrade) {
            OnInnerFragmentClicks frag = (OnInnerFragmentClicks) mContext;
            assert frag != null;
            frag.ReplaceFrag(new FragUpgradePremiun());
        } else if (id == R.id.iv_edit_bio) {
            Intent mIntent = new Intent(mContext, BioActivity.class);
            mIntent.putExtra("fromProfile", true);
            startActivityForResult(mIntent, 409);
        } else if (id == R.id.create_relation_rl) {
            callGetUserSuggestionAPI();
        } else if (id == R.id.leave_relation_rl) {
            callLeaveRelationshipApi();
        }
    }

    private void callLeaveRelationshipApi() {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        String userId = pref.getStringVal(SessionPref.LoginUserID);
        hashMap.put("userId", userId);
        if (null != lst_getCoupleDetail) {
            hashMap.put("requestId", lst_getCoupleDetail.get(0).getCoupleId());
        } else {
            return;
        }
        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(mContext);
        pd.show();
        Call<CommonModel> call = service.leaveRelationship("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new Callback<CommonModel>() {
            @Override
            public void onResponse(Call<CommonModel> call, Response<CommonModel> response) {
                pd.cancel();
                if (response.code() == 200) {
                    if (Objects.requireNonNull(response.body()).getStatus() == 1) {
                        showYesNoDialogRelation();
                    } else {
                        clsCommon.showDialogMsgFrag(mContext, "PlayDate", response.body().getMessage(), "Ok");
                    }

                } else {
                    try {
                        JSONObject jObjError = new JSONObject(Objects.requireNonNull(response.errorBody()).string());
                        clsCommon.showDialogMsgFrag(mContext, "PlayDate", jObjError.getString("message"), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(mContext, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<CommonModel> call, Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(mContext, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void callGetUserSuggestionAPI() {

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        // hashMap.put("filter", "");
        hashMap.put("limit", "100");
        hashMap.put("pageNo", "1");//Hardcode
        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(mContext);
        pd.show();

        Call<FriendsListModel> call = service.getFriendsList("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new Callback<FriendsListModel>() {
            @Override
            public void onResponse(Call<FriendsListModel> call, Response<FriendsListModel> response) {
                pd.cancel();
                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {
                        ArrayList<MatchListUser> lstUserSuggestions = response.body().getUsers();
                        if (lstUserSuggestions == null) {
                            lstUserSuggestions = new ArrayList<>();
                        }
                        if (lstUserSuggestions.size() > 0)
                            showFriendsDialog(lstUserSuggestions);
                    }
                }

            }

            @Override
            public void onFailure(Call<FriendsListModel> call, Throwable t) {
                t.printStackTrace();
                pd.cancel();
            }
        });
    }

    void showFriendsDialog(ArrayList<MatchListUser> lstUserSuggestions) {
        FriendDialog dialog = new FriendDialog(mContext, lstUserSuggestions, true, inviteLink);
        dialog.show();
        dialog.setOnDismissListener(dialog1 -> {

            for (int i = 0; i < lstUserSuggestions.size(); i++) {
                if (lstUserSuggestions.get(i).isSelected()) {
                    callAPICreateRelation(lstUserSuggestions.get(i));
                    break;
                }
            }


        });
    }

    private void callAPICreateRelation(MatchListUser user) {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("userId", pref.getStringVal(SessionPref.LoginUserID));
        hashMap.put("toUserId", user.getUserId());
        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(mContext);
        pd.show();

        Call<CommonModel> call = service.createRelationship("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new Callback<CommonModel>() {
            @Override
            public void onResponse(Call<CommonModel> call, Response<CommonModel> response) {
                pd.cancel();
                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {
                        clsCommon.showDialogMsgFrag(mContext, "PlayDate", response.body().getMessage(), "Ok");
                        Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    } else if (response.body().getStatus() == 0) {
                        clsCommon.showDialogMsgFrag(mContext, "PlayDate", response.body().getMessage(), "Ok");
                        Toast.makeText(mContext, response.message(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(mContext, "" + jObjError.getString("message"), Toast.LENGTH_SHORT).show();
                        clsCommon.showDialogMsgFrag(mContext, "PlayDate", jObjError.getString("message"), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(mContext, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<CommonModel> call, Throwable t) {
                t.printStackTrace();
                pd.cancel();
            }
        });
    }

    void setDarkModeSwitch() {
        if (pref.getBoolVal(SessionPref.DARKMODE)) {
            iv_dark_mode.setImageResource(R.drawable.dark_mode_sel);
            iv_dark_mode.setRotation(180);
        } else {
            iv_dark_mode.setImageResource(R.drawable.dark_mode);
            iv_dark_mode.setRotation(0);
        }
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

    private void showYesNoDialog() {
        new AlertDialog.Builder(mContext)

                .setInverseBackgroundForced(true)
                .setMessage("Are you sure you want to logout from PlayDate?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, id) -> outFromApp())
                .setNegativeButton("No", null)
                .show();
    }

    private void showYesNoDialogRelation() {
        new AlertDialog.Builder(mContext)
                .setInverseBackgroundForced(true)
                .setMessage("Are you sure you want to leave this Relationship?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, id) -> callAPI())
                .setNegativeButton("No", null)
                .show();
    }

    private void outFromApp() {
        switch (pref.getStringVal(SessionPref.LoginUserSourceType)) {
            case "Direct": {
                SessionPref.logout(mContext);
                Intent intent = new Intent(mContext, LoginActivity.class);
                startActivity(intent);
                mContext.finish();
                break;
            }
            case "Facebook": {

                LoginManager.getInstance().logOut();
                pref.saveBoolKeyVal(LoginVerified, false);
                SessionPref.logout(mContext);
                Intent intent = new Intent(mContext, LoginActivity.class);
                startActivity(intent);
                mContext.finish();
                break;
            }
            case "Google":

                signOut();
                break;
        }
    }

    private void signOut() {


        mGoogleSignInClient.signOut()
                .addOnCompleteListener(mContext, task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(mContext, "LogOut", Toast.LENGTH_LONG).show();
                        pref.saveBoolKeyVal(LoginVerified, false);
                        SessionPref.logout(mContext);
                        Intent intent = new Intent(mContext, LoginActivity.class);
                        startActivity(intent);
                        mContext.finish();
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        setValues();
        OnProfilePhotoChangeListerner inf = (OnProfilePhotoChangeListerner) mContext;
        assert inf != null;
        inf.updateImage();
    }

    private void callAPI() {

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();

        hashMap.put("userId", pref.getStringVal(SessionPref.LoginUserID));

        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(mContext);
        pd.show();


        Call<GetProfileDetails> call = service.getProfileDetails("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new Callback<GetProfileDetails>() {
            @Override
            public void onResponse(Call<GetProfileDetails> call, Response<GetProfileDetails> response) {
                pd.cancel();
                if (response.code() == 200) {
                    if (Objects.requireNonNull(response.body()).getStatus() == 1) {
                        try {
                            lst_getPostDetail = (ArrayList<GetProileDetailData>) response.body().getData();
                            if (lst_getPostDetail == null) {
                                lst_getPostDetail = new ArrayList<>();
                            }
                            inviteCode = String.valueOf(lst_getPostDetail.get(0).getInviteCode());
                            inviteLink = String.valueOf(lst_getPostDetail.get(0).getInviteLink());
                            pref.saveStringKeyVal(SessionPref.LoginUserrelationship, lst_getPostDetail.get(0).getRelationship());


                            if (pref.getStringVal(SessionPref.LoginUserrelationship).equals("Single")) {
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

                        } catch (Exception e) {
                            e.printStackTrace();
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


    @Override
    public void onConnectionFailed(@NonNull @NotNull ConnectionResult connectionResult) {

    }
}
