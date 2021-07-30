package com.playdate.app.ui.anonymous_question;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.playdate.app.R;
import com.playdate.app.data.api.GetDataService;
import com.playdate.app.data.api.RetrofitClientInstance;
import com.playdate.app.model.LoginResponse;
import com.playdate.app.ui.social.adapter.SocialFeedAdapter;
import com.playdate.app.ui.social.model.PostDetails;
import com.playdate.app.util.session.SessionPref;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Response;

public class CommentBottomSheet extends BottomSheetDialogFragment {

    private final boolean notification;
    private final PostDetails postDetails;
    private final SocialFeedAdapter socialFeedAdapter;
    private final int index;

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
        RelativeLayout rl_block = view.findViewById(R.id.rl_block);
        ImageView iv_delete_post = view.findViewById(R.id.iv_delete_post);

        if (notification) {
            switch_on_off.setChecked(true);
            text_on_off.setText(R.string.str_turn_post_off);
        } else {
            switch_on_off.setChecked(false);
            text_on_off.setText(R.string.str_turn_post_on);
        }
        SessionPref pref = SessionPref.getInstance(getActivity());
        if (postDetails.getUserId().equals(pref.getStringVal(SessionPref.LoginUserID))) {
            rl_delete.setVisibility(View.VISIBLE);
            rl_block.setVisibility(View.GONE);
        } else {
            rl_block.setVisibility(View.VISIBLE);
        }

        iv_delete_post.setOnClickListener(v -> callAPIDeletePost());
        iv_block.setOnClickListener(v -> callBlockUser(postDetails.getLstpostby().get(0).getUserId()));
        switch_on_off.setOnCheckedChangeListener((buttonView, isChecked) -> {
            String status;
            if (isChecked) {
                status = "On";
            } else {
                status = "Off";

            }
            callAPI(postDetails.getPostId(), status);
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
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {


            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
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


        Call<LoginResponse> call = service.addUserReportBlock("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new retrofit2.Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                if (response.code() == 200) {
                    if (Objects.requireNonNull(response.body()).getStatus() == 1) {
                        socialFeedAdapter.refreshPage();
                        dismiss();

                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
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


        Call<LoginResponse> call = service.addPostLikeUnlike("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new retrofit2.Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                if (response.code() == 200) {
                    if (Objects.requireNonNull(response.body()).getStatus() == 1) {
                        if (notification) {
                            postDetails.setNotifyStatus("Off");
                        } else {
                            postDetails.setNotifyStatus("On");
                        }
                        socialFeedAdapter.notifyDataSetChanged();
                        dismiss();

                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
