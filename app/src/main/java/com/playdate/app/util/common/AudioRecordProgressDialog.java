package com.playdate.app.util.common;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.playdate.app.R;
import com.playdate.app.ui.chat.ChatMainActivity;

public class AudioRecordProgressDialog extends Dialog {


    public static AudioRecordProgressDialog dialog;

    public static AudioRecordProgressDialog getInstance(Context mContext) {
        if (dialog == null) {
            return new AudioRecordProgressDialog(mContext);
        }
        return dialog;
    }

    public AudioRecordProgressDialog(@NonNull Context context) {
        super(context, R.style.My_Dialog);

        WindowManager.LayoutParams wlmp = getWindow().getAttributes();

        wlmp.gravity = Gravity.CENTER_HORIZONTAL;
        getWindow().setAttributes(wlmp);
        setTitle(null);
        setCancelable(false);
        setOnCancelListener(null);
        View view = LayoutInflater.from(context).inflate(
                R.layout.audio_record_dialog, null);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(view);

    }
}
