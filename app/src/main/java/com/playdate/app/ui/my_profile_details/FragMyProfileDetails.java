package com.playdate.app.ui.my_profile_details;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.playdate.app.R;
import com.playdate.app.ui.dashboard.OnProfilePhotoChageListerner;
import com.playdate.app.ui.login.LoginActivity;
import com.playdate.app.ui.register.interest.InterestActivity;
import com.playdate.app.ui.register.profile.UploadProfileActivity;
import com.playdate.app.ui.register.username.UserNameActivity;
import com.playdate.app.util.session.SessionPref;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.concurrent.Executor;

import static com.playdate.app.data.api.RetrofitClientInstance.BASE_URL_IMAGE;
import static com.playdate.app.util.session.SessionPref.CompleteProfile;
import static com.playdate.app.util.session.SessionPref.LoginVerified;

public class FragMyProfileDetails extends Fragment implements View.OnClickListener {
    ImageView iv_dark_mode;
    ImageView profile_image;
    ImageView iv_reset_pass;
    TextView txt_user_name, logout;
    SessionPref pref;
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

        iv_reset_pass = view.findViewById(R.id.iv_reset_pass);
        profile_image = view.findViewById(R.id.profile_image);
        ImageView iv_edit_username = view.findViewById(R.id.iv_edit_username);
        ImageView iv_interest = view.findViewById(R.id.iv_interest);
        iv_dark_mode = view.findViewById(R.id.iv_dark_mode);
        txt_user_name = view.findViewById(R.id.txt_user_name);
        logout = view.findViewById(R.id.logout);
        TextView txt_change_photo = view.findViewById(R.id.txt_change_photo);
        profile_image.setOnClickListener(this);
        iv_edit_username.setOnClickListener(this);
        txt_change_photo.setOnClickListener(this);
        iv_edit_username.setOnClickListener(this);
        iv_interest.setOnClickListener(this);
        iv_dark_mode.setOnClickListener(this);
        logout.setOnClickListener(this);

        setValues();

        return view;

    }

    private void setValues() {
        SessionPref pref = SessionPref.getInstance(getActivity());
        txt_user_name.setText(pref.getStringVal(SessionPref.LoginUserusername));

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
        } else if (id == R.id.iv_edit_username) {
            Intent mIntent = new Intent(getActivity(), UserNameActivity.class);
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

        } else if (id == R.id.logout) {

            Log.e("LoginUserSourceType", "" + pref.getStringVal(SessionPref.LoginUserSourceType));
            Toast.makeText(getActivity(), "LogOut", Toast.LENGTH_LONG).show();

//            LoginManager.getInstance().logOut();
//            SessionPref.getInstance(getActivity()).saveBoolKeyVal(LoginVerified, false);
//            SessionPref.logout(getActivity());
//            Intent intent = new Intent(getActivity(), LoginActivity.class);
//            startActivity(intent);
//            getActivity().finish();
//


            if (pref.getStringVal(SessionPref.LoginUserSourceType).equals("Direct")) {
                Toast.makeText(getActivity(), "LogOut", Toast.LENGTH_LONG).show();

                SessionPref.getInstance(getActivity()).saveBoolKeyVal(LoginVerified, false);
                SessionPref.getInstance(getActivity()).saveBoolKeyVal(CompleteProfile, false);
                SessionPref.logout(getActivity());
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            } else if (pref.getStringVal(SessionPref.LoginUserSourceType).equals("Facebook")) {
                Toast.makeText(getActivity(), "LogOut", Toast.LENGTH_LONG).show();

                LoginManager.getInstance().logOut();
                SessionPref.getInstance(getActivity()).saveBoolKeyVal(LoginVerified, false);
                SessionPref.logout(getActivity());
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            } else if (pref.getStringVal(SessionPref.LoginUserSourceType).equals("Google")) {

                signOut();
////                Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(
////                        new ResultCallback<Status>() {
////                            @Override
////                            public void onResult(@NotNull Status status) {
////                                if (status.isSuccess()) {
////                                    Toast.makeText(getActivity(), "LogOut", Toast.LENGTH_LONG).show();
////                                    SessionPref.getInstance(getActivity()).saveBoolKeyVal(LoginVerified, false);
////                                    SessionPref.logout(getActivity());
////                                    Intent intent = new Intent(getActivity(), LoginActivity.class);
////                                    startActivity(intent);
////                                    getActivity().finish();
////
////                                } else {
////                                    Toast.makeText(getActivity(), "Session not close", Toast.LENGTH_LONG).show();
////                                }
////                            }
////                        });
            }

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
