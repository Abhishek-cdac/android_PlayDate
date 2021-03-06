package com.playdate.app.ui.card_swipe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
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
import com.playdate.app.ui.chat.request.Onclick;
import com.playdate.app.util.common.CommonClass;
import com.playdate.app.util.common.TransparentProgressDialog;
import com.playdate.app.util.session.SessionPref;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeableMethod;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragCardSwipe extends Fragment {

    private CardStackLayoutManager manager;
    public TinderSwipeAdapter adapter;
    public ArrayList<Interest> lst_interest;
    private Onclick itemClick;
    private CardStackView cardStackView;
    private FragmentActivity mContext;


    private CommonClass clsCommon;

    public FragCardSwipe() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tinder_swipe, container, false);
        clsCommon = CommonClass.getInstance();
        cardStackView = view.findViewById(R.id.card_stack_view);
        mContext = getActivity();
        ConstraintLayout cl_page = view.findViewById(R.id.cl_page);

        itemClick = new Onclick() {
            @Override
            public void onItemClick(View view, int position, int value) {
            }

            @Override
            public void onItemClicks(View view, int position, int value, String s) {
                try {
                    if (value == 13) {
                        callAddUserMatchRequestAPI(s, "Like");

                    } else if (value == 14) {
                        callAddUserMatchRequestAPI(s, "Unlike");

                    } else if (value == 15) {
                        callchatRequestApi(s);

                    }
                    lstUsers.remove(0);
                    adapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
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


        int height = new CommonClass().getScreenHeight(mContext);

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

    private int PageNumber = 1;

    private void getInterest() {


        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("limit", "100");
        hashMap.put("pageNo", "1");
        SessionPref pref = SessionPref.getInstance(mContext);

        Call<InterestsMain> call = service.interested("Bareer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new Callback<InterestsMain>() {
            @Override
            public void onResponse(@NotNull Call<InterestsMain> call, @NotNull Response<InterestsMain> response) {
                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {
                        lst_interest = response.body().getLst();
                        if (lst_interest == null) {
                            lst_interest = new ArrayList<>();
                        }
                        callAPI();

                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<InterestsMain> call, @NotNull Throwable t) {
                t.printStackTrace();
            }
        });


    }

    private void callAPI() {
        if (PageNumber == -1) {
            return;
        }

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("limit", "100");
        hashMap.put("pageNo", "" + PageNumber);
        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(mContext);
        pd.show();
        SessionPref pref = SessionPref.getInstance(mContext);
        Call<MatchListModel> call = service.getUserMatchList("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new Callback<MatchListModel>() {
            @Override
            public void onResponse(@NotNull Call<MatchListModel> call, @NotNull Response<MatchListModel> response) {
                pd.cancel();
                if (response.code() == 200) {
                    if (Objects.requireNonNull(response.body()).getStatus() == 1) {

                        setPages(response.body().getUsers());
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<MatchListModel> call, @NonNull Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(mContext, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callchatRequestApi(String s) {

        SessionPref pref = SessionPref.getInstance(mContext);
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("userId", pref.getStringVal(SessionPref.LoginUserID));
        hashMap.put("toUserId", s);
        Call<CommonModel> call = service.addChatRequest("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new Callback<CommonModel>() {
            @Override
            public void onResponse(@NonNull Call<CommonModel> call, @NonNull Response<CommonModel> response) {
                if (response.code() == 200) {
                    assert response.body() != null;
                    clsCommon.showDialogMsgFrag(mContext, "PlayDate", response.body().getMessage(), "Ok");

                } else {
                    try {
                        JSONObject jObjError = new JSONObject(Objects.requireNonNull(response.errorBody()).string());
                        clsCommon.showDialogMsgFrag(mContext, "PlayDate", jObjError.getString("message"), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(mContext, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<CommonModel> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }


    private void callAddUserMatchRequestAPI(String userId, String action) {

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("toUserID", userId);
        hashMap.put("action", action);
        SessionPref pref = SessionPref.getInstance(mContext);
        Call<CommonModel> call = service.addUserMatchRequest("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new Callback<CommonModel>() {
            @Override
            public void onResponse(@NonNull Call<CommonModel> call, @NonNull Response<CommonModel> response) {
            }

            @Override
            public void onFailure(@NonNull Call<CommonModel> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }

    ArrayList<MatchListUser> lstUsers = new ArrayList<>();

    private void setPages(ArrayList<MatchListUser> lst) {
        if (PageNumber == 1) {
            PageNumber = PageNumber + 1;
            lstUsers.addAll(lst);


            manager = new CardStackLayoutManager(mContext, new CardStackListener() {
                @Override
                public void onCardDragging(Direction direction, float ratio) {

                }

                @Override
                public void onCardSwiped(Direction direction) {
                    try {
                        String userID = lstUsers.get(manager.getTopPosition()).get_id();

                        String likeDisLike = "Like";
                        if (direction == Direction.Right) {
                            likeDisLike = "Like";
                        }
                        if (direction == Direction.Top) {
                            likeDisLike = "Like";
                        }
                        if (direction == Direction.Left) {
                            likeDisLike = "Unlike";
                        }
                        if (direction == Direction.Bottom) {
                            likeDisLike = "Unlike";
                        }
                        callAddUserMatchRequestAPI(userID, likeDisLike);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onCardRewound() {
                }

                @Override
                public void onCardCanceled() {

                }

                @Override
                public void onCardAppeared(View view, int position) {
                }

                @Override
                public void onCardDisappeared(View view, int position) {

                    if (adapter.getItemCount() - 1 == position) {
                        callAPI();
                    }

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
            adapter = new TinderSwipeAdapter(lstUsers, lst_interest, itemClick);
            cardStackView.setLayoutManager(manager);
            cardStackView.setAdapter(adapter);
            cardStackView.setItemAnimator(new DefaultItemAnimator());
        } else {
            if (lst.isEmpty()) {
                PageNumber = -1;
                return;
            }
            paginate(lst);
            PageNumber = PageNumber + 1;
        }
    }

    private void paginate(ArrayList<MatchListUser> lst) {
        List<MatchListUser> oldList = adapter.getList();

        TinderSwipeCallback callback = new TinderSwipeCallback(oldList, lst);
        DiffUtil.DiffResult hasil = DiffUtil.calculateDiff(callback);
        adapter.setList(lst);
        hasil.dispatchUpdatesTo(adapter);
    }

}
