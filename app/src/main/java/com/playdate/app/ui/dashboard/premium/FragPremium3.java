package com.playdate.app.ui.dashboard.premium;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class FragPremium3 extends Fragment {

    private FragInvite fragInvite;
    private PremiumPlans premiumPlans;

    public FragPremium3(FragInvite fragInvite, PremiumPlans premiumPlans) {
        this.fragInvite = fragInvite;
        this.premiumPlans = premiumPlans;
    }

    public FragPremium3() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_get_premium3, container, false);
        ImageView iv_close = view.findViewById(R.id.iv_close);
        iv_close.setOnClickListener(v -> fragInvite.closePremium());
        Button getNowButton=view.findViewById(R.id.login_button);
        getNowButton.setOnClickListener(v -> fragInvite.closePremium());
        RecyclerView recycler_premium = view.findViewById(R.id.recy_premiun);
        PremiumAdapter adapter = new PremiumAdapter(premiumPlans.getLst_packageDescription(),3);
        LinearLayoutManager maLinearLayout = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recycler_premium.setLayoutManager(maLinearLayout);
        recycler_premium.setAdapter(adapter);
        return view;
    }
}
