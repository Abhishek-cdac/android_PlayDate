package com.playdate.app.ui.dashboard.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.data.api.GetDataService;
import com.playdate.app.data.api.RetrofitClientInstance;
import com.playdate.app.model.CommonModel;
import com.playdate.app.model.GetUserSuggestion;
import com.playdate.app.model.GetUserSuggestionData;
import com.playdate.app.ui.chat.request.Onclick;
import com.playdate.app.ui.dashboard.OnFriendSelected;
import com.playdate.app.ui.dashboard.adapter.SuggestedFriendAdapter;
import com.playdate.app.util.common.CommonClass;
import com.playdate.app.util.common.TransparentProgressDialog;
import com.playdate.app.util.session.SessionPref;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragSearchUser extends Fragment implements SuggestedFriendAdapter.SuggestionsAdapterListner {
    private EditText edt_search;
    private RecyclerView recyclerView;
    private ArrayList<GetUserSuggestionData> lst_getUserSuggestions;
    private CommonClass clsCommon;
    private Onclick itemClick;
    private SuggestedFriendAdapter adapter;
    private int PageNumber=1;

    public FragSearchUser() {
    }
    


    public void OnUserProfileSelected(boolean isFriend, String id) {
        try {
            OnFriendSelected inf = (OnFriendSelected) getActivity();
            Objects.requireNonNull(inf).OnSuggestionClosed(isFriend, id);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_search_user, container, false);
        clsCommon = CommonClass.getInstance();
        edt_search = view.findViewById(R.id.edt_search);
        TextView txt_cancel = view.findViewById(R.id.txt_cancel);
        recyclerView = view.findViewById(R.id.recycler_view);
        itemClick = new Onclick() {
            @Override
            public void onItemClick(View view, int position, int value) {

            }

            @Override
            public void onItemClicks(View view, int position, int value, String id) {
                if (value == 10) {
                    callAddFriendRequestApi(id);
                }
            }

            @Override
            public void onItemClicks(View v, int adapterPosition, int i, String notifiationId, String userId) {

            }

            @Override
            public void onItemClicks(View v, int absoluteAdapterPosition, int i, String commentId, String postId, String userId) {

            }

            @Override
            public void onItemClicks(View v, int position, int i, String username, String totalPoints, String id, String profilePicPath) {

            }
        };

        txt_cancel.setOnClickListener(v -> {
            edt_search.setText("");
            OnFriendSelected inf = (OnFriendSelected) getActivity();
            inf.OnSuggestionClosed();


        });


        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.length() > 0) {
                    ArrayList<GetUserSuggestionData> lst = new ArrayList<>();
                    for (int i = 0; i < lst_getUserSuggestions.size(); i++) {
                        if (lst_getUserSuggestions.get(i).getUsername().toLowerCase().contains(s.toString().toLowerCase())) {
                            lst.add(lst_getUserSuggestions.get(i));
                        }
                    }
                    adapter = new SuggestedFriendAdapter(lst, itemClick, FragSearchUser.this);
                    recyclerView.setAdapter(adapter);

                } else {
                    adapter = new SuggestedFriendAdapter(lst_getUserSuggestions, itemClick, FragSearchUser.this);
                    recyclerView.setAdapter(adapter);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
//                adapter.getFilter().filter(s);
            }
        });

        callGetUserSuggestionAPI();


        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                OnFriendSelected inf = (OnFriendSelected) getActivity();
                Objects.requireNonNull(inf).OnSuggestionClosed();
                return true;
            }
            return false;
        });
        return view;
    }

    private void callGetUserSuggestionAPI() {

        if(PageNumber==-1){
            return;
        }

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        // hashMap.put("filter", "");
        hashMap.put("limit", "20");
        hashMap.put("pageNo", ""+PageNumber);//Hardcode
        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(getActivity());
        pd.show();
        SessionPref pref = SessionPref.getInstance(getActivity());
        Call<GetUserSuggestion> call = service.getUserSuggestion("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new Callback<GetUserSuggestion>() {
            @Override
            public void onResponse(Call<GetUserSuggestion> call, Response<GetUserSuggestion> response) {
                pd.cancel();
                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {
                        ArrayList<GetUserSuggestionData> lst = (ArrayList<GetUserSuggestionData>) response.body().getData();
                        if (lst == null) {
                            lst = new ArrayList<>();
                        }

                        if(lst.isEmpty()){
                            PageNumber=-1;
                            adapter.setHideShowMore();
                            adapter.notifyItemChanged(lst_getUserSuggestions.size()-1);
                            return;
                        }
                        if(PageNumber==1){
                            PageNumber=PageNumber+1;
                            lst_getUserSuggestions = new ArrayList<>();
                            lst_getUserSuggestions.addAll(lst);
                            RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
                            recyclerView.setLayoutManager(manager);
                            adapter = new SuggestedFriendAdapter(lst_getUserSuggestions, itemClick, FragSearchUser.this);
                            recyclerView.setAdapter(adapter);
                        }else{
                            PageNumber=PageNumber+1;
                            lst_getUserSuggestions.addAll(lst);
                            adapter.notifyDataSetChanged();

                        }


                    }
                } else {
                    PageNumber=-1;
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsgfrag(getActivity(), "PlayDate", jObjError.getString("message"), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<GetUserSuggestion> call, Throwable t) {
                t.printStackTrace();
                pd.cancel();
                PageNumber=-1;
                Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onSuggestionSelected(GetUserSuggestionData getUserSuggestionData) {

        Log.e("filter user", "" + getUserSuggestionData);
    }

    private void callAddFriendRequestApi(String toUserID) {

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("toUserID", toUserID);
        SessionPref pref = SessionPref.getInstance(getActivity());

        Call<CommonModel> call = service.addFriendRequest("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new Callback<CommonModel>() {
            @Override
            public void onResponse(Call<CommonModel> call, Response<CommonModel> response) {
//                pd.cancel();
                if (response.code() == 200) {
                    assert response.body() != null;
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsgfrag(getActivity(), "PlayDate", jObjError.getString("message"), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<CommonModel> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void loadMore() {
        callGetUserSuggestionAPI();
    }
}
