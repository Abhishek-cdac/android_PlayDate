package com.playdate.app.ui.my_profile_details;

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
import androidx.fragment.app.Fragment;

import com.playdate.app.R;
import com.playdate.app.data.api.GetDataService;
import com.playdate.app.data.api.RetrofitClientInstance;
import com.playdate.app.model.GetProfileDetails;
import com.playdate.app.model.GetProileDetailData;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.playdate.app.data.api.RetrofitClientInstance.BASE_URL_IMAGE;

public class FragInstaLikeProfile extends Fragment implements onPhotoClick, View.OnClickListener {
    private ImageView iv_send_request;
    private ImageView iv_chat;
    private ImageView profile_image;
    private TextView txt_bio;
    private TextView txt_login_user;
    private CommonClass clsCommon;
    private TextView txtTotalFriend, txtTotalPost;
    private ArrayList<GetProileDetailData> lst_getPostDetail;

    public FragInstaLikeProfile() {
    }

    boolean itsMe = true;

    public FragInstaLikeProfile(boolean itsMe) {
        this.itsMe = itsMe;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_insta_profile, container, false);

        clsCommon = CommonClass.getInstance();

        txtTotalFriend = view.findViewById(R.id.friend_count);
        txtTotalPost = view.findViewById(R.id.post_count);
        iv_send_request = view.findViewById(R.id.iv_send_request);
        profile_image = view.findViewById(R.id.profile_image);
        iv_chat = view.findViewById(R.id.iv_chat);
        txt_bio = view.findViewById(R.id.txt_bio);
        txt_login_user = view.findViewById(R.id.txt_login_user);
        callAPI();
        if (itsMe) {
            iv_send_request.setVisibility(View.GONE);
            iv_chat.setVisibility(View.GONE);
            InstaPhotosAdapter.isLocked=false;
        } else {
            iv_send_request.setVisibility(View.VISIBLE);
            iv_chat.setVisibility(View.VISIBLE);
        }

        setValue();

        iv_send_request.setOnClickListener(this);
        profile_image.setOnClickListener(this);

        return view;
    }

    private void setValue() {
        SessionPref pref = SessionPref.getInstance(getActivity());
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
        txt_login_user.setText(pref.getStringVal(SessionPref.LoginUserusername));
        txt_bio.setText(pref.getStringVal(SessionPref.LoginUserpersonalBio));


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
                        txtTotalFriend.setText(String.valueOf(lst_getPostDetail.get(0).getTotalFriends()));
                        txtTotalPost.setText(String.valueOf(lst_getPostDetail.get(0).getTotalPosts()));
//                        onTypeChange(0);

                    } else {
                        clsCommon.showDialogMsgfrag(getActivity(), "PlayDate", response.body().getMessage(), "Ok");
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsgfrag(getActivity(), "PlayDate", jObjError.getString("message").toString(), "Ok");
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

        if (type == 1) {
            if (InstaPhotosAdapter.isLocked) {
                return;
            }
//            recycler_photos.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
//            ArrayList<SocialFeed> lst = new ArrayList<>();
//            lst.add(new SocialFeed("Olivia251", 1548, false, 0, USER, "https://images.saymedia-content.com/.image/t_share/MTc0MDkwNjUxNDc2OTYwODM0/5-instagram-models-you-should-be-following.png", "https://i.pinimg.com/originals/09/31/b5/0931b5399d9f1a3afe4417ee83eff961.jpg"));
//            lst.add(new SocialFeed("AvaSophia", 1542, false, 0, USER, "https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/4p3a7420-copy-1524689604.jpg", "https://i.pinimg.com/originals/09/31/b5/0931b5399d9f1a3afe4417ee83eff961.jpg"));
//            lst.add(new SocialFeed("Isabella", 4854, false, 0, USER, "https://images.saymedia-content.com/.image/t_share/MTc1MDE0NzI4MTg2OTk2NTIz/5-instagram-models-you-should-be-following.png", "https://i.pinimg.com/originals/09/31/b5/0931b5399d9f1a3afe4417ee83eff961.jpg"));
//
//            lst.add(new SocialFeed("Olivia251", 1548, false, 0, USER, "https://images.saymedia-content.com/.image/t_share/MTc0MDkwNjUxNDc2OTYwODM0/5-instagram-models-you-should-be-following.png", "https://i.pinimg.com/originals/09/31/b5/0931b5399d9f1a3afe4417ee83eff961.jpg"));
//            lst.add(new SocialFeed("AvaSophia", 1542, false, 0, USER, "https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/4p3a7420-copy-1524689604.jpg", "https://i.pinimg.com/originals/09/31/b5/0931b5399d9f1a3afe4417ee83eff961.jpg"));
//            lst.add(new SocialFeed("Isabella", 4854, false, 0, USER, "https://images.saymedia-content.com/.image/t_share/MTc1MDE0NzI4MTg2OTk2NTIz/5-instagram-models-you-should-be-following.png", "https://i.pinimg.com/originals/09/31/b5/0931b5399d9f1a3afe4417ee83eff961.jpg"));
//            lst.add(new SocialFeed("Isabella", 4854, false, 0, USER, "https://blog.ainfluencer.com/wp-content/uploads/2021/01/Cinta-Laura-868x1024.jpg", "https://i.pinimg.com/originals/09/31/b5/0931b5399d9f1a3afe4417ee83eff961.jpg"));
//
//
//            SocialFeedNormal adapter = new SocialFeedNormal(lst);
//            recycler_photos.setAdapter(adapter);
        } else {
//            recycler_photos.setLayoutManager(new GridLayoutManager(getActivity(), 3));
//            InstaPhotosAdapter adapter = new InstaPhotosAdapter(this);
//            recycler_photos.setAdapter(adapter);
        }


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
                SessionPref pref = SessionPref.getInstance(getActivity());

                String videopath = pref.getStringVal(SessionPref.LoginUserprofileVideo);


                if (videopath.contains("http")) {

                } else {
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