package com.playdate.app.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.provider.Settings;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.playdate.app.R;

public class NoInternetDialog extends Dialog {
    ImageView bar;
    public NoInternetDialog(@NonNull Context context) {
        super(context, R.style.My_Dialog);

        WindowManager.LayoutParams wlmp = getWindow().getAttributes();

//        wlmp.gravity = Gravity.CENTER_HORIZONTAL;
//        getWindow().setAttributes(wlmp);
        setTitle(null);
        setCancelable(false);
        setOnCancelListener(null);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_no_internet, null);

        bar= view.findViewById(R.id.bar);
        bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
            }
        });

//        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(view);
    }
}