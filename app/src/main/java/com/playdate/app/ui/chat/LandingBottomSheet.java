package com.playdate.app.ui.chat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.playdate.app.R;
import com.playdate.app.data.api.GetDataService;
import com.playdate.app.data.api.RetrofitClientInstance;
import com.playdate.app.model.CommonModel;
import com.playdate.app.model.LoginResponse;
import com.playdate.app.ui.interfaces.OnInnerFragmentClicks;
import com.playdate.app.ui.my_profile_details.FragInstaLikeProfile;
import com.playdate.app.util.session.SessionPref;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LandingBottomSheet extends BottomSheetDialogFragment {

    private ChattingAdapter chattingAdapter;
    private ChatAdapter chatAdapter;
    private final int index;
    private final String from;
    private String toUserId;
    private String chatId;


    public LandingBottomSheet(ChattingAdapter chattingAdapter, int index, String from, String toUserId, String chatId) {
        this.chattingAdapter = chattingAdapter;
        this.index = index;
        this.from = from;
        this.toUserId = toUserId;
        this.chatId = chatId;
    }

    public LandingBottomSheet(ChatAdapter chatAdapter, int index, String from) {
        this.chatAdapter = chatAdapter;
        this.index = index;
        this.from = from;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_chat_landing_bottomsheet, container, false);
        RelativeLayout rl_delete_msg = view.findViewById(R.id.rl_delete_msg);
        RelativeLayout rl_viewProfile = view.findViewById(R.id.rl_viewProfile);
        RelativeLayout report_comment_rl = view.findViewById(R.id.report_comment_rl);
        RelativeLayout rl_block = view.findViewById(R.id.rl_block);
        TextView report_user = view.findViewById(R.id.report_user);
        TextView share = view.findViewById(R.id.share);

        if (from.equals("chat")) {
            report_comment_rl.setVisibility(View.GONE);
            rl_block.setVisibility(View.GONE);
            share.setText("Share");
        } else {
            report_user.setText("Report User");
        }


        report_comment_rl.setOnClickListener(v -> {
            dismiss();
            Toast.makeText(getActivity(), "User  reported  successfully!", Toast.LENGTH_SHORT).show();
            callBlockUser(toUserId, "Report");
        });

        rl_block.setOnClickListener(v -> {
            dismiss();
            Toast.makeText(getActivity(), "User  blocked  successfully!", Toast.LENGTH_SHORT).show();
            callBlockUser(toUserId, "Block");

        });

        rl_delete_msg.setOnClickListener(v -> {
            if (from.equals("chat")) {
                chatAdapter.removeFromList(index);
            } else {
                callDeleteChatRoom(chatId);
            }
            dismiss();
        });

        rl_viewProfile.setOnClickListener(v -> {
            if (from.equals("chat")) {
                chatAdapter.shareChat(index);
//                shareTextUrl();
            } else {
                OnInnerFragmentClicks ref = (OnInnerFragmentClicks) getActivity();
                ref.ReplaceFrag(new FragInstaLikeProfile());
//                ref.loadProfile(toUserId);
//                chattingAdapter.loadProfile(toUserId);
                chattingAdapter.dismissSheet();
            }
        });


        return view;
    }
    private void callDeleteChatRoom(String chatId) {
        SessionPref pref = SessionPref.getInstance(getActivity());
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("userId", pref.getStringVal(SessionPref.LoginUserID));
        hashMap.put("chatId", chatId);

        Call<CommonModel> call = service.deleteChatRoom("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new Callback<CommonModel>() {
            @Override
            public void onResponse(Call<CommonModel> call, Response<CommonModel> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus() == 1) {
                        chattingAdapter.deleteChat(index);
                        chattingAdapter.Refresh();

                    } else {

                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<CommonModel> call, Throwable t) {
                t.printStackTrace();
            }
        });


    }

    private void callBlockUser(String toUserId, String action) {
        chattingAdapter.updateList(index);
        SessionPref pref = SessionPref.getInstance(getActivity());

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("userId", pref.getStringVal(SessionPref.LoginUserID));
        hashMap.put("action", action);//Block or Report
        hashMap.put("toUserId", toUserId);

        Call<LoginResponse> call = service.addUserReportBlock("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new retrofit2.Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
//                pd.cancel();
                if (response.code() == 200) {
                    if (response.body().getStatus() == 1) {
                        Log.e("successful", "" + toUserId + " " + action);

                    } else {
                    }
                } else {

                }
            }


            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    private void shareTextUrl() {
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        String inviteLink = "Hey, welcome to playDAte";
        share.putExtra(Intent.EXTRA_TEXT, inviteLink);
        startActivity(Intent.createChooser(share, "PlayDate InviteLink!"));
        chatAdapter.dismissSheet();
    }
}
