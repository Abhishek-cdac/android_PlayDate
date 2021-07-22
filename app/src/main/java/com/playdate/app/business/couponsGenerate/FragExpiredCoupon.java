package com.playdate.app.business.couponsGenerate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.business.couponsGenerate.adapter.ActiveCouponsAdapter;
import com.playdate.app.business.couponsGenerate.adapter.ExpiredCouponsAdapter;
import com.playdate.app.model.GetBusinessCouponData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class FragExpiredCoupon extends Fragment {
    private ArrayList<GetBusinessCouponData> lst;
    public FragExpiredCoupon(ArrayList<GetBusinessCouponData> lst) {
    this.lst=lst;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_expired_coupons, container, false);
        RecyclerView rv_coupons_list = view.findViewById(R.id.rv_coupons_list);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        rv_coupons_list.setLayoutManager(manager);

        //        *****Temp Code*****
        String inputPattern = "yyyy-MM-dd";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat(inputPattern, Locale.getDefault());
        String formattedDate = df.format(c);
        ArrayList<GetBusinessCouponData> lstTemp = new ArrayList<>();
        for (int i = 0; i < lst.size(); i++) {
            try {
                String[] s = lst.get(i).getCouponValidTillDate().split("T");
                Date date = inputFormat.parse(s[0]);
                Date date1 = inputFormat.parse(formattedDate);
                if (date.before(date1)) {
                    lstTemp.add(lst.get(i));
                }


            } catch (ParseException e) {
                e.printStackTrace();
            }

        }

//        *****Temp Code*****


        ExpiredCouponsAdapter adapter = new ExpiredCouponsAdapter(lstTemp);
        rv_coupons_list.setAdapter(adapter);

        return view;
    }
}
