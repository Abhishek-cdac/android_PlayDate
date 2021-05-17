package com.playdate.app.ui.date;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.github.ybq.android.spinkit.SpinKitView;
import com.playdate.app.R;
import com.playdate.app.model.PartnerImage;
import com.playdate.app.ui.date.adapter.PartnerViewPagerAdapter;

import java.util.ArrayList;

public class SelectPartner extends AppCompatActivity {

    ArrayList<PartnerImage> list = new ArrayList<>();
    PartnerViewPagerAdapter adapter;
    Button btn_search_partner;
    ViewPager vp_partners;
    TextView tv_waiting;
    TextView tv_or;
    SpinKitView spin_kit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_partner);
        vp_partners = findViewById(R.id.vp_partners);
        btn_search_partner = findViewById(R.id.btn_search_partner);
        tv_waiting = findViewById(R.id.tv_waiting);
        tv_or = findViewById(R.id.tv_or);
        spin_kit = findViewById(R.id.spin_kit);


        list.add(new PartnerImage("https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/4p3a7420-copy-1524689604.jpg"));
        list.add(new PartnerImage("https://images.saymedia-content.com/.image/t_share/MTc0MDkwNjUxNDc2OTYwODM0/5-instagram-models-you-should-be-following.png"));
        list.add(new PartnerImage("https://hips.hearstapps.com/esquireuk.cdnds.net/17/17/elizabeth-turner.jpg"));
        list.add(new PartnerImage("https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/4p3a7420-copy-1524689604.jpg"));
        list.add(new PartnerImage("https://hips.hearstapps.com/esquireuk.cdnds.net/17/17/elizabeth-turner.jpg"));
        list.add(new PartnerImage("\"https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/4p3a7420-copy-1524689604.jpg\""));
        list.add(new PartnerImage("\"https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/4p3a7420-copy-1524689604.jpg\""));
        list.add(new PartnerImage("\"https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/4p3a7420-copy-1524689604.jpg\""));
        list.add(new PartnerImage("\"https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/4p3a7420-copy-1524689604.jpg\""));
        list.add(new PartnerImage("\"https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/4p3a7420-copy-1524689604.jpg\""));
        list.add(new PartnerImage("\"https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/4p3a7420-copy-1524689604.jpg\""));


        vp_partners.setClipToPadding(false);
        vp_partners.setPageMargin(-450);

        adapter = new PartnerViewPagerAdapter(SelectPartner.this, list);
        vp_partners.setAdapter(adapter);


    }

    public void updateChanges() {
        btn_search_partner.setVisibility(View.GONE);
        tv_or.setVisibility(View.GONE);
        spin_kit.setVisibility(View.VISIBLE);
        tv_waiting.setVisibility(View.VISIBLE);
        adapter.notifyDataSetChanged();

    }
}
