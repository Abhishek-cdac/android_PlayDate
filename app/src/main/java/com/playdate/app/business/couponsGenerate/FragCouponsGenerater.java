package com.playdate.app.business.couponsGenerate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.playdate.app.R;


public class FragCouponsGenerater extends Dialog {

    ImageView iv_back;
    Button btnCreateCoupons;

    public FragCouponsGenerater(@NonNull Context context) {
        super(context, R.style.My_Dialog);

        WindowManager.LayoutParams wlmp = getWindow().getAttributes();

        wlmp.gravity = Gravity.CENTER_HORIZONTAL;
        getWindow().setAttributes(wlmp);
        setTitle(null);
        setCancelable(true);
        setOnCancelListener(null);
        View view = LayoutInflater.from(context).inflate(
                R.layout.activity_coupons_generater, null);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(view);
        iv_back = view.findViewById(R.id.iv_back_generator);
        btnCreateCoupons = view.findViewById(R.id.btnCreateCoupons);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        btnCreateCoupons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FragCouponCreated(context).show();
                dismiss();

            }
        });

    }
}

//
//public class FragCouponsGenerater extends Fragment {
//
//    Button btnCreateCoupons;
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.activity_coupons_generater, container, false);
//        btnCreateCoupons = view.findViewById(R.id.btnCreateCoupons);
//        btnCreateCoupons.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Intent intent = new Intent(getApplicationContext(), CouponCreatedActivity.class);
////                startActivity(intent);
//            }
//        });
//        return view;
//    }
//
////    @Override
////    protected void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////        setContentView(R.layout.activity_coupons_generater);
////        btnCreateCoupons = findViewById(R.id.btnCreateCoupons);
////        btnCreateCoupons.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                Intent intent = new Intent(getApplicationContext(), CouponCreatedActivity.class);
////                startActivity(intent);
////            }
////        });
////    }
//}