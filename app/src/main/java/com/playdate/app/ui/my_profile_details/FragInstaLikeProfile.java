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
import androidx.fragment.app.Fragment;

import com.playdate.app.R;
import com.playdate.app.data.api.GetDataService;
import com.playdate.app.data.api.RetrofitClientInstance;
import com.playdate.app.model.GetCoupleProfileData;
import com.playdate.app.model.GetCoupleProfileModel;
import com.playdate.app.model.GetProfileDetails;
import com.playdate.app.model.GetProileDetailData;
import com.playdate.app.ui.dialogs.BoosterDialogDM;
import com.playdate.app.ui.my_profile_details.adapters.InstaPhotosAdapter;
import com.playdate.app.ui.playvideo.ExoPlayerActivity;
import com.playdate.app.util.common.CommonClass;
import com.playdate.app.util.common.TransparentProgressDialog;
import com.playdate.app.util.session.SessionPref;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.playdate.app.data.api.RetrofitClientInstance.BASE_URL_IMAGE;

public class FragInstaLikeProfile extends Fragment implements onPhotoClick, View.OnClickListener {
    private ImageView iv_send_request;
    private ImageView iv_chat;
    private ImageView iv_booster;
    private ImageView profile_image, boy_profile_image, girl_profile_image;
    private TextView txt_bio;
    private TextView txt_login_user;
    private CommonClass clsCommon;
    private TextView txtTotalFriend, txtTotalPost;
    private TextView txt_points;
    private ArrayList<GetProileDetailData> lst_getPostDetail;
    private ArrayList<GetCoupleProfileData> lst_getCoupleDetail;

    boolean isBoosterOn = false;

    public FragInstaLikeProfile() {
    }

    private boolean itsMe = true;

    public FragInstaLikeProfile(boolean itsMe) {
        this.itsMe = itsMe;
    }

    private Picasso picasso;
    private SessionPref pref;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_insta_profile, container, false);
        clsCommon = CommonClass.getInstance();
        pref = SessionPref.getInstance(getActivity());
        picasso = Picasso.get();
        girl_profile_image = view.findViewById(R.id.girl_profile_image);
        boy_profile_image = view.findViewById(R.id.boy_profile_image);
        ImageView connection_img = view.findViewById(R.id.connection_img);
        RelativeLayout single_person = view.findViewById(R.id.single_person);
        RelativeLayout couple_rl = view.findViewById(R.id.couple_rl);
        txtTotalFriend = view.findViewById(R.id.friend_count);
        txtTotalPost = view.findViewById(R.id.post_count);
        iv_send_request = view.findViewById(R.id.iv_send_request);
        profile_image = view.findViewById(R.id.profile_image);
        iv_chat = view.findViewById(R.id.iv_chat);
        txt_bio = view.findViewById(R.id.txt_bio);
        txt_login_user = view.findViewById(R.id.txt_login_user);
        iv_booster = view.findViewById(R.id.iv_booster);
        txt_points = view.findViewById(R.id.txt_points);
        TextView header_text = view.findViewById(R.id.header_text);


        if (pref.getBoolVal("isBoosterOn")) {
            iv_booster.setImageResource(R.drawable.booster_select);
        } else {
            iv_booster.setImageResource(R.drawable.booster);
        }
        isBoosterOn = pref.getBoolVal("isBoosterOn");


        iv_booster.setOnClickListener(v -> {
            if (isBoosterOn) {
                iv_booster.setImageResource(R.drawable.booster);
                isBoosterOn = false;
                pref.saveBoolKeyVal("isBoosterOn", isBoosterOn);
            } else {
                new BoosterDialogDM(getActivity()).show();
                iv_booster.setImageResource(R.drawable.booster_select);
                isBoosterOn = true;
                pref.saveBoolKeyVal("isBoosterOn", isBoosterOn);
            }
        });
        if (pref.getStringVal(SessionPref.LoginUserrelationship).equals("Single")) {
            connection_img.setVisibility(View.GONE);
            header_text.setText("About Me");
            single_person.setVisibility(View.VISIBLE);
            iv_send_request.setVisibility(View.VISIBLE);
            iv_chat.setVisibility(View.VISIBLE);
            couple_rl.setVisibility(View.GONE);
        } else {
            callCoupleAPI();
            connection_img.setVisibility(View.VISIBLE);
            header_text.setText("How we met");
            single_person.setVisibility(View.GONE);
            couple_rl.setVisibility(View.VISIBLE);
            iv_send_request.setVisibility(View.GONE);
            iv_chat.setVisibility(View.GONE);
        }
        callAPI();
        if (itsMe) {
            iv_send_request.setVisibility(View.GONE);
            iv_chat.setVisibility(View.GONE);
            InstaPhotosAdapter.isLocked = false;
        } else {
            iv_send_request.setVisibility(View.VISIBLE);
            iv_chat.setVisibility(View.VISIBLE);
        }

        setValue();

        iv_send_request.setOnClickListener(this);
        profile_image.setOnClickListener(this);


        return view;
    }

    private void callCoupleAPI() {


        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("userId", pref.getStringVal(SessionPref.LoginUserID));
        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(getActivity());
        pd.show();
        Call<GetCoupleProfileModel> call = service.getCoupleProfile("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new Callback<GetCoupleProfileModel>() {
            @Override
            public void onResponse(Call<GetCoupleProfileModel> call, Response<GetCoupleProfileModel> response) {
                pd.cancel();
                if (response.code() == 200) {
                    if (response.body().getStatus() == 1) {
                        lst_getCoupleDetail = (ArrayList<GetCoupleProfileData>) response.body().getData();
                        if (lst_getCoupleDetail == null) {
                            lst_getCoupleDetail = new ArrayList<>();
                        }

                        picasso.load( lst_getCoupleDetail.get(0).getProfile1().get(0).getProfilePicPath())
                                .placeholder(R.drawable.profile)
                                .into(boy_profile_image);

                        Log.e("getCoupleBoy", "" + lst_getCoupleDetail.get(0).getProfile1().get(0).getProfilePicPath());
                        Log.e("getCoupleGirl", "" + lst_getCoupleDetail.get(0).getProfile2().get(0).getProfilePicPath());

                        picasso.load( lst_getCoupleDetail.get(0).getProfile2().get(0).getProfilePicPath())
                                .placeholder(R.drawable.profile)
                                .into(girl_profile_image);
                    } else {
                        clsCommon.showDialogMsgfrag(getActivity(), "PlayDate", response.body().getMessage(), "Ok");
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsgfrag(getActivity(), "PlayDate", jObjError.getString("message"), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }

                }


            }

            @Override
            public void onFailure(Call<GetCoupleProfileModel> call, Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setValue() {
        String img = pref.getStringVal(SessionPref.LoginUserprofilePic);
        if (img.contains("http")) {
            picasso.load(img)
                    .into(profile_image);
        } else {
            picasso.load(BASE_URL_IMAGE + img)
                    .into(profile_image);
        }
        txt_login_user.setText(pref.getStringVal(SessionPref.LoginUserusername));
        txt_bio.setText(pref.getStringVal(SessionPref.LoginUserpersonalBio));


    }

    private void callAPI() {

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
                    if (Objects.requireNonNull(response.body()).getStatus() == 1) {
                        try {
                            lst_getPostDetail = (ArrayList<GetProileDetailData>) response.body().getData();
                            if (lst_getPostDetail == null) {
                                lst_getPostDetail = new ArrayList<>();
                            }
                            txtTotalFriend.setText(String.valueOf(lst_getPostDetail.get(0).getTotalFriends()));
                            txtTotalPost.setText(String.valueOf(lst_getPostDetail.get(0).getTotalPosts()));

                            txt_points.setText(lst_getPostDetail.get(0).getAccount().get(0).getCurrentPoints() + " Points");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else {
                        clsCommon.showDialogMsgfrag(getActivity(), "PlayDate", response.body().getMessage(), "Ok");
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(Objects.requireNonNull(response.errorBody()).string());
                        clsCommon.showDialogMsgfrag(getActivity(), "PlayDate", jObjError.getString("message"), "Ok");
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


    @Override
    public void onTypeChange(int type) {

    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.iv_send_request) {
            iv_send_request.setImageResource(R.drawable.sent_request_sel);
            iv_chat.setImageResource(R.drawable.chat_sel);
            InstaPhotosAdapter.isLocked = false;
            onTypeChange(0);
        } else if (id == R.id.profile_image) {
            try {
                Intent mIntent = new Intent(getActivity(), ExoPlayerActivity.class);

                String videopath = pref.getStringVal(SessionPref.LoginUserprofileVideo);


                if (!videopath.contains("http")) {
                    videopath = BASE_URL_IMAGE + videopath;
                }
                mIntent.putExtra("video", videopath);
                startActivity(mIntent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}


interface onPhotoClick {
    void onTypeChange(int type);
}