package com.playdate.app.business.couponsGenerate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.playdate.app.R;
import com.playdate.app.util.common.CommonClass;

public class FragCouponGenerator extends Fragment {

    Button btnCreateCoupons;
    ImageView iv_back_generator;
    RelativeLayout rl_body;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_coupons_generater, container, false);
        btnCreateCoupons = view.findViewById(R.id.btnCreateCoupons);
        iv_back_generator = view.findViewById(R.id.iv_back_generator);
        rl_body = view.findViewById(R.id.rl_body);

//        int height = new CommonClass().getScreenHeight(getActivity());
//
//        int m1 = (int) getResources().getDimension(R.dimen._15sdp);
//        int m2 = (int) getResources().getDimension(R.dimen._10sdp);
//        int m3 = (int) getResources().getDimension(R.dimen._20sdp);
//        int m4 = (int) getResources().getDimension(R.dimen._20sdp);
//        int m5 = (int) getResources().getDimension(R.dimen._60sdp);
//        int m6 = (int) getResources().getDimension(R.dimen._75sdp);
//
//        rl_body.getLayoutParams().height = height - (m1 + m2 + m3 + m4 + m5 + m6);



        btnCreateCoupons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FragCouponCreated(getActivity()).show();
            }
        });

        iv_back_generator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        });
        return view;
    }

    //    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_coupons_generater);
//        btnCreateCoupons = findViewById(R.id.btnCreateCoupons);
//        btnCreateCoupons.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), CouponCreatedActivity.class);
//                startActivity(intent);
//            }
//        });
//    }
}