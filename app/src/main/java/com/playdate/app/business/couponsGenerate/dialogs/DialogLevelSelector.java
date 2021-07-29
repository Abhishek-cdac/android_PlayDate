package com.playdate.app.business.couponsGenerate.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.playdate.app.R;
import com.playdate.app.business.couponsGenerate.CouponGenActivity;

public class DialogLevelSelector extends Dialog {
    ImageView iv_minus;
    ImageView iv_add;
    ImageView iv_cancel;
    Button btn_confirm;
    TextView tv_coins;

    int coin = 0;

    public DialogLevelSelector(@NonNull Context context) {
        super(context, R.style.My_Dialog);
        WindowManager.LayoutParams wlmp = getWindow().getAttributes();
        wlmp.gravity = Gravity.CENTER_HORIZONTAL;
        getWindow().setAttributes(wlmp);
        setTitle(null);
        setCancelable(true);
        setOnCancelListener(null);
        View view = LayoutInflater.from(context).inflate(
                R.layout.dialog_level_selector, null);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(view);

        iv_minus = view.findViewById(R.id.iv_minus);
        iv_add = view.findViewById(R.id.iv_add);
        iv_cancel = view.findViewById(R.id.iv_cancel);
        btn_confirm = view.findViewById(R.id.btn_confirm);
        tv_coins = view.findViewById(R.id.tv_coins);

        iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  dismiss();
            }
        });

        tv_coins.setText(String.valueOf(coin));
        iv_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (coin>0){
                    coin = coin - 1;
                }
                tv_coins.setText(String.valueOf(coin));
            }
        });

        iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coin = coin + 1;
                tv_coins.setText(String.valueOf(coin));
            }
        });
    }
}
