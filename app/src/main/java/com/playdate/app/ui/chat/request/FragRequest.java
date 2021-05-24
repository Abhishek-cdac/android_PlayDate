package com.playdate.app.ui.chat.request;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.model.Inbox;
import com.playdate.app.ui.dashboard.adapter.SuggestedFriendAdapter;

import java.util.ArrayList;
import java.util.List;

public class FragRequest  extends Fragment {
    RequestAdapter requestAdapter;
    private List<Inbox> inboxList = new ArrayList<>();
    RecyclerView recyclerView;
    Onclick itemClick;

    public FragRequest() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_request_list, container, false);
        recyclerView = view.findViewById(R.id.friend_list);
        setAdapter();
        return view;
    }

    private void setAdapter() {
        requestAdapter = new RequestAdapter(inboxList, itemClick);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(requestAdapter);
        prepareInboxData();
    }

    private void prepareInboxData() {
        Inbox inbox = new Inbox("jonn den", "https://images.saymedia-content.com/.image/t_share/MTc1MDE0NzI4MTg2OTk2NTIz/5-instagram-models-you-should-be-following.png", "Wants to send you request", "5", "8:00");
        inboxList.add(inbox);

        inbox = new Inbox("gomes helen", "https://images.saymedia-content.com/.image/t_share/MTc0MDkwNjUxNDc2OTYwODM0/5-instagram-models-you-should-be-following.png", "Wants to send you request", "3", "1d");
        inboxList.add(inbox);

        inbox = new Inbox("adreena helen", "https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/4p3a7420-copy-1524689604.jpg", "Wants to send you msg", "5", "1d");
        inboxList.add(inbox);
    }
}
