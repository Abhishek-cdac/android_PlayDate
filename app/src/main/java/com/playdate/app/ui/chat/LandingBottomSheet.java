package com.playdate.app.ui.chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.playdate.app.R;
import com.playdate.app.ui.interfaces.OnInnerFragmentClicks;
import com.playdate.app.ui.my_profile_details.FragInstaLikeProfile;

public class LandingBottomSheet extends BottomSheetDialogFragment {

    RelativeLayout rl_delete_msg;
    RelativeLayout rl_viewProfile;
    ChattingAdapter chattingAdapter;
    int index;

    public LandingBottomSheet(ChattingAdapter chattingAdapter, int index) {
        this.chattingAdapter = chattingAdapter;
        this.index = index;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_chat_landing_bottomsheet, container, false);
        rl_delete_msg = view.findViewById(R.id.rl_delete_msg);
        rl_viewProfile = view.findViewById(R.id.rl_viewProfile);

        rl_delete_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chattingAdapter.deleteChat(index);

            }
        });

        rl_viewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnInnerFragmentClicks ref = (OnInnerFragmentClicks) getActivity();
//                ref.loadProfile(lst.get(position).getUserId());
                ref.ReplaceFragWithStack(new FragInstaLikeProfile());
                chattingAdapter.dismissSheet();

            }
        });


        return view;
    }
}
