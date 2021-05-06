package com.playdate.app.util.common;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.playdate.app.R;



public class TransparentProgressDialog extends Dialog {


    public static TransparentProgressDialog dialog;

    public static TransparentProgressDialog getInstance(Context mContext) {
        if (dialog == null) {
            return new TransparentProgressDialog(mContext);
        }
        return dialog;
    }

    public TransparentProgressDialog(Context context) {
        super(context, R.style.My_Dialog);

        WindowManager.LayoutParams wlmp = getWindow().getAttributes();

        wlmp.gravity = Gravity.CENTER_HORIZONTAL;
        getWindow().setAttributes(wlmp);
        setTitle(null);
        setCancelable(false);
        setOnCancelListener(null);
        View view = LayoutInflater.from(context).inflate(
                R.layout.progress_dialog, null);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(view);
    }
}