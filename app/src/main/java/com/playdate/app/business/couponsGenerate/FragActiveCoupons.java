package com.playdate.app.business.couponsGenerate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.business.couponsGenerate.adapter.ActiveCouponsAdapter;

public class FragActiveCoupons extends Fragment {
    private RecyclerView rv_coupons_list;
    ActiveCouponsAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_active_coupons, container, false);
        rv_coupons_list = view.findViewById(R.id.rv_coupons_list);
        enableSwipeToDeleteAndUndo();

        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        rv_coupons_list.setLayoutManager(manager);

         adapter = new ActiveCouponsAdapter();
        rv_coupons_list.setAdapter(adapter);

        return view;
    }

    private void enableSwipeToDeleteAndUndo() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(getActivity()) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();

                adapter.removeItem(position);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchHelper.attachToRecyclerView(rv_coupons_list);
    }
}



