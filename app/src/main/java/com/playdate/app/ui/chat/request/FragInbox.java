package com.playdate.app.ui.chat.request;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.data.api.GetDataService;
import com.playdate.app.data.api.RetrofitClientInstance;
import com.playdate.app.model.chat_models.ChatList;
import com.playdate.app.model.chat_models.ChatResponse;
import com.playdate.app.ui.chat.ChatMainActivity;
import com.playdate.app.ui.chat.ChattingAdapter;
import com.playdate.app.util.common.TransparentProgressDialog;
import com.playdate.app.util.session.SessionPref;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragInbox extends Fragment implements onClickEventListener {
    private ArrayList<ChatList> lst_chat_users;
    //    private ArrayList<ChatMessage> chatMessageList;
    private ChattingAdapter chattingAdapter;
    private RecyclerView recyclerView;
    private Onclick itemClick;
    private RelativeLayout rl_c;
    private RequestAdapter requestAdapter;
    SessionPref pref;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_inbox_list, container, false);
        recyclerView = view.findViewById(R.id.friend_list);
        rl_c = view.findViewById(R.id.rl_c);
        pref = SessionPref.getInstance(getActivity());
        callApiForChats();
//            setAdapter();

        return view;
    }

    private void callApiForChats() {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        SessionPref pref = SessionPref.getInstance(getActivity());
        hashMap.put("userId", pref.getStringVal(SessionPref.LoginUserID));
        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(getActivity());
        pd.show();
        Call<ChatResponse> callChats = service.getChatUsers("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);

        callChats.enqueue(new Callback<ChatResponse>() {
            @Override
            public void onResponse(Call<ChatResponse> call, Response<ChatResponse> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        lst_chat_users = response.body().getLst();

                        chattingAdapter = new ChattingAdapter(lst_chat_users, itemClick, FragInbox.this);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                        recyclerView.setLayoutManager(mLayoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(chattingAdapter);
                    }
                    pd.cancel();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ChatResponse> call, Throwable t) {
                pd.cancel();
            }
        });
    }


    @Override
    public void onClickEvent(int position) {


        try {
            if (null != lst_chat_users) {
                Intent mIntent = new Intent(getActivity(), ChatMainActivity.class);
                mIntent.putExtra("Name", lst_chat_users.get(position).getLstFrom().get(0).getUsername());
                mIntent.putExtra("Image", lst_chat_users.get(position).getLstFrom().get(0).getProfilePicPath());
                mIntent.putExtra("UserID", lst_chat_users.get(position).getLstFrom().get(0).getUserId());
                mIntent.putExtra("chatId", lst_chat_users.get(position).getChatId());
                startActivity(mIntent);


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void onAcceptChatRequest(String name, String image) {
        Log.d("Accepted", "onAcceptChatRequest: ");
        chattingAdapter = new ChattingAdapter(name, image);

    }

    public void setFilters(CharSequence s) {
//        chattingAdapter.getFilter().filter(s);
        chattingAdapter.notifyDataSetChanged();

    }

    public void filter(String s) {
        Log.d("Filter Method", "In Filter method");
        try {
            ArrayList<ChatList> filteredList = new ArrayList<>();
//        chatExampleList = new ArrayList<>(); ////for search purpose comment it

//            for (ChatList item : lst_chat_users) {
//                if (item.getSenderName().toLowerCase().contains(s.toLowerCase())) {
//                    filteredList.add(item);
//                    Log.d("SIZE", String.valueOf(filteredList.size()));
//                }
//            }

            for (int i = 0; i < filteredList.size(); i++) {
//                Log.d("SENDERNAME", filteredList.get(i).getSenderName());

            }
//        chattingAdapter = new ChattingAdapter();  ////for search purpose comment it
//            chattingAdapter.filterList(filteredList);
        } catch (Exception e) {
            Log.d("Exception during search", e.toString());

            e.printStackTrace();
        }
    }

}


interface onClickEventListener {
    void onClickEvent(int position);
}



