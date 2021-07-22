package com.playdate.app.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.playdate.app.R;
import com.playdate.app.ui.login.OnUserTypeSelected;


public class UserTypeDialog extends Dialog {
    int selectedUserType = -1;

    public UserTypeDialog(Context context) {
        super(context, R.style.My_Dialog);

        WindowManager.LayoutParams wlmp = getWindow().getAttributes();

        wlmp.gravity = Gravity.CENTER_HORIZONTAL;
        getWindow().setAttributes(wlmp);
        setTitle(null);
        setCancelable(true);
        setOnCancelListener(null);
        View view = LayoutInflater.from(context).inflate(
                R.layout.dialog_user_type, null);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(view);
        ImageView iv_next = view.findViewById(R.id.iv_next);
        Button btn_single = view.findViewById(R.id.btn_single);
        Button btn_taken = view.findViewById(R.id.btn_taken);


        btn_single.setOnClickListener(view12 -> {
            selectedUserType = 0;
            btn_taken.setBackground(context.getDrawable(R.drawable.normal_btn_back));
            btn_single.setBackground(context.getDrawable(R.drawable.selected_btn_back));
            iv_next.setVisibility(View.VISIBLE);
        });

        btn_taken.setOnClickListener(view1 -> {
            selectedUserType = 1;
            btn_taken.setBackground(context.getDrawable(R.drawable.selected_btn_back));
            btn_single.setBackground(context.getDrawable(R.drawable.normal_btn_back));
            iv_next.setVisibility(View.VISIBLE);
        });

        iv_next.setOnClickListener(view13 -> {
            OnUserTypeSelected inf = (OnUserTypeSelected) context;
            inf.nextCheck(selectedUserType);
            dismiss();
        });


    }


}

