package com.playdate.app.ui.chat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.playdate.app.R;

public class WinnerActivity extends AppCompatActivity {
    TextView txt_winner, txt_ans, txt_level, txt_points;
    ImageView img_medal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winner);
        setInIt();

    }

    private void setInIt() {

        img_medal = (ImageView) findViewById(R.id.img_medal);
        txt_winner = (TextView) findViewById(R.id.text_winner);
        txt_ans = (TextView) findViewById(R.id.text_ans);
        txt_level = (TextView) findViewById(R.id.text_level);
        txt_points = (TextView) findViewById(R.id.txt_points);
        txt_points.setText("+50");

        if (txt_points.equals("+100")) {
            img_medal.setImageResource(R.drawable.medal);
            txt_winner.setText("Winner!");
            txt_ans.setText("You answered first!");
        } else if (txt_points.equals("+50")) {
            img_medal.setImageResource(R.drawable.medal_second);
            txt_winner.setText("Not Bad!");
            txt_ans.setText("You answered second!");
        } else if (txt_points.equals("0")){
            img_medal.setImageResource(R.drawable.brokenheart);
            txt_winner.setText("Nice Try!");
            txt_ans.setText("You answered wrong!");

        }


    }
}