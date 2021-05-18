package com.playdate.app.ui.date.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.playdate.app.R;
import com.playdate.app.ui.interfaces.OnInnerFragmentClicks;
import com.squareup.picasso.Picasso;

public class FragPartnerSelected extends Fragment {

    ImageView iv_partner_image;
    ImageView iv_accepted;
    ImageView iv_loading;
    TextView tv_waiting;
    TextView tv_points;
    TextView tv_username;
    String image_url;
    String image_name;
    String image_points;
    boolean accepted = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_date_selected_partner, container, false);

        iv_partner_image = view.findViewById(R.id.partner_image);
        iv_accepted = view.findViewById(R.id.iv_accepted);
        iv_loading = view.findViewById(R.id.iv_loading);
        tv_waiting = view.findViewById(R.id.tv_waiting);
        tv_points = view.findViewById(R.id.tv_points);
        tv_username = view.findViewById(R.id.tv_username);

        Bundle bundle = getArguments();
        image_url = bundle.getString("profile_image", "");
        image_name = bundle.getString("profile_name", "");
        image_points = bundle.getString("profile_points", "");

        tv_username.setText(image_name);
        tv_points.setText(image_points);
        Picasso.get().load(image_url).placeholder(R.drawable.cupertino_activity_indicator).into(iv_partner_image);
        tv_waiting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!accepted) {
                    iv_loading.setVisibility(View.GONE);
                    tv_waiting.setText(R.string.accepted_patner);
                    iv_accepted.setVisibility(View.VISIBLE);
                    accepted = true;
                } else {
//                    startActivity(new Intent(getActivity(), SelectDateActivity.class));
                    OnInnerFragmentClicks frag = (OnInnerFragmentClicks) getActivity();
                    frag.ReplaceFrag(new FragSelectDate());
                }


            }
        });

        return view;

    }

//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_date_selected_partner);
//        iv_partner_image = findViewById(R.id.partner_image);
//        iv_accepted = findViewById(R.id.iv_accepted);
//        iv_loading = findViewById(R.id.iv_loading);
//        tv_waiting = findViewById(R.id.tv_waiting);
//        tv_points = findViewById(R.id.tv_points);
//        tv_username = findViewById(R.id.tv_username);
//
//        Intent intent = getIntent();
//        image_url = intent.getStringExtra("profile_image");
//        image_name = intent.getStringExtra("profile_name");
//        image_points = intent.getStringExtra("profile_points");
//
//        tv_username.setText(image_name);
//        tv_points.setText(image_points);
//        Picasso.get().load(image_url).placeholder(R.drawable.cupertino_activity_indicator).into(iv_partner_image);
//
////        if (accepted) {
////            iv_loading.setVisibility(View.GONE);
////            tv_waiting.setText(R.string.accepted_patner);
////            iv_accepted.setVisibility(View.VISIBLE);
////        } else {
////            iv_loading.setVisibility(View.VISIBLE);
////            tv_waiting.setText(R.string.waiting_partner);
////            iv_accepted.setVisibility(View.GONE);
////        }
//
//        tv_waiting.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!accepted) {
//                    iv_loading.setVisibility(View.GONE);
//                    tv_waiting.setText(R.string.accepted_patner);
//                    iv_accepted.setVisibility(View.VISIBLE);
//                    accepted = true;
//                } else {
//                    startActivity(new Intent(PartnerSelected.this, SelectDateActivity.class));
//                }
//
//
//            }
//        });
//
//    }

}
