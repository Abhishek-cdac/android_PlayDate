package com.playdate.app.ui.chat_ui_screen.request;

import android.content.Intent;
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

import com.google.gson.JsonObject;
import com.playdate.app.R;
import com.playdate.app.model.ChattingGetChats;
import com.playdate.app.model.Inbox;
import com.playdate.app.ui.chat_ui_screen.ChatActivity;
import com.playdate.app.ui.chat_ui_screen.ChattingAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class FragInbox extends Fragment {
    InboxAdapter inboxAdapter;
    private List<Inbox> inboxList = new ArrayList<>();
    ArrayList<ChattingGetChats> arrayList = new ArrayList<>();
    RecyclerView recyclerView;
    Onclick itemClick;

    public FragInbox() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_inbox_list, container, false);
        recyclerView = view.findViewById(R.id.friend_list);

        itemClick = new Onclick() {
            @Override
            public void onItemClick(View view, int position, int value) {
                if (value == 12) {
                    Intent intent = new Intent(getContext(), ChatActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onItemClicks(View view, int position, int value, String s) {

            }

            @Override
            public void onItemClicks(View v, int adapterPosition, int i, String notifiationId, String userId) {

            }
        };
        try {
            setAdapter();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return view;
    }

    private void setAdapter() throws JSONException {

        inboxAdapter = new InboxAdapter(inboxList, itemClick);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(inboxAdapter);
        prepareInboxData();
//        callApiForChats();
    }




//    private void callApiForChats() throws JSONException {
//        try {
//            JSONObject object1 = new JSONObject(loadJSONFromAssets());
//            JSONArray m_jArry = object1.getJSONArray("chats");
//            for (int i = 0; i < m_jArry.length(); i++) {
//                JSONObject jsonObject = m_jArry.getJSONObject(i);
//
//                String id = jsonObject.getString("id");
//                String first_name = jsonObject.getString("name");
//
//                ChattingGetChats getChats = new ChattingGetChats();
//                getChats.setId(id);
//                getChats.setName(first_name);
//                arrayList.add(getChats);
//            }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

//        inboxAdapter = new ChattingAdapter(arrayList, itemClick);
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
//        recyclerView.setLayoutManager(mLayoutManager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.setAdapter(inboxAdapter);
//    }

    private String loadJSONFromAssets() {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open("chats.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


    private void prepareInboxData() {

        Inbox inbox = new Inbox("jonn den", "https://images.saymedia-content.com/.image/t_share/MTc1MDE0NzI4MTg2OTk2NTIz/5-instagram-models-you-should-be-following.png", "I am good", "5", "8:00");
        inboxList.add(inbox);

        inbox = new Inbox("gomes helen", "https://images.saymedia-content.com/.image/t_share/MTc0MDkwNjUxNDc2OTYwODM0/5-instagram-models-you-should-be-following.png", "How are you", "3", "1d");
        inboxList.add(inbox);

        inbox = new Inbox("adreena helen", "https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/4p3a7420-copy-1524689604.jpg", "Hiiiiiii", "5", "1d");
        inboxList.add(inbox);

        inbox = new Inbox("Ramsphy k", "https://s29588.pcdn.co/wp-content/uploads/sites/2/2018/08/Claire-Abbott-1.jpg.optimal.jpg", "coming", "6", "2d");
        inboxList.add(inbox);

        inbox = new Inbox("adreena helen", "https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/4p3a7420-copy-1524689604.jpg", "yupp...", "5", "2d");
        inboxList.add(inbox);

        inbox = new Inbox("Ramsphy k", "https://s29588.pcdn.co/wp-content/uploads/sites/2/2018/08/Claire-Abbott-1.jpg.optimal.jpg", "lets see", "2", "3d");
        inboxList.add(inbox);


    }

}
