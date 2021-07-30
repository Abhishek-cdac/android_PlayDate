package com.playdate.app.business.couponsGenerate.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.playdate.app.R;

public class DialogCouponCreated extends Dialog {

    public DialogCouponCreated(@NonNull Context context) {
        super(context, R.style.My_Dialog);
        WindowManager.LayoutParams wlmp = getWindow().getAttributes();

        wlmp.gravity = Gravity.CENTER_HORIZONTAL;
        getWindow().setAttributes(wlmp);
        setTitle(null);
        setCancelable(true);
        setOnCancelListener(null);
        View view = LayoutInflater.from(context).inflate(
                R.layout.activity_coupon_created, null);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(view);
        ImageView iv_close = view.findViewById(R.id.iv_close);
        iv_close.setOnClickListener(view1 -> dismiss());
    }
}

