package com.playdate.app.ui.dashboard.more_suggestion;

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
import com.playdate.app.ui.dashboard.adapter.SuggestedFriendAdapter;

public class FragSuggested extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_suggested_list, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.friend_list);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);

        SuggestedFriendAdapter adapter = new SuggestedFriendAdapter();
        recyclerView.setAdapter(adapter);


        return view;
    }
}
