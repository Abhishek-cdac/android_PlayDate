package com.playdate.app.ui.card_swipe;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DiffUtil;

import com.playdate.app.R;
import com.playdate.app.data.api.GetDataService;
import com.playdate.app.data.api.RetrofitClientInstance;
import com.playdate.app.model.CommonModel;
import com.playdate.app.model.Interest;
import com.playdate.app.model.InterestsMain;
import com.playdate.app.model.MatchListModel;
import com.playdate.app.model.MatchListUser;
import com.playdate.app.ui.chat_ui_screen.request.Onclick;
import com.playdate.app.util.common.CommonClass;
import com.playdate.app.util.common.TransparentProgressDialog;
import com.playdate.app.util.session.SessionPref;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeableMethod;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragCardSwipe extends Fragment {

    private CardStackLayoutManager manager;
    private TinderSwipeAdapter adapter;
    public ArrayList<Interest> lst_interest;
    Onclick itemClick;
    private CommonClass clsCommon;
    private CardStackView cardStackView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        clsCommon = CommonClass.getInstance();
        View view = inflater.inflate(R.layout.tinder_swipe, container, false);
        itemClick = new Onclick() {
            @Override
            public void onItemClick(View view, int position, int value) {

            }

            @Override
            public void onItemClicks(View view, int position, int value, String s) {
                if (value == 13) {
                    callAddUserMatchRequestAPI(s, "Like");

                } else if (value == 14) {
                    callAddUserMatchRequestAPI(s, "Unlike");

                }
            }

            @Override
            public void onItemClicks(View v, int adapterPosition, int i, String notifiationId, String userId) {

            }
        };
        cardStackView = view.findViewById(R.id.card_stack_view);
        ConstraintLayout cl_page = view.findViewById(R.id.cl_page);


        int height = new CommonClass().getScreenHeight(getActivity());

        int m1 = (int) getResources().getDimension(R.dimen._15sdp);
        int m2 = (int) getResources().getDimension(R.dimen._10sdp);
        int m3 = (int) getResources().getDimension(R.dimen._20sdp);
        int m4 = (int) getResources().getDimension(R.dimen._20sdp);
        int m5 = (int) getResources().getDimension(R.dimen._60sdp);
        int m6 = (int) getResources().getDimension(R.dimen._75sdp);

        cl_page.getLayoutParams().height = height - (m1 + m2 + m3 + m4 + m5 + m6);

        getInterest();

        return view;
    }

    private void getInterest() {


        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("limit", "50");
        hashMap.put("pageNo", "1");
        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(getActivity());
        pd.show();
        SessionPref pref = SessionPref.getInstance(getActivity());

        Call<InterestsMain> call = service.interested("Bareer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new Callback<InterestsMain>() {
            @Override
            public void onResponse(Call<InterestsMain> call, Response<InterestsMain> response) {
                pd.cancel();
                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {
                        lst_interest = response.body().getLst();
                        if (lst_interest == null) {
                            lst_interest = new ArrayList<>();
                        }
                        callAPI();

                    } else {
//                        clsCommon.showDialogMsg(InterestActivity.this, "PlayDate", response.body().getMessage(), "Ok");
                    }
                } else {
//                    try {
//                        JSONObject jObjError = new JSONObject(response.errorBody().string());
//                        clsCommon.showDialogMsg(InterestActivity.this, "PlayDate", jObjError.getString("message").toString(), "Ok");
//                    } catch (Exception e) {
//                        Toast.makeText(InterestActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
//                    }

                }


            }

            @Override
            public void onFailure(Call<InterestsMain> call, Throwable t) {
                t.printStackTrace();
                pd.cancel();
//                Toast.makeText(InterestActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void callAPI() {


        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("limit", "100");
        hashMap.put("pageNo", "1");
        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(getActivity());
        pd.show();
        SessionPref pref = SessionPref.getInstance(getActivity());
        Call<MatchListModel> call = service.getUserMatchList("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new Callback<MatchListModel>() {
            @Override
            public void onResponse(Call<MatchListModel> call, Response<MatchListModel> response) {
                pd.cancel();
                if (response.code() == 200) {
                    if (response.body().getStatus() == 1) {

                        setPages(response.body().getUsers());

                    } else {
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }

                }

            }

            @Override
            public void onFailure(Call<MatchListModel> call, Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void callAddUserMatchRequestAPI(String userId, String action) {

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("toUserID", userId);
        hashMap.put("action", action);
        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(getActivity());
        pd.show();
        SessionPref pref = SessionPref.getInstance(getActivity());
        Call<CommonModel> call = service.addUserMatchRequest("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new Callback<CommonModel>() {
            @Override
            public void onResponse(Call<CommonModel> call, Response<CommonModel> response) {
                pd.cancel();
                if (response.code() == 200) {
                    if (response.body().getStatus() == 1) {
                        clsCommon.showDialogMsgfrag(getActivity(), "PlayDate", response.body().getMessage(), "Ok");
                    }
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
                pd.cancel();
                Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void setPages(ArrayList<MatchListUser> lstusers) {
        manager = new CardStackLayoutManager(getActivity(), new CardStackListener() {
            @Override
            public void onCardDragging(Direction direction, float ratio) {
                Log.d("OnCardDragging: ", "onCardDragging: d=" + direction.name() + " ratio=" + ratio);

            }

            @Override
            public void onCardSwiped(Direction direction) {
                Log.d("OnCardSwiped: ", "onCardSwiped: p=" + manager.getTopPosition() + " d=" + direction);
                if (direction == Direction.Right) {
                    Log.e("Right", "Right");
                }
                if (direction == Direction.Top) {
                    Log.e("Top", "Top");
                }
                if (direction == Direction.Left) {
                    Log.e("Left", "Left");
                }
                if (direction == Direction.Bottom) {
                    Log.e("Bottom", "Bottom");
                }


//                if (manager.getTopPosition() == adapter.getItemCount() - 5) {
//                    paginate();
//                }

            }

            @Override
            public void onCardRewound() {
                Log.d("OnCardRewoundTAG", "onCardRewound: " + manager.getTopPosition());
            }

            @Override
            public void onCardCanceled() {
                Log.d("onCardCanceledTAG", "onCardRewound: " + manager.getTopPosition());

            }

            @Override
            public void onCardAppeared(View view, int position) {
                TextView name = view.findViewById(R.id.item_name);
                Log.d("onCardAppearedTAG", "onCardAppeared: " + position + ", nama: " + name.getText());
            }

            @Override
            public void onCardDisappeared(View view, int position) {
                TextView name = view.findViewById(R.id.item_name);
                Log.d("onCardDisappearedTAG", "onCardAppeared: " + position + ", nama: " + name.getText());
            }
        });

        manager.setStackFrom(StackFrom.None);
        manager.setVisibleCount(5);
        manager.setTranslationInterval(8.0f);
        manager.setScaleInterval(0.95f);
        manager.setSwipeThreshold(0.3f);
        manager.setMaxDegree(20.0f);
        manager.setDirections(Direction.FREEDOM);
        manager.setCanScrollHorizontal(true);
        manager.setSwipeableMethod(SwipeableMethod.Manual);
        manager.setOverlayInterpolator(new LinearInterpolator());
        adapter = new TinderSwipeAdapter(lstusers, lst_interest, itemClick);
        cardStackView.setLayoutManager(manager);
        cardStackView.setAdapter(adapter);
        cardStackView.setItemAnimator(new DefaultItemAnimator());
    }

    private void paginate() {
        List<MatchListUser> oldList = adapter.getList();
        List<MatchListUser> newList = new ArrayList<>(adapter.tinder_list);
        TinderSwipeCallback callback = new TinderSwipeCallback(oldList, newList);
        DiffUtil.DiffResult hasil = DiffUtil.calculateDiff(callback);
        adapter.setList(newList);
        hasil.dispatchUpdatesTo(adapter);
    }
}
