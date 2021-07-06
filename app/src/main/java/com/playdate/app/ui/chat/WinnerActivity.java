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

//    private TextView txt_level;
//    private String rank;

    public WinnerActivity(@NonNull Context context, String rank) {
        super(context, R.style.My_Dialog);
//        this.rank = rank;
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
//        txt_level = view.findViewById(R.id.text_level);
        TextView txt_points = view.findViewById(R.id.txt_points);
        ImageView iv_close = view.findViewById(R.id.iv_close);
        ImageView center = view.findViewById(R.id.center);


        if (rank.equals("1")) {
            txt_points.setText("+100");
            center.setVisibility(View.VISIBLE);
            img_medal.setImageResource(R.drawable.medal);
            txt_winner.setText("Winner!");
            txt_ans.setText("You answered first!");
        } else if (rank.equals("2")) {
            txt_points.setText("+50");
            img_medal.setImageResource(R.drawable.medal_second);
            txt_winner.setText("Not Bad!");
            txt_ans.setText("You answered second!");
        } else {
            txt_points.setText("0");
            img_medal.setImageResource(R.drawable.brokenheart);
            txt_winner.setText("Nice Try!");
            txt_ans.setText("You answered wrong!");
        }
        iv_close.setOnClickListener(view1 -> dismiss());
    }

}

