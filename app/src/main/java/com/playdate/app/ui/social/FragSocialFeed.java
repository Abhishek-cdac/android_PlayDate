package com.playdate.app.ui.social;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;

public class FragSocialFeed extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.row_social_feed, container, false);
        RecyclerView recycler_view_feed = view.findViewById(R.id.recycler_view_feed);
        SocialFeedAdapter adapter = new SocialFeedAdapter();
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recycler_view_feed.setLayoutManager(manager);
        recycler_view_feed.setAdapter(adapter);
        return view;
    }
}
