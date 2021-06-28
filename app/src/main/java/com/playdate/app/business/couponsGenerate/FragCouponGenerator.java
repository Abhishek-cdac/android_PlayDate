package com.playdate.app.business.couponsGenerate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.playdate.app.R;
import com.playdate.app.business.couponsGenerate.dialogs.DialogCouponCreated;
import com.playdate.app.business.couponsGenerate.dialogs.DialogLevelSelector;

public class FragCouponGenerator extends Fragment {

    Button btnCreateCoupons;
    ImageView iv_back_generator;
    RelativeLayout rl_body;
    LinearLayout ll_dropdown;
    TextView tv_awarded;
    TextView tv_level;
    RelativeLayout rl_awarded;
    ImageView iv_drop;
    boolean isDropdownVisible = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_coupons_generater, container, false);
        btnCreateCoupons = view.findViewById(R.id.btnCreateCoupons);
        iv_back_generator = view.findViewById(R.id.iv_back_generator);
        rl_body = view.findViewById(R.id.rl_body);
        ll_dropdown = view.findViewById(R.id.ll_dropdown);
        tv_awarded = view.findViewById(R.id.tv_awarded);
        rl_awarded = view.findViewById(R.id.rl_awarded);
        tv_level = view.findViewById(R.id.tv_level);
        iv_drop = view.findViewById(R.id.iv_drop);

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
                new DialogCouponCreated(getActivity()).show();
            }
        });

        iv_back_generator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        });
        rl_awarded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDropdownVisible) {
                    ll_dropdown.setVisibility(View.GONE);
                    iv_drop.setImageResource(R.drawable.ic_arrow_drop_down);
                    isDropdownVisible = false;
                } else {
                    ll_dropdown.setVisibility(View.VISIBLE);
                    iv_drop.setImageResource(R.drawable.ic_arrow_drop_up);
                    isDropdownVisible = true;
                }
            }
        });

        tv_level.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DialogLevelSelector(getActivity()).show();
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