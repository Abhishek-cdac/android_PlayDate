package com.playdate.app.ui.chat.request;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.data.api.GetDataService;
import com.playdate.app.model.Inbox;
import com.playdate.app.model.chat_models.ChatExample;
import com.playdate.app.model.chat_models.ChatUserList;
import com.playdate.app.ui.chat.ChattingAdapter;
import com.playdate.app.util.common.TransparentProgressDialog;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragRequest extends Fragment {
    RequestAdapter requestAdapter;
    private List<Inbox> inboxList = new ArrayList<>();
    RecyclerView recyclerView;
    ArrayList<ChatExample> chatExampleList;

    Onclick itemClick;

    public FragRequest() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_request_list, container, false);
        recyclerView = view.findViewById(R.id.friend_list);
        callApiForChats();
        return view;
    }

    private void callApiForChats() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://mocki.io/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GetDataService getServiceApi = retrofit.create(GetDataService.class);
        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(getActivity());
        pd.show();

        Call<ChatUserList> callChats = getServiceApi.getChats();

        callChats.enqueue(new Callback<ChatUserList>() {
            @Override
            public void onResponse(Call<ChatUserList> call, Response<ChatUserList> response) {
                Log.d("Response ", response.toString());
                pd.cancel();
                chatExampleList = new ArrayList<>(response.body().getChats());
                Log.d("ChatmessageLIst", chatExampleList.toString());

                for (int i = 0; i < chatExampleList.size(); i++) {
                    requestAdapter = new RequestAdapter(chatExampleList, itemClick);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(requestAdapter);

                }
            }

            @Override
            public void onFailure(Call<ChatUserList> call, Throwable t) {
                Log.d("Error code", t + "Failed to get data");
                Toast.makeText(getActivity(), t + "Failed to get data", Toast.LENGTH_SHORT).show();
            }
        });

    }
}

//    private void setAdapter() {
//        requestAdapter = new RequestAdapter(inboxList, itemClick);
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
//        recyclerView.setLayoutManager(mLayoutManager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.setAdapter(requestAdapter);
//        prepareInboxData();
//    }

//    private void prepareInboxData() {
//        Inbox inbox = new Inbox("jonn den", "https://images.saymedia-content.com/.image/t_share/MTc1MDE0NzI4MTg2OTk2NTIz/5-instagram-models-you-should-be-following.png", "Wants to send you request", "5", "8:00");
//        inboxList.add(inbox);
//
//        inbox = new Inbox("gomes helen", "https://images.saymedia-content.com/.image/t_share/MTc0MDkwNjUxNDc2OTYwODM0/5-instagram-models-you-should-be-following.png", "Wants to send you request", "3", "1d");
//        inboxList.add(inbox);
//
//        inbox = new Inbox("adreena helen", "https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/4p3a7420-copy-1524689604.jpg", "Wants to send you msg", "5", "1d");
//        inboxList.add(inbox);
//    }

