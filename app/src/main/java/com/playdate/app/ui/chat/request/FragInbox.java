package com.playdate.app.ui.chat.request;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.data.api.GetDataService;
import com.playdate.app.model.chat_models.ChatExample;
import com.playdate.app.model.chat_models.ChatMessage;
import com.playdate.app.model.chat_models.ChatUserList;
import com.playdate.app.ui.chat.FragChatMain;
import com.playdate.app.ui.chat.ChattingAdapter;
import com.playdate.app.ui.date.fragments.FragIntroScreen;
import com.playdate.app.ui.date.fragments.FragPartnerSelected;
import com.playdate.app.ui.interfaces.OnInnerFragmentClicks;
import com.playdate.app.util.common.TransparentProgressDialog;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragInbox extends Fragment implements onClickEventListener {
    //    InboxAdapter inboxAdapter;
//    private List<Inbox> inboxList = new ArrayList<>();
    ArrayList<ChatExample> chatExampleList;
    ArrayList<ChatMessage> chatMessageList;
    ChattingAdapter chattingAdapter;
    RecyclerView recyclerView;
    Onclick itemClick;
    RelativeLayout rl_c;
    RequestAdapter requestAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_inbox_list, container, false);
        recyclerView = view.findViewById(R.id.friend_list);
        rl_c = view.findViewById(R.id.rl_c);

        callApiForChats();
//            setAdapter();

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

      /*  callChats.enqueue(new Callback<ChatUserList>() {
            @Override
            public void onItemClicks(View v, int absoluteAdapterPosition, int i, String commentId, String postId, String userId) {

            }

            @Override
            public void onItemClicks(View v, int position, int i, String username, String totalPoints, String id, String profilePicPath) {

            }
        };
        setAdapter();
            public void onResponse(Call<ChatUserList> call, Response<ChatUserList> response) {
                Log.d("Response ", response.toString());
                pd.cancel();
                chatExampleList = new ArrayList<>(response.body().getChats());
                Log.d("ChatmessageLIst", chatExampleList.toString());

                for (int i = 0; i < chatExampleList.size(); i++) {
                    chattingAdapter = new ChattingAdapter(chatExampleList, itemClick, FragInbox.this);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
//                    chattingAdapter.getFilter().filter(sequence);

                    recyclerView.setAdapter(chattingAdapter);

                }
//                requestAdapter = new RequestAdapter(FragInbox.this);
            }

            @Override
            public void onFailure(Call<ChatUserList> call, Throwable t) {
                Log.d("Error code", t + "Failed to get data");
                pd.cancel();
                Toast.makeText(getActivity(), t + "Failed to get data", Toast.LENGTH_SHORT).show();
            }
        });
    }
*/
    }

    @Override
    public void onClickEvent(int position) {
        chatMessageList = new ArrayList<>(chatExampleList.get(position).getMessages());
        String sender_name = chatExampleList.get(position).getSenderName();
        String sender_profile_image = chatExampleList.get(position).getProfilePhoto();


        OnInnerFragmentClicks frag = (OnInnerFragmentClicks) getActivity();
        if (frag != null) {
            frag.ReplaceFrag(new FragChatMain(chatMessageList, sender_name, sender_profile_image));
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
        ArrayList<ChatExample> filteredList = new ArrayList<>();
        chatExampleList = new ArrayList<>();

        for (ChatExample item : chatExampleList) {
            if (item.getSenderName().toLowerCase().contains(s.toLowerCase())) {
                filteredList.add(item);
            }
        }

        chattingAdapter.filterList(filteredList);
    }

    }


interface onClickEventListener {
    void onClickEvent(int position);
}



