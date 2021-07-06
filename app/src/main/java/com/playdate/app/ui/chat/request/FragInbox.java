//package com.playdate.app.ui.chat.request;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.EditText;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.DefaultItemAnimator;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
//
//import com.playdate.app.R;
//import com.playdate.app.data.api.GetDataService;
//import com.playdate.app.data.api.RetrofitClientInstance;
//import com.playdate.app.model.chat_models.ChatList;
//import com.playdate.app.model.chat_models.ChatResponse;
//import com.playdate.app.ui.chat.ChatMainActivity;
//import com.playdate.app.ui.chat.ChattingAdapter;
//import com.playdate.app.util.common.TransparentProgressDialog;
//import com.playdate.app.util.session.SessionPref;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class FragInbox extends Fragment implements onClickEventListener {
//    private ArrayList<ChatList> lst_chat_users;
//    private ChattingAdapter adapter;
//    private RecyclerView recyclerView;
//    private Onclick itemClick;
//    private SwipeRefreshLayout mSwipeRefreshLayout;
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.frag_inbox_list, container, false);
//        recyclerView = view.findViewById(R.id.friend_list);
//        mSwipeRefreshLayout = view.findViewById(R.id.swiperefresh);
//
//        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.color_pink));
//
//        mSwipeRefreshLayout.setOnRefreshListener(() -> FragInbox.this.callApiForChats());
//        return view;
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        callApiForChats();
//    }
//
//    private void callApiForChats() {
//        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
//        Map<String, String> hashMap = new HashMap<>();
//        SessionPref pref = SessionPref.getInstance(getActivity());
//        hashMap.put("userId", pref.getStringVal(SessionPref.LoginUserID));
//        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(getActivity());
//        pd.show();
//        Call<ChatResponse> callChats = service.getChatUsers("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
//
//        callChats.enqueue(new Callback<ChatResponse>() {
//            @Override
//            public void onResponse(Call<ChatResponse> call, Response<ChatResponse> response) {
//                try {
//                    if (response.body().getStatus() == 1) {
//                        lst_chat_users = response.body().getLst();
//
//                        adapter = new ChattingAdapter(lst_chat_users, itemClick, FragInbox.this);
//                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
//                        recyclerView.setLayoutManager(mLayoutManager);
//                        recyclerView.setItemAnimator(new DefaultItemAnimator());
//                        recyclerView.setAdapter(adapter);
//                    }
//                    pd.cancel();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                mSwipeRefreshLayout.setRefreshing(false);
//            }
//
//            @Override
//            public void onFailure(Call<ChatResponse> call, Throwable t) {
//                pd.cancel();
//                mSwipeRefreshLayout.setRefreshing(false);
//            }
//        });
//    }
//
//
//    @Override
//    public void onClickEvent(ChatList item) {
//
//
//        try {
//            if (null != lst_chat_users) {
//                Intent mIntent = new Intent(getActivity(), ChatMainActivity.class);
//                mIntent.putExtra("Name", item.getLstFrom().get(0).getUsername());
//                mIntent.putExtra("Image", item.getLstFrom().get(0).getProfilePicPath());
//                mIntent.putExtra("UserID", item.getLstFrom().get(0).getUserId());
//                mIntent.putExtra("chatId", item.getChatId());
//
//                startActivity(mIntent);
//
//
//                if (null != edt_search_chat) {
//                    edt_search_chat.setText("");
//                }
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    public void setFilters(CharSequence s) {
//        adapter.notifyDataSetChanged();
//
//    }
//
//    EditText edt_search_chat;
//
//    public void filter(String s, EditText edt_search_chat) {
//        try {
//            ArrayList<ChatList> filteredList = new ArrayList<>();
//
//            for (ChatList item : lst_chat_users) {
//                if (item.getLstFrom().get(0).getUsername().toLowerCase().contains(s.toLowerCase())) {
//                    filteredList.add(item);
//                    Log.d("SIZE", String.valueOf(filteredList.size()));
//                }
//            }
//
//            adapter.filterList(filteredList);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//}
//
//
//interface onClickEventListener {
//    void onClickEvent(ChatList item);
//}
//
//
//
