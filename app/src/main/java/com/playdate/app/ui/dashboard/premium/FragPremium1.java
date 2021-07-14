package com.playdate.app.ui.dashboard.premium;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.model.PremiumPlans;
import com.playdate.app.ui.dashboard.adapter.PremiumAdapter;
import com.playdate.app.ui.dashboard.more_suggestion.FragInvite;

public class FragPremium1 extends Fragment {
    private final FragInvite fragInvite;
    private PremiumPlans premiumPlans;


    public FragPremium1(FragInvite fragInvite, PremiumPlans premiumPlans) {
        this.fragInvite = fragInvite;
        this.premiumPlans = premiumPlans;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_get_premium1, container, false);
        ImageView iv_close=view.findViewById(R.id.iv_close);
        iv_close.setOnClickListener(v -> fragInvite.closePremium());
        RecyclerView recy_premiun=view.findViewById(R.id.recy_premiun);
        PremiumAdapter adapter=new PremiumAdapter(premiumPlans.getLst_packageDescription(),1);
        LinearLayoutManager maLinearLayout=new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
        recy_premiun.setLayoutManager(maLinearLayout);
        recy_premiun.setAdapter(adapter);
        return view;
    }
}
