package com.playdate.app.ui.anonymous_question;

import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.playdate.app.R;
import com.playdate.app.data.api.GetDataService;
import com.playdate.app.data.api.RetrofitClientInstance;
import com.playdate.app.model.LoginResponse;
import com.playdate.app.ui.interfaces.OnInnerFragmentClicks;
import com.playdate.app.ui.social.FragSocialFeed;
import com.playdate.app.ui.social.adapter.SocialFeedAdapter;
import com.playdate.app.ui.social.model.PostDetails;
import com.playdate.app.ui.social.model.PostHistory;
import com.playdate.app.util.session.SessionPref;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentBottomSheet extends BottomSheetDialogFragment {
    boolean notification;
    PostDetails postDetails;
    SocialFeedAdapter socialFeedAdapter;
    int index;

    public CommentBottomSheet(boolean notification, PostDetails postDetails, SocialFeedAdapter socialFeedAdapter, int adapterPosition) {
        this.notification = notification;
        this.postDetails = postDetails;
        this.socialFeedAdapter = socialFeedAdapter;
        this.index = adapterPosition;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_comment_bootom_sheet, container, false);
        SwitchCompat switch_on_off = view.findViewById(R.id.switch_on_off);
        TextView text_on_off = view.findViewById(R.id.text_on_off);
        ImageView iv_block = view.findViewById(R.id.iv_block);
        RelativeLayout rl_delete = view.findViewById(R.id.rl_delete);
        ImageView iv_delete_post = view.findViewById(R.id.iv_delete_post);

        if (notification) {
            switch_on_off.setChecked(true);
            text_on_off.setText("Turn Post Notification OFF");
        } else {
            switch_on_off.setChecked(false);
            text_on_off.setText("Turn Post Notification ON");
        }
        SessionPref pref = SessionPref.getInstance(getActivity());
        if (postDetails.getUserId().equals(pref.getStringVal(SessionPref.LoginUserID))) {
            rl_delete.setVisibility(View.VISIBLE);
        }
        iv_delete_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callAPIDeletePost();
            }
        });
        iv_block.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                callBlockUser(postDetails.getLstpostby().get(0).getUserId());
            }
        });
        switch_on_off.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String status = "On";
                if (isChecked) {
                    status = "On";
                } else {
                    status = "Off";

                }
                callAPI(postDetails.getPostId(), status);
            }
        });
        return view;
    }

    private void callAPIDeletePost() {
        SessionPref pref = SessionPref.getInstance(getActivity());
        socialFeedAdapter.DeleteItem(index);
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("userId", pref.getStringVal(SessionPref.LoginUserID));
        hashMap.put("postId", postDetails.getPostId());

        Call<LoginResponse> call = service.deletePost("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        dismiss();
        call.enqueue(new retrofit2.Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
//                pd.cancel();
                if (response.code() == 200) {
                    if (response.body().getStatus() == 1) {



                    } else {
                    }
                } else {

                }


            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                t.printStackTrace();
//                pd.cancel();
//                Toast.makeText(BioActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void callBlockUser(String toUserId) {
        SessionPref pref = SessionPref.getInstance(getActivity());

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("userId", pref.getStringVal(SessionPref.LoginUserID));
        hashMap.put("action", "Block");//Block or Report
        hashMap.put("toUserId", toUserId);


//        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(mContext);
//        pd.show();

        Call<LoginResponse> call = service.addUserReportBlock("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new retrofit2.Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
//                pd.cancel();
                if (response.code() == 200) {
                    if (response.body().getStatus() == 1) {
//                        socialFeedAdapter.notifyDataSetChanged();

                        dismiss();

                    } else {
                    }
                } else {

                }


            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                t.printStackTrace();
//                pd.cancel();
//                Toast.makeText(BioActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });

    }



    private void callAPI(String postId, String Status) {
        SessionPref pref = SessionPref.getInstance(getActivity());

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("userId", pref.getStringVal(SessionPref.LoginUserID));
        hashMap.put("postId", postId);
        hashMap.put("status", Status);


//        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(mContext);
//        pd.show();

        Call<LoginResponse> call = service.addPostLikeUnlike("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new retrofit2.Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
//                pd.cancel();
                if (response.code() == 200) {
                    if (response.body().getStatus() == 1) {
                        if (notification) {
                            postDetails.setNotifyStatus("Off");
                        } else {
                            postDetails.setNotifyStatus("On");
                        }
                        socialFeedAdapter.notifyDataSetChanged();
                        dismiss();

                    } else {
                    }
                } else {

                }


            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                t.printStackTrace();
//                pd.cancel();
//                Toast.makeText(BioActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
