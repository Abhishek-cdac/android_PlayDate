package com.playdate.app.ui.dashboard.premium;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.playdate.app.R;
import com.playdate.app.ui.dashboard.more_suggestion.FragInvite;

public class FragPremium3 extends Fragment {

    FragInvite fragInvite;

    public FragPremium3(FragInvite fragInvite) {
        this.fragInvite = fragInvite;
    }

    public FragPremium3() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_get_premium3, container, false);
        ImageView iv_close=view.findViewById(R.id.iv_close);
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragInvite.closePremium();
            }
        });
        return view;
    }
}
