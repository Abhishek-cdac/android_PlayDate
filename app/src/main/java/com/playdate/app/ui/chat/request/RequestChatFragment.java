package com.playdate.app.ui.chat.request;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.playdate.app.R;
import com.playdate.app.data.api.GetDataService;
import com.playdate.app.data.api.RetrofitClientInstance;
import com.playdate.app.model.chat_models.ChatList;
import com.playdate.app.model.chat_models.ChatResponse;
import com.playdate.app.ui.chat.ChatMainActivity;
import com.playdate.app.ui.chat.ChattingAdapter;
import com.playdate.app.ui.interfaces.OnInnerFragmentClicks;
import com.playdate.app.ui.notification_screen.FragNotification;
import com.playdate.app.util.common.TransparentProgressDialog;
import com.playdate.app.util.session.SessionPref;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestChatFragment extends Fragment implements View.OnClickListener, onClickEventListener {

    private ArrayList<ChatList> lst_chat_users;
    private ChattingAdapter adapter;
    private RecyclerView recyclerView;
    private TextView txt_no_chat;
    private Onclick itemClick;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public RequestChatFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_request_chat, container, false);
        ImageView back_anonymous = view.findViewById(R.id.back_anonymous);
        ImageView iv_chat_notification = view.findViewById(R.id.iv_chat_notification);
        EditText edt_search_chat = view.findViewById(R.id.edt_search_chat);
        recyclerView = view.findViewById(R.id.friend_list);
        txt_no_chat = view.findViewById(R.id.txt_no_chat);
        mSwipeRefreshLayout = view.findViewById(R.id.swiperefresh);


        edt_search_chat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                filter(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.color_pink));

        mSwipeRefreshLayout.setOnRefreshListener(RequestChatFragment.this::callApiForChats);

        back_anonymous.setOnClickListener(this);
        iv_chat_notification.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.back_anonymous) {
            requireActivity().finish();
        } else if (id == R.id.iv_chat_notification) {
            OnInnerFragmentClicks ref = (OnInnerFragmentClicks) getActivity();
            Objects.requireNonNull(ref).ReplaceFrag(new FragNotification("chat"));
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        callApiForChats();
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

                        adapter = new ChattingAdapter(lst_chat_users, itemClick, RequestChatFragment.this);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                        recyclerView.setLayoutManager(mLayoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(adapter);
                        txt_no_chat.setVisibility(View.GONE);
                    }else{
                        txt_no_chat.setVisibility(View.VISIBLE);
                    }
                    pd.cancel();
                } catch (Exception e) {
                    e.printStackTrace();
                    pd.cancel();
                    txt_no_chat.setVisibility(View.VISIBLE);
                }
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<ChatResponse> call, Throwable t) {
                pd.cancel();
                txt_no_chat.setVisibility(View.VISIBLE);
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }


    @Override
    public void onClickEvent(ChatList item) {


        try {
            if (null != lst_chat_users) {
                Intent mIntent = new Intent(getActivity(), ChatMainActivity.class);
                mIntent.putExtra("Name", item.getLstFrom().get(0).getUsername());
                mIntent.putExtra("Image", item.getLstFrom().get(0).getProfilePicPath());
                mIntent.putExtra("UserID", item.getLstFrom().get(0).getUserId());
                mIntent.putExtra("chatId", item.getChatId());

                startActivity(mIntent);


                if (null != edt_search_chat) {
                    edt_search_chat.setText("");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void setFilters(CharSequence s) {
        adapter.notifyDataSetChanged();

    }

    EditText edt_search_chat;

    public void filter(String str) {
        try {
            ArrayList<ChatList> filteredList = new ArrayList<>();

            for (ChatList item : lst_chat_users) {
                if (item.getLstFrom().get(0).getUsername().toLowerCase().contains(str.toLowerCase())) {
                    filteredList.add(item);
                }
            }

            adapter.filterList(filteredList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

interface onClickEventListener {
    void onClickEvent(ChatList item);
}