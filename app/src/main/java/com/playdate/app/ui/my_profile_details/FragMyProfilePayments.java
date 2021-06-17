package com.playdate.app.ui.my_profile_details;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.ui.date.games.FragCoinScreen;
import com.playdate.app.ui.interfaces.OnInnerFragmentClicks;
import com.playdate.app.ui.invite.InviteFriendActivity;
import com.playdate.app.ui.my_profile_details.adapters.PaymentsAdapter;
import com.playdate.app.util.common.CommonClass;


public class FragMyProfilePayments extends Fragment {
    public FragMyProfilePayments() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_my_details_payments, container, false);

//        Button btn_add_payment = view.findViewById(R.id.btn_add_payment);
//        ConstraintLayout cl_page = view.findViewById(R.id.cl_page);
//
//        int height = new CommonClass().getScreenHeight(getActivity());
//
//
//        int m1= (int) getResources().getDimension(R.dimen._15sdp);
//        int m2= (int) getResources().getDimension(R.dimen._10sdp);
//        int m3= (int) getResources().getDimension(R.dimen._20sdp);
//        int m4= (int) getResources().getDimension(R.dimen._20sdp);
//        int m5= (int) getResources().getDimension(R.dimen._20sdp);
//        int m6= (int) getResources().getDimension(R.dimen._75sdp);
//
//        cl_page.getLayoutParams().height=height-(m1+m2+m3+m4+m5+m6);


//        btn_add_payment.setOnClickListener(v -> {
//            OnInnerFragmentClicks frag = (OnInnerFragmentClicks) getActivity();
//            frag.ReplaceFrag(new NewPaymentMethod());
//        });

//        PaymentsAdapter adapter = new PaymentsAdapter();
//        RecyclerView recycler_payment = view.findViewById(R.id.recycler_payment);
//        recycler_payment.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
//        recycler_payment.setAdapter(adapter);
        return view;

    }
}