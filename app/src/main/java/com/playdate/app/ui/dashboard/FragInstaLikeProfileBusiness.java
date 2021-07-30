package com.playdate.app.ui.dashboard;

import static com.playdate.app.data.api.RetrofitClientInstance.BASE_URL_IMAGE;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.playdate.app.R;
import com.playdate.app.data.api.GetDataService;
import com.playdate.app.data.api.RetrofitClientInstance;
import com.playdate.app.model.GetProfileDetails;
import com.playdate.app.model.GetProileDetailData;
import com.playdate.app.ui.chat.ChatMainActivity;
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

public class FragInstaLikeProfileBusiness extends Fragment implements View.OnClickListener {

    private ImageView profile_image;
    private TextView txt_bio;
    private TextView txt_login_user;
    private CommonClass clsCommon;
    private TextView txtTotalFriend, txtTotalPost;
    //    private TextView txt_points;
    private ArrayList<GetProileDetailData> lst_getPostDetail;
    private Picasso picasso;
    private SessionPref pref;

    RatingBar ratingbar;

    public FragInstaLikeProfileBusiness() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_insta_profile_business, container, false);
        clsCommon = CommonClass.getInstance();
        pref = SessionPref.getInstance(getActivity());
        picasso = Picasso.get();
        ratingbar = view.findViewById(R.id.ratingBar);
        ratingbar.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> Toast.makeText(getActivity(), "Rating: " +
                (int) rating, Toast.LENGTH_SHORT).show());
        txtTotalFriend = view.findViewById(R.id.friend_count);
        txtTotalPost = view.findViewById(R.id.post_count);
        profile_image = view.findViewById(R.id.profile_image);
        txt_bio = view.findViewById(R.id.txt_bio);
        txt_login_user = view.findViewById(R.id.txt_login_user);
//        txt_points = view.findViewById(R.id.txt_points);


        callAPI();
        setValue();

        profile_image.setOnClickListener(this);

        return view;
    }


    private void setValue() {
        String img = pref.getStringVal(SessionPref.LoginUserprofilePic);
        if (img.contains("http")) {
            picasso.load(img).placeholder(R.drawable.profile)
                    .into(profile_image);
        } else {
            picasso.load(BASE_URL_IMAGE + img).placeholder(R.drawable.profile)
                    .into(profile_image);
        }
        txt_login_user.setText(pref.getStringVal(SessionPref.LoginUserusername));


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
            public void onResponse(@NonNull Call<GetProfileDetails> call, @NonNull Response<GetProfileDetails> response) {
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


                            txt_bio.setText(lst_getPostDetail.get(0).getPersonalBio());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else {
                        clsCommon.showDialogMsgFrag(getActivity(), "PlayDate", response.body().getMessage(), "Ok");
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(Objects.requireNonNull(response.errorBody()).string());
                        clsCommon.showDialogMsgFrag(getActivity(), "PlayDate", jObjError.getString("message"), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }

                }


            }

            @Override
            public void onFailure(@NonNull Call<GetProfileDetails> call, @NonNull Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }





    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.iv_send_request) {
            InstaPhotosAdapter.isLocked = false;
//            onTypeChange(0);
        } else if (id == R.id.profile_image) {
            try {
                Intent mIntent = new Intent(getActivity(), ExoPlayerActivity.class);

                String videoPath = pref.getStringVal(SessionPref.LoginUserprofileVideo);


                if (!videoPath.contains("http")) {
                    videoPath = BASE_URL_IMAGE + videoPath;
                }
                mIntent.putExtra("video", videoPath);
                startActivity(mIntent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (id == R.id.iv_chat) {
            Intent mIntent = new Intent(getActivity(), ChatMainActivity.class);
            mIntent.putExtra("Name", "");
            mIntent.putExtra("Image", "");
            mIntent.putExtra("UserID", "");
            startActivity(mIntent);
        }
    }
}


