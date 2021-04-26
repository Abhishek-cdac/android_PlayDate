package com.playdate.app.ui.dashboard.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.playdate.app.R;
import com.playdate.app.ui.dashboard.adapter.FriendAdapter;
import com.playdate.app.ui.dashboard.adapter.SuggestionAdapter;

public class FragLanding extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_landing, container, false);
        ViewPager vp_suggestion = view.findViewById(R.id.vp_suggestion);
        RecyclerView rv_friends = view.findViewById(R.id.rv_friends);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        FriendAdapter adapterfriend = new FriendAdapter();
        rv_friends.setAdapter(adapterfriend);
        rv_friends.setLayoutManager(manager);

//        SuggestionAdapter adapter = new SuggestionAdapter(getActivity(), getActivity().getSupportFragmentManager());
        SuggestionAdapter adapter = new SuggestionAdapter(getActivity());
        vp_suggestion.setAdapter(adapter);
        vp_suggestion.setCurrentItem(8);
        vp_suggestion.setPadding(130, 0, 130, 0);


        return view;
    }
}
