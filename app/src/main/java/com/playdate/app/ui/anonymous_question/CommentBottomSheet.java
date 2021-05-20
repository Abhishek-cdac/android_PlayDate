package com.playdate.app.ui.anonymous_question;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.playdate.app.R;

public class CommentBottomSheet extends BottomSheetDialogFragment {

    ImageView notificationOn, blockUser, reportComment;
    Switch postOnSwitch;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_comment_bootom_sheet, container, false);

        notificationOn = view.findViewById(R.id.notificationOn);
        blockUser = view.findViewById(R.id.block_user);
        reportComment = view.findViewById(R.id.report_comment);
        postOnSwitch = view.findViewById(R.id.post_on_switch);


        return view;
    }
}
