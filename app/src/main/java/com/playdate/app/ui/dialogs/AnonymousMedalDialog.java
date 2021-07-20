package com.playdate.app.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.playdate.app.R;

public class AnonymousMedalDialog extends Dialog {
    public AnonymousMedalDialog(@NonNull Context context) {
        super(context, R.style.My_Dialog);

        WindowManager.LayoutParams wlmp = getWindow().getAttributes();

        wlmp.gravity = Gravity.CENTER_HORIZONTAL;
        getWindow().setAttributes(wlmp);
        setTitle(null);
        setCancelable(true);
        setOnCancelListener(null);
        View view = LayoutInflater.from(context).inflate(
                R.layout.dialog_perfect_answer_anonymous, null);
        View medal = view.findViewById(R.id.medal);
//        View view = LayoutInflater.from(context).inflate(
//                R.layout.dialog_winner, null);
//        View view = LayoutInflater.from(context).inflate(
//                R.layout.dialog_second, null);
//        View view = LayoutInflater.from(context).inflate(
//                R.layout.dialog_wrong_answer, null);
//        medal.startAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.shake));


        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(view);
    }
}
