package com.playdate.app.ui.my_profile_details;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.playdate.app.R;
import com.playdate.app.data.api.GetDataService;
import com.playdate.app.data.api.RetrofitClientInstance;
import com.playdate.app.model.CommonModel;
import com.playdate.app.model.GetProfileDetails;
import com.playdate.app.model.GetProileDetailData;
import com.playdate.app.ui.chat.ChatMainActivity;
import com.playdate.app.ui.dashboard.OnFriendSelected;
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

public class FragInstaLikeProfileFriends extends Fragment implements View.OnClickListener {

    private ImageView iv_send_request;
    private ImageView iv_chat;
    private ImageView profile_image;
    private TextView txt_bio;
    private TextView txt_login_user;
    private CommonClass clsCommon;
    private TextView txtTotalFriend, txtTotalPost;
    private ArrayList<GetProileDetailData> lst_getPostDetail;
    private boolean fromChat;

    public FragInstaLikeProfileFriends() {
    }


    private boolean isFriend = true;
    private String LoginId;
    private String friendID;

    public FragInstaLikeProfileFriends(boolean isFriend, String LoginId, boolean fromChat) {
        this.isFriend = isFriend;
        this.LoginId = LoginId;
        this.fromChat = fromChat;

    }


    public FragInstaLikeProfileFriends(boolean isFriend, String LoginId, String friendID) {
        this.isFriend = isFriend;
        this.LoginId = LoginId;
        this.friendID = friendID;
    }

    private FragMyUploadMedia mediaFrag;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_insta_profile_other, container, false);

        clsCommon = CommonClass.getInstance();

        InstaPhotosAdapter.isLocked = !isFriend;

        txtTotalFriend = view.findViewById(R.id.friend_count);
        txtTotalPost = view.findViewById(R.id.post_count);
        iv_send_request = view.findViewById(R.id.iv_send_request);
        profile_image = view.findViewById(R.id.profile_image);
        iv_chat = view.findViewById(R.id.iv_chat);
        txt_bio = view.findViewById(R.id.txt_bio);
        txt_login_user = view.findViewById(R.id.txt_login_user);

        try {
            FragmentManager fm = getChildFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            mediaFrag = new FragMyUploadMedia(LoginId);
            ft.add(R.id.fl_fragment, mediaFrag);
            ft.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }


        callAPI(LoginId);
        if (isFriend) {
            iv_send_request.setImageResource(R.drawable.sent_request_sel);
            iv_chat.setImageResource(R.drawable.chat_sel);
        } else {
            iv_send_request.setImageResource(R.drawable.sent_request);
            iv_chat.setImageResource(R.drawable.chat_black);
        }


        iv_send_request.setOnClickListener(this);
        profile_image.setOnClickListener(this);
        iv_chat.setOnClickListener(this);

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if (fromChat) {
                    getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                } else {
                    OnFriendSelected inf = (OnFriendSelected) getActivity();
                    Objects.requireNonNull(inf).onFrinedDataClosed();
                }

                return true;
            }
            return false;
        });
        return view;
    }


    private void callAPI(String loginId) {
        SessionPref pref = SessionPref.getInstance(getActivity());

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();

        hashMap.put("userId", loginId);

        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(getActivity());
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
                            txtTotalFriend.setText(String.valueOf(lst_getPostDetail.get(0).getTotalFriends()));
                            txtTotalPost.setText(String.valueOf(lst_getPostDetail.get(0).getTotalPosts()));

                            try {
                                if (lst_getPostDetail.get(0).getProfilePicPath().contains("http")) {
                                    Picasso.get().load(lst_getPostDetail.get(0).getProfilePicPath())
                                            .placeholder(R.drawable.profile)
                                            .into(profile_image);
                                } else {
                                    Picasso.get().load(BASE_URL_IMAGE + lst_getPostDetail.get(0).getProfilePicPath())
                                            .placeholder(R.drawable.profile)
                                            .into(profile_image);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            txt_login_user.setText(lst_getPostDetail.get(0).getUsername());
                            txt_bio.setText(lst_getPostDetail.get(0).getPersonalBio());
                            videopath = lst_getPostDetail.get(0).getProfileVideoPath();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    } else {
                        clsCommon.showDialogMsgFrag(getActivity(), "PlayDate", response.body().getMessage(), "Ok");
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsgFrag(getActivity(), "PlayDate", jObjError.getString("message"), "Ok");
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


//    @Override
//    public void onTypeChange(int type) {
//    }

    String videopath = "";

    private void callAddFriendRequestApi(String toUserID) {

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("toUserID", toUserID);
        SessionPref pref = SessionPref.getInstance(getActivity());

        Call<CommonModel> call = service.addFriendRequest("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new Callback<CommonModel>() {
            @Override
            public void onResponse(Call<CommonModel> call, Response<CommonModel> response) {
//                pd.cancel();
                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {
//                        clsCommon.showDialogMsgfrag(getActivity(), "PlayDate", "" + response.body().getMessage(), "Ok");
                    } else {
//                        clsCommon.showDialogMsgfrag(getActivity(), "PlayDate", "" + response.body().getMessage(), "Ok");
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsgFrag(getActivity(), "PlayDate", jObjError.getString("message"), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<CommonModel> call, Throwable t) {
                t.printStackTrace();
//                pd.cancel();
                Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callRemoveFriendRequestApi() {

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        SessionPref pref = SessionPref.getInstance(getActivity());
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("friendId", friendID);
        hashMap.put("userId", pref.getStringVal(SessionPref.LoginUserID));


        Call<CommonModel> call = service.removeFriend("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new Callback<CommonModel>() {
            @Override
            public void onResponse(Call<CommonModel> call, Response<CommonModel> response) {
//                pd.cancel();
                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {

//                        clsCommon.showDialogMsgfrag(getActivity(), "PlayDate", "" + response.body().getMessage(), "Ok");
                    } else {
//                        clsCommon.showDialogMsgfrag(getActivity(), "PlayDate", "" + response.body().getMessage(), "Ok");
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsgFrag(getActivity(), "PlayDate", jObjError.getString("message"), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<CommonModel> call, Throwable t) {
                t.printStackTrace();
//                pd.cancel();
                Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showYesNoDialog() {
        new AlertDialog.Builder(getActivity())

                .setInverseBackgroundForced(true)
                .setMessage("Are you sure you want to unfriend " + txt_login_user.getText().toString() + " ?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, id) -> removeFriend())
                .setNegativeButton("No", null)
                .show();
    }

    private void removeFriend() {
        iv_send_request.setImageResource(R.drawable.sent_request);
        iv_chat.setImageResource(R.drawable.chat_black);
        InstaPhotosAdapter.isLocked = true;
        callRemoveFriendRequestApi();
        isFriend = false;
        mediaFrag.setView(0, 0);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.iv_send_request) {
            if (isFriend) {
                showYesNoDialog();

            } else {
                iv_send_request.setImageResource(R.drawable.sent_request_sel);
                callAddFriendRequestApi(LoginId);
            }


//            onTypeChange(0);
        } else if (id == R.id.profile_image) {
            try {
                if (videopath == null) {
                    return;
                } else if (videopath.isEmpty()) {
                    return;
                }
                Intent mIntent = new Intent(getActivity(), ExoPlayerActivity.class);

                if (!videopath.contains("http")) {
                    videopath = BASE_URL_IMAGE + videopath;
                }
                mIntent.putExtra("video", videopath);
                startActivity(mIntent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (id == R.id.iv_chat) {
            if (null != lst_getPostDetail) {
                Intent mIntent = new Intent(getActivity(), ChatMainActivity.class);
                mIntent.putExtra("Name", lst_getPostDetail.get(0).getUsername());
                mIntent.putExtra("Image", lst_getPostDetail.get(0).getProfilePicPath());
                mIntent.putExtra("UserID", lst_getPostDetail.get(0).get_id());
                startActivity(mIntent);
            }

        }
    }
}


