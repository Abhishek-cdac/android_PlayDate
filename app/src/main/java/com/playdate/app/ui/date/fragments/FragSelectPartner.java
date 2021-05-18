package com.playdate.app.ui.date.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.github.ybq.android.spinkit.SpinKitView;
import com.playdate.app.R;
import com.playdate.app.model.PartnerImage;
import com.playdate.app.ui.date.adapter.PartnerViewPagerAdapter;
import com.playdate.app.ui.interfaces.OnInnerFragmentClicks;

import java.util.ArrayList;

public class FragSelectPartner extends Fragment implements OnPartnerSelectedListener{
    ArrayList<PartnerImage> list = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_date_select_partner,container,false);


        PartnerViewPagerAdapter adapter;
        Button btn_search_partner;
        ViewPager vp_partners;
        TextView tv_waiting;
        TextView tv_or;
        SpinKitView spin_kit;

        vp_partners = view.findViewById(R.id.vp_partners);
        btn_search_partner = view.findViewById(R.id.btn_search_partner);


        list.add(new PartnerImage("https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/4p3a7420-copy-1524689604.jpg","kaylajenner12","1200"));
        list.add(new PartnerImage("https://images.saymedia-content.com/.image/t_share/MTc0MDkwNjUxNDc2OTYwODM0/5-instagram-models-you-should-be-following.png","malaika2124","1200"));
        list.add(new PartnerImage("https://hips.hearstapps.com/esquireuk.cdnds.net/17/17/elizabeth-turner.jpg","kaylajenner12","1400"));
        list.add(new PartnerImage("https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/4p3a7420-copy-1524689604.jpg","kaylajenner12","1200"));
        list.add(new PartnerImage("https://hips.hearstapps.com/esquireuk.cdnds.net/17/17/elizabeth-turner.jpg","kaylajenner12","1200"));
        list.add(new PartnerImage("https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/4p3a7420-copy-1524689604.jpg","myron1122","1300"));
        list.add(new PartnerImage("https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/4p3a7420-copy-1524689604.jpg","geneeeres","1200"));
        list.add(new PartnerImage("https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/4p3a7420-copy-1524689604.jpg","kaylajenner12","1200"));
        list.add(new PartnerImage("https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/4p3a7420-copy-1524689604.jpg","kaylajenner12","1200"));
        list.add(new PartnerImage("https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/4p3a7420-copy-1524689604.jpg","kaylajenner12","1200"));
        list.add(new PartnerImage("https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/4p3a7420-copy-1524689604.jpg","kaylajenner12","1200"));


        vp_partners.setClipToPadding(false);
        vp_partners.setPageMargin(-450);

        adapter = new PartnerViewPagerAdapter(getActivity(), list,this);
        vp_partners.setAdapter(adapter);


        return view;
    }


    @Override
    public void OnPortnerSelect(int position) {

        OnInnerFragmentClicks frag = (OnInnerFragmentClicks) getActivity();

        Fragment fragment=new FragPartnerSelected();
        Bundle bundle=new Bundle();
        bundle.putString("profile_image", list.get(position).getImage());
        bundle.putString("profile_name", list.get(position).getName());
        bundle.putString("profile_points", list.get(position).getPoints());
        fragment.setArguments(bundle);
        frag.ReplaceFrag(fragment);

        // Intent intent = new Intent(view.getContext(), PartnerSelected.class);

    }
}
interface OnPartnerSelectedListener{
    void OnPortnerSelect(int inex);
}



//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_date_select_partner);
//        vp_partners = findViewById(R.id.vp_partners);
//        btn_search_partner = findViewById(R.id.btn_search_partner);
//
//
//        list.add(new PartnerImage("https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/4p3a7420-copy-1524689604.jpg","kaylajenner12","1200"));
//        list.add(new PartnerImage("https://images.saymedia-content.com/.image/t_share/MTc0MDkwNjUxNDc2OTYwODM0/5-instagram-models-you-should-be-following.png","malaika2124","1200"));
//        list.add(new PartnerImage("https://hips.hearstapps.com/esquireuk.cdnds.net/17/17/elizabeth-turner.jpg","kaylajenner12","1400"));
//        list.add(new PartnerImage("https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/4p3a7420-copy-1524689604.jpg","kaylajenner12","1200"));
//        list.add(new PartnerImage("https://hips.hearstapps.com/esquireuk.cdnds.net/17/17/elizabeth-turner.jpg","kaylajenner12","1200"));
//        list.add(new PartnerImage("https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/4p3a7420-copy-1524689604.jpg","myron1122","1300"));
//        list.add(new PartnerImage("https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/4p3a7420-copy-1524689604.jpg","geneeeres","1200"));
//        list.add(new PartnerImage("https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/4p3a7420-copy-1524689604.jpg","kaylajenner12","1200"));
//        list.add(new PartnerImage("https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/4p3a7420-copy-1524689604.jpg","kaylajenner12","1200"));
//        list.add(new PartnerImage("https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/4p3a7420-copy-1524689604.jpg","kaylajenner12","1200"));
//        list.add(new PartnerImage("https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/4p3a7420-copy-1524689604.jpg","kaylajenner12","1200"));
//
//
//        vp_partners.setClipToPadding(false);
//        vp_partners.setPageMargin(-450);
//
//        adapter = new PartnerViewPagerAdapter(SelectPartner.this, list);
//        vp_partners.setAdapter(adapter);
//
//
//    }