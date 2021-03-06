package com.playdate.app.ui.dashboard.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.data.api.GetDataService;
import com.playdate.app.data.api.RetrofitClientInstance;
import com.playdate.app.model.RestMain;
import com.playdate.app.ui.chat.request.Onclick;
import com.playdate.app.ui.coupons.FragCouponParent;
import com.playdate.app.ui.dashboard.OnFriendSelected;
import com.playdate.app.ui.dashboard.adapter.RestaurentAdapter;
import com.playdate.app.ui.interfaces.OnInnerFragmentClicks;
import com.playdate.app.ui.restaurant.adapter.Restaurant;
import com.playdate.app.util.session.SessionPref;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentSearchRestaurant extends Fragment implements RestaurentAdapter.RestaurentAdapterListner {

    private EditText edt_search;
    private RecyclerView recyclerView;
    private Onclick itemClick;
    private RestaurentAdapter adapter;
    private SessionPref pref;

    public FragmentSearchRestaurant() {
    }

    public void OnUserProfileSelected(boolean isFriend, String id) {
        try {
            OnFriendSelected inf = (OnFriendSelected) getActivity();
            Objects.requireNonNull(inf).onSuggestionClosed(isFriend, id);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_search_user, container, false);
//        CommonClass clsCommon = CommonClass.getInstance();
        pref = SessionPref.getInstance(getContext());
        edt_search = view.findViewById(R.id.edt_search);
        TextView txt_cancel = view.findViewById(R.id.txt_cancel);
        recyclerView = view.findViewById(R.id.recycler_view);
        TextView txt_suggestion = view.findViewById(R.id.txt_suggestion);

        txt_suggestion.setVisibility(View.GONE);
        itemClick = new Onclick() {
            @Override
            public void onItemClick(View view, int position, int value) {

            }

            @Override
            public void onItemClicks(View view, int position, int value, String id) {

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
            OnInnerFragmentClicks frag = (OnInnerFragmentClicks) getActivity();
            Objects.requireNonNull(frag).ReplaceFrag(new FragCouponParent());
//            getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        });


        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
                adapter.getFilter().filter(s);
            }
        });

        callAPIRestaurant();


        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                edt_search.setText("");
                OnInnerFragmentClicks frag = (OnInnerFragmentClicks) getActivity();
                Objects.requireNonNull(frag).ReplaceFrag(new FragCouponParent());
//                OnFriendSelected inf = (OnFriendSelected) getActivity();
//                Objects.requireNonNull(inf).OnSuggestionClosed();
                return true;
            }
            return false;
        });
        return view;
    }

    private void callAPIRestaurant() {

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("limit", "100");
        hashMap.put("pageNo", "1");

        Call<RestMain> call = service.restaurants("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new Callback<RestMain>() {
            @Override
            public void onResponse(@NonNull Call<RestMain> call, @NonNull Response<RestMain> response) {
                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {
                        ArrayList<Restaurant> restaurantLst = response.body().getLst();
                        if (restaurantLst == null) {
                            restaurantLst = new ArrayList<>();
                        }

                        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
                        recyclerView.setLayoutManager(manager);
                        adapter = new RestaurentAdapter(restaurantLst, itemClick, FragmentSearchRestaurant.this);
                        recyclerView.setAdapter(adapter);
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<RestMain> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });

    }


    @Override
    public void onSuggestionSelected(Restaurant restaurant) {
//        Log.e("filter restaurant", "" + restaurant);
    }


}
