package com.playdate.app.ui.chat;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.playdate.app.R;

public class WinnerActivity extends Dialog {

    public WinnerActivity(@NonNull Context context, String rank, String points) {
        super(context, R.style.My_Dialog);
        WindowManager.LayoutParams wlmp = getWindow().getAttributes();

        wlmp.gravity = Gravity.CENTER_HORIZONTAL;
        getWindow().setAttributes(wlmp);
        setTitle(null);
        setCancelable(true);
        setOnCancelListener(null);
        View view = LayoutInflater.from(context).inflate(
                R.layout.activity_winner, null);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(view);

        ImageView img_medal = view.findViewById(R.id.img_medal);
        TextView txt_winner = view.findViewById(R.id.text_winner);
        TextView txt_ans = view.findViewById(R.id.text_ans);
        TextView txt_points = view.findViewById(R.id.txt_points);
        ImageView iv_close = view.findViewById(R.id.iv_close);
        ImageView center = view.findViewById(R.id.center);


        if (rank.equals("1")) {
            txt_points.setText(("+" + points));
            center.setVisibility(View.VISIBLE);
            img_medal.setImageResource(R.drawable.medal);
            txt_winner.setText(R.string.str_winner);
            txt_ans.setText(R.string.str_you_win);
        } else if (rank.equals("2")) {
            txt_points.setText(("+" + points));
            img_medal.setImageResource(R.drawable.medal_second);
            txt_winner.setText(R.string.str_not_bad);
            txt_ans.setText(R.string.str_you_second);
        } else {
            txt_points.setText(points);
            img_medal.setImageResource(R.drawable.brokenheart);
            txt_winner.setText(R.string.str_nice_try);
            txt_ans.setText(R.string.str_you_ans);
        }
        iv_close.setOnClickListener(view1 -> dismiss());
    }

}

