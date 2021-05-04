package com.playdate.app.ui.anonymous_question;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.ui.anonymous_question.adapter.ColorAdapter;
import com.playdate.app.ui.anonymous_question.adapter.SmileyAdapter;

import java.util.ArrayList;

public class AnoQuesCreateActivity extends AppCompatActivity implements OnColorCodeSelect {
    RelativeLayout ll_ques;
    LinearLayout ll_smily;
    RecyclerView rec_view_colors;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ano_ques);
        rec_view_colors = findViewById(R.id.rec_view_colors);
        ll_ques = findViewById(R.id.ll_ques);
        ll_smily = findViewById(R.id.ll_smily);
        CreateList();
        CreateSmilyList();
        OnColorChange(0);
        ColorAdapter adapter = new ColorAdapter(lst, this);
        rec_view_colors.setLayoutManager(new GridLayoutManager(this, 4));
        rec_view_colors.setAdapter(adapter);
    }

    ArrayList<Integer> lst = new ArrayList<>();
    ArrayList<Integer> lstSmiley = new ArrayList<>();

    public void CreateList() {
        lst.add(R.color.color_pink);
        lst.add(R.color.color_violet);
        lst.add(R.color.color_violet1);
        lst.add(R.color.color_green_fresh);
        lst.add(R.color.black);
        lst.add(R.color.color_yellow);
        lst.add(R.color.color_blue_ligth);
        lst.add(R.color.color_brown);
        lst.add(R.color.color_blue);
        lst.add(R.color.color_grey);
        lst.add(R.color.color_green_dark);
        lst.add(R.color.color_red);


    }

    public void CreateSmilyList() {
        lstSmiley.add(R.drawable.face1);
        lstSmiley.add(R.drawable.face2);
        lstSmiley.add(R.drawable.face3);
        lstSmiley.add(R.drawable.face4);
        lstSmiley.add(R.drawable.face5);
        lstSmiley.add(R.drawable.face6);
        lstSmiley.add(R.drawable.face7);
        lstSmiley.add(R.drawable.face8);
        lstSmiley.add(R.drawable.face9);
        lstSmiley.add(R.drawable.face10);
        lstSmiley.add(R.drawable.face11);
        lstSmiley.add(R.drawable.face12);
        lstSmiley.add(R.drawable.face13);
        lstSmiley.add(R.drawable.face14);
        lstSmiley.add(R.drawable.face15);
        lstSmiley.add(R.drawable.face16);
        lstSmiley.add(R.drawable.face17);
        lstSmiley.add(R.drawable.face18);
        lstSmiley.add(R.drawable.face19);
        lstSmiley.add(R.drawable.face20);
        lstSmiley.add(R.drawable.face21);
        lstSmiley.add(R.drawable.face22);
        lstSmiley.add(R.drawable.face23);
        lstSmiley.add(R.drawable.face24);
        lstSmiley.add(R.drawable.face25);
        lstSmiley.add(R.drawable.face26);
        lstSmiley.add(R.drawable.face27);
        lstSmiley.add(R.drawable.face28);
        lstSmiley.add(R.drawable.face29);


    }

    @Override
    public void OnColorChange(int index) {
        ll_ques.setBackground(getDrawable(lst.get(index)));

        SmileyAdapter adapter = new SmileyAdapter(lstSmiley, this);
        rec_view_colors.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        rec_view_colors.setAdapter(adapter);
    }

    @Override
    public void onSmileySelect(int index) {
        ll_smily.setBackground(getDrawable(lstSmiley.get(index)));
    }
}


interface OnColorCodeSelect {
    void OnColorChange(int index);

    void onSmileySelect(int index);
}
