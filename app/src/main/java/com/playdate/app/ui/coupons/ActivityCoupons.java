package com.playdate.app.ui.coupons;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.ui.coupons.adapters.FrequentlyQuestionAdapter;

public class ActivityCoupons extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frag_coupan_code);
        RecyclerView rv_frequently = findViewById(R.id.rv_frequently);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rv_frequently.setLayoutManager(manager);
        FrequentlyQuestionAdapter adapter = new FrequentlyQuestionAdapter();
        rv_frequently.setAdapter(adapter);
    }
}
