package com.playdate.app.ui.date.games;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.ui.date.OnBackPressed;
import com.playdate.app.ui.date.adapter.StoreDMAdapter;
import com.playdate.app.ui.date.adapter.StoreDateCoinAdpter;
import com.playdate.app.ui.date.adapter.StoreGameCoinAdapter;
import com.playdate.app.ui.date.adapter.StoreMultiplierAdapter;
import com.playdate.app.ui.interfaces.OnInnerFragmentClicks;

public class FragStore extends Fragment {

    ImageView cancel;
    ImageView iv_leaderboard;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_coupon_store, container, false);
        RecyclerView rv_game_coin = view.findViewById(R.id.rv_game_coin);
        RecyclerView rv_date_coin = view.findViewById(R.id.rv_date_coin);
        RecyclerView rv_multiplier = view.findViewById(R.id.rv_multiplier);
        RecyclerView rv_dm_booster = view.findViewById(R.id.rv_dm_booster);
        cancel = view.findViewById(R.id.cancel);
        iv_leaderboard = view.findViewById(R.id.iv_leaderboard);

        RecyclerView.LayoutManager manager = new GridLayoutManager(getActivity(), 3);
        rv_game_coin.setLayoutManager(manager);
        RecyclerView.LayoutManager manager1 = new GridLayoutManager(getActivity(), 3);
        rv_date_coin.setLayoutManager(manager1);
        RecyclerView.LayoutManager manager2 = new GridLayoutManager(getActivity(), 3);
        rv_multiplier.setLayoutManager(manager2);
        RecyclerView.LayoutManager manager3 = new GridLayoutManager(getActivity(), 3);
        rv_dm_booster.setLayoutManager(manager3);

        StoreGameCoinAdapter adapter = new StoreGameCoinAdapter();
        rv_game_coin.setAdapter(adapter);
        StoreDateCoinAdpter adapter1 = new StoreDateCoinAdpter();
        rv_date_coin.setAdapter(adapter1);
        StoreMultiplierAdapter adapter2 = new StoreMultiplierAdapter();
        rv_multiplier.setAdapter(adapter2);
        StoreDMAdapter adapter3 = new StoreDMAdapter();
        rv_dm_booster.setAdapter(adapter3);

        iv_leaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnInnerFragmentClicks frag = (OnInnerFragmentClicks) getActivity();
                frag.ReplaceFrag(new FragGameLeaderBoard());
            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CANCEL", "onClick: ");
//                getActivity().finish();
                getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        });
        return view;
    }
}
