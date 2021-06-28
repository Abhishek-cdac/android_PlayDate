package com.playdate.app.business.couponsGenerate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.playdate.app.R;

public class FragCouponGenerator extends Fragment {

    Button btnCreateCoupons;
    ImageView iv_back_generator;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_coupons_generater, container, false);
        btnCreateCoupons = view.findViewById(R.id.btnCreateCoupons);
        iv_back_generator = view.findViewById(R.id.iv_back_generator);



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