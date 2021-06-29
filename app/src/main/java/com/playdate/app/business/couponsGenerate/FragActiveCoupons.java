package com.playdate.app.business.couponsGenerate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.business.couponsGenerate.adapter.ActiveCouponsAdapter;
import com.playdate.app.ui.interfaces.OnInnerFragmentClicks;

public class FragActiveCoupons extends Fragment implements OnInnerFragmentClicks {
    private RecyclerView rv_coupons_list;
    ActiveCouponsAdapter adapter;
    private Fragment CurrentFrag;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_active_coupons, container, false);
        rv_coupons_list = view.findViewById(R.id.rv_coupons_list);
        enableSwipeToDeleteAndUndo();
        enableSwipeToUpdateAndUndo();

        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        rv_coupons_list.setLayoutManager(manager);

        adapter = new ActiveCouponsAdapter();
        rv_coupons_list.setAdapter(adapter);

        return view;
    }

    private void enableSwipeToUpdateAndUndo() {
        SwipeToUpdateCallback swipeToUpdateCallback = new SwipeToUpdateCallback(getActivity()) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();

                ReplaceFragWithStack(new FragCouponGenerator());
//                adapter.removeItem(position);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeToUpdateCallback);
        itemTouchHelper.attachToRecyclerView(rv_coupons_list);
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

    @Override
    public void ReplaceFrag(Fragment fragment) {

    }

    @Override
    public void ReplaceFragWithStack(Fragment fragment) {
        try {
            CurrentFrag = fragment;
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            if (fragmentManager.getFragments().size() > 0) {
                ft.replace(R.id.flFragment, fragment, fragment.getClass().getSimpleName());
            } else {
                ft.add(R.id.flFragment, fragment, fragment.getClass().getSimpleName());
            }
            ft.addToBackStack("tags");
            ft.commitAllowingStateLoss();

            //  mSwipeRefreshLayout.setEnabled(CurrentFrag.getClass().getSimpleName().equals("FragSocialFeed"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void NoFriends() {

    }

    @Override
    public void Reset() {

    }

    @Override
    public void loadProfile(String UserID) {

    }

    @Override
    public void loadMatchProfile(String UserID) {

    }
}



