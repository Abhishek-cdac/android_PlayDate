package com.playdate.app.ui.my_profile_details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.playdate.app.R;
import com.playdate.app.ui.coupons.WrapContentViewPager;
import com.playdate.app.ui.my_profile_details.adapters.SettingPageAdapter;

import org.jetbrains.annotations.NotNull;

public class FragSettingsParent extends Fragment implements View.OnClickListener {

    private WrapContentViewPager viewPager;
    private TextView txt_account;
    private TextView txt_personal;
    private TextView txt_payment;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_setting_parent, container, false);
        viewPager = view.findViewById(R.id.viewpager);
        txt_account = view.findViewById(R.id.txt_account);
        txt_personal = view.findViewById(R.id.txt_personal);
        txt_payment = view.findViewById(R.id.txt_payment);
        SettingPageAdapter adapter = new SettingPageAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);


        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                viewPager.reMeasureCurrentPage(0);
                switch (position) {
                    case 0:
                        viewPager.reMeasureCurrentPage(0);
                        txt_account.setTextColor(getResources().getColor(R.color.white));
                        txt_account.setBackground(getResources().getDrawable(R.drawable.menu_button));
                        txt_personal.setTextColor(getResources().getColor(android.R.color.darker_gray));
                        txt_personal.setBackground(null);
                        txt_payment.setTextColor(getResources().getColor(android.R.color.darker_gray));
                        txt_payment.setBackground(null);
                        break;
                    case 1:
                        viewPager.reMeasureCurrentPage(1);
                        txt_personal.setTextColor(getResources().getColor(R.color.white));
                        txt_personal.setBackground(getResources().getDrawable(R.drawable.menu_button));
                        txt_account.setTextColor(getResources().getColor(android.R.color.darker_gray));
                        txt_account.setBackground(null);
                        txt_payment.setTextColor(getResources().getColor(android.R.color.darker_gray));
                        txt_payment.setBackground(null);
                        break;
                    case 2:
                        viewPager.reMeasureCurrentPage(2);
                        txt_payment.setTextColor(getResources().getColor(R.color.white));
                        txt_payment.setBackground(getResources().getDrawable(R.drawable.menu_button));
                        txt_personal.setTextColor(getResources().getColor(android.R.color.darker_gray));
                        txt_personal.setBackground(null);
                        txt_account.setTextColor(getResources().getColor(android.R.color.darker_gray));
                        txt_account.setBackground(null);
                        break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        txt_account.setOnClickListener(this);
        txt_personal.setOnClickListener(this);
//        txt_payment.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.txt_account) {
            viewPager.setCurrentItem(0);
        } else if (id == R.id.txt_personal) {
            viewPager.setCurrentItem(1);
        } else {
            viewPager.setCurrentItem(2);
        }
    }
}
