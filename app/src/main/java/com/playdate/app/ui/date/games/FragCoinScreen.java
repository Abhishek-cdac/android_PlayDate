package com.playdate.app.ui.date.games;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.playdate.app.R;
import com.playdate.app.ui.interfaces.OnInnerFragmentClicks;

import java.util.Objects;

public class FragCoinScreen extends Fragment {
    int coin = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_coin_screen, container, false);

        ImageView iv_minus;
        ImageView iv_add;
//        TextView tv_coins_available;
        TextView tv_coins;
        Button btn_buy_coin;
        Button btn_send_coin;

        iv_minus = view.findViewById(R.id.iv_minus);
        iv_add = view.findViewById(R.id.iv_add);
//        tv_coins_available = view.findViewById(R.id.tv_coins_available);
        tv_coins = view.findViewById(R.id.tv_coins);
        btn_buy_coin = view.findViewById(R.id.btn_buy_coin);
        btn_send_coin = view.findViewById(R.id.btn_send_coin);

        tv_coins.setText(String.valueOf(coin));
        iv_minus.setOnClickListener(v -> {
            coin = coin - 1;
            tv_coins.setText(String.valueOf(coin));
        });

        iv_add.setOnClickListener(v -> {
            coin = coin + 1;
            tv_coins.setText(String.valueOf(coin));
        });

        btn_send_coin.setOnClickListener(v -> {
            OnInnerFragmentClicks frag = (OnInnerFragmentClicks) getActivity();
            Objects.requireNonNull(frag).ReplaceFrag(new FragTimesUp1());
        });

        btn_buy_coin.setOnClickListener(v -> {
//            OnInnerFragmentClicks frag = (OnInnerFragmentClicks) getActivity();
            new FragStore(Objects.requireNonNull(getActivity())).show();
//                frag.ReplaceFrag(new FragStore());
        });

        return view;
    }
}
