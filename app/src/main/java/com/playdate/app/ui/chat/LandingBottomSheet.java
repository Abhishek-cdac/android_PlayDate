package com.playdate.app.ui.chat;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.playdate.app.R;
import com.playdate.app.ui.interfaces.OnInnerFragmentClicks;
import com.playdate.app.ui.my_profile_details.FragInstaLikeProfile;

public class LandingBottomSheet extends BottomSheetDialogFragment {

    RelativeLayout rl_delete_msg;
    RelativeLayout rl_viewProfile;
    RelativeLayout rl_block;
    RelativeLayout report_comment_rl;
    TextView share;
    ChattingAdapter chattingAdapter;
    ChatAdapter chatAdapter;
    int index;
    String from = "";
    String inviteLink = "Hey, welcome to playDAte";

    public LandingBottomSheet(ChattingAdapter chattingAdapter, int index, String from) {
        this.chattingAdapter = chattingAdapter;
        this.index = index;
        this.from = from;
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
        rl_delete_msg = view.findViewById(R.id.rl_delete_msg);
        rl_viewProfile = view.findViewById(R.id.rl_viewProfile);
        report_comment_rl = view.findViewById(R.id.report_comment_rl);
        rl_block = view.findViewById(R.id.rl_block);
        share = view.findViewById(R.id.share);

        if (from.equals("chat")) {
            report_comment_rl.setVisibility(View.GONE);
            rl_block.setVisibility(View.GONE);
            share.setText("Share");

        }
        rl_delete_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (from.equals("chat")) {
                    chatAdapter.removeFromList(index);
                } else {
                    chattingAdapter.deleteChat(index);
                }

            }
        });

        rl_viewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (from.equals("chat")) {

                    shareTextUrl();
                } else {

                    OnInnerFragmentClicks ref = (OnInnerFragmentClicks) getActivity();
//                ref.loadProfile(lst.get(position).getUserId());
                    ref.ReplaceFragWithStack(new FragInstaLikeProfile());
                    chattingAdapter.dismissSheet();

                }
            }
        });


        return view;
    }

    private void shareTextUrl() {
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        share.putExtra(Intent.EXTRA_TEXT, inviteLink);
        startActivity(Intent.createChooser(share, "PlayDate InviteLink!"));
        chatAdapter.dismissSheet();
    }
}
