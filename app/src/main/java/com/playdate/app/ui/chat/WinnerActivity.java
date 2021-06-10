package com.playdate.app.ui.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.playdate.app.R;

public class WinnerActivity extends Dialog {

    TextView txt_winner, txt_ans, txt_level, txt_points;
    ImageView img_medal, iv_close,center;
    String rank;

    public WinnerActivity(@NonNull Context context, String rank) {
        super(context, R.style.My_Dialog);
        this.rank = rank;
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

        img_medal = view.findViewById(R.id.img_medal);
        txt_winner = view.findViewById(R.id.text_winner);
        txt_ans = view.findViewById(R.id.text_ans);
        txt_level = view.findViewById(R.id.text_level);
        txt_points = view.findViewById(R.id.txt_points);
        iv_close = view.findViewById(R.id.iv_close);
        center = view.findViewById(R.id.center);


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

//        if (txt_points.equals("+100")) {
//            img_medal.setImageResource(R.drawable.medal);
//            txt_winner.setText("Winner!");
//            txt_ans.setText("You answered first!");
//        } else if (txt_points.equals("+50")) {
//            img_medal.setImageResource(R.drawable.medal_second);
//            txt_winner.setText("Not Bad!");
//            txt_ans.setText("You answered second!");
//        } else if (txt_points.equals("0")) {
//            img_medal.setImageResource(R.drawable.brokenheart);
//            txt_winner.setText("Nice Try!");
//            txt_ans.setText("You answered wrong!");
//        }


        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

}


//public class WinnerActivity extends AppCompatActivity {
//    TextView txt_winner, txt_ans, txt_level, txt_points;
//    ImageView img_medal;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_winner);
//        setInIt();
//
//    }
//
//    private void setInIt() {
//
//        img_medal = (ImageView) findViewById(R.id.img_medal);
//        txt_winner = (TextView) findViewById(R.id.text_winner);
//        txt_ans = (TextView) findViewById(R.id.text_ans);
//        txt_level = (TextView) findViewById(R.id.text_level);
//        txt_points = (TextView) findViewById(R.id.txt_points);
//        txt_points.setText("+50");
//
//        if (txt_points.equals("+100")) {
//            img_medal.setImageResource(R.drawable.medal);
//            txt_winner.setText("Winner!");
//            txt_ans.setText("You answered first!");
//        } else if (txt_points.equals("+50")) {
//            img_medal.setImageResource(R.drawable.medal_second);
//            txt_winner.setText("Not Bad!");
//            txt_ans.setText("You answered second!");
//        } else if (txt_points.equals("0")){
//            img_medal.setImageResource(R.drawable.brokenheart);
//            txt_winner.setText("Nice Try!");
//            txt_ans.setText("You answered wrong!");
//
//        }
//
//
//    }
//}