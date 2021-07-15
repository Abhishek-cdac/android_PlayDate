package com.playdate.app.ui.date.games;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.data.api.GetDataService;
import com.playdate.app.data.api.RetrofitClientInstance;
import com.playdate.app.model.StoreData;
import com.playdate.app.model.StoreItems;
import com.playdate.app.model.StoreModel;
import com.playdate.app.ui.chat.request.Onclick;
import com.playdate.app.ui.dashboard.DashboardActivity;
import com.playdate.app.ui.date.adapter.StoreDMAdapter;
import com.playdate.app.ui.date.adapter.StoreDateCoinAdpter;
import com.playdate.app.ui.date.adapter.StoreGameCoinAdapter;
import com.playdate.app.ui.date.adapter.StoreMultiplierAdapter;
import com.playdate.app.ui.interfaces.OnInnerFragmentClicks;
import com.playdate.app.util.common.CommonClass;
import com.playdate.app.util.common.TransparentProgressDialog;
import com.playdate.app.util.session.SessionPref;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragStore extends Dialog {


    ImageView cancel;
    ImageView iv_leaderboard;
    DashboardActivity ref;
    List<StoreData> lst_store_data;
    List<StoreItems> game_items;
    List<StoreItems> date_items;
    List<StoreItems> multiplier_items;
    List<StoreItems> dm_items;
    private CommonClass clsCommon;
    RecyclerView rv_game_coin;
    RecyclerView rv_date_coin;
    RecyclerView rv_multiplier;
    RecyclerView rv_dm_booster;
    private Onclick itemClick;


    public FragStore(@NonNull Context context) {
        super(context, R.style.My_Dialog);

        WindowManager.LayoutParams wlmp = getWindow().getAttributes();

        wlmp.gravity = Gravity.CENTER_HORIZONTAL;
        getWindow().setAttributes(wlmp);
        setTitle(null);
        setCancelable(true);
        setOnCancelListener(null);
        View view = LayoutInflater.from(context).inflate(
                R.layout.frag_coupon_store, null);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(view);
        callgetStore(context);

        clsCommon = CommonClass.getInstance();

        ref = new DashboardActivity();
        rv_game_coin = view.findViewById(R.id.rv_game_coin);
        rv_date_coin = view.findViewById(R.id.rv_date_coin);
        rv_multiplier = view.findViewById(R.id.rv_multiplier);
        rv_dm_booster = view.findViewById(R.id.rv_dm_booster);
        cancel = view.findViewById(R.id.cancel);
        iv_leaderboard = view.findViewById(R.id.iv_leaderboard);


        itemClick = new Onclick() {
            @Override
            public void onItemClick(View view, int position, int value) {

            }

            @Override
            public void onItemClicks(View view, int position, int value, String s) {

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

        iv_leaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ref.redirectToLeaderBoard();
//                ref.ReplaceFragWithStack(new FragGameLeaderBoard());
//                dismiss();

//                OnInnerFragmentClicks frag = (OnInnerFragmentClicks) context;
//                frag.ReplaceFrag(new FragGameLeaderBoard());
                dismiss();
            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CANCEL", "onClick: ");
                dismiss();
            }
        });

    }

    private void callgetStore(Context context) {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("limit", "10");
        hashMap.put("pageNo", "1");//Hardcode
        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(context);
        pd.show();
        SessionPref pref = SessionPref.getInstance(context);

        Call<StoreModel> call = service.getStore("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);

        call.enqueue(new Callback<StoreModel>() {
            @Override
            public void onResponse(Call<StoreModel> call, Response<StoreModel> response) {
                pd.cancel();
                if (response.code() == 200) {
                    try {
                        if (response.body().getmStatus() == 1) {
                            lst_store_data = response.body().getmData();
                            if (lst_store_data == null) {
                                lst_store_data = new ArrayList<>();
                            }

                            for (int i = 0; i < lst_store_data.size(); i++) {
                                switch (lst_store_data.get(i).getStoreType()) {
                                    case "Game Coins":
                                        game_items = lst_store_data.get(i).getStoreItems();
                                        break;
                                    case "Date Coins":
                                        date_items = lst_store_data.get(i).getStoreItems();
                                        break;
                                    case "Multiplier Boosters":
                                        multiplier_items = lst_store_data.get(i).getStoreItems();
                                        break;
                                    case "DM Boosters":
                                        dm_items = lst_store_data.get(i).getStoreItems();
                                        break;
                                }
                            }

                            RecyclerView.LayoutManager manager = new GridLayoutManager(context, 3);
                            rv_game_coin.setLayoutManager(manager);
                            RecyclerView.LayoutManager manager1 = new GridLayoutManager(context, 3);
                            rv_date_coin.setLayoutManager(manager1);
                            RecyclerView.LayoutManager manager2 = new GridLayoutManager(context, 3);
                            rv_multiplier.setLayoutManager(manager2);
                            RecyclerView.LayoutManager manager3 = new GridLayoutManager(context, 3);
                            rv_dm_booster.setLayoutManager(manager3);

                            StoreGameCoinAdapter adapter = new StoreGameCoinAdapter((ArrayList<StoreItems>) game_items, itemClick);
                            rv_game_coin.setAdapter(adapter);
                            StoreDateCoinAdpter adapter1 = new StoreDateCoinAdpter((ArrayList<StoreItems>) date_items, itemClick);
                            rv_date_coin.setAdapter(adapter1);
                            StoreMultiplierAdapter adapter2 = new StoreMultiplierAdapter((ArrayList<StoreItems>) multiplier_items, itemClick);
                            rv_multiplier.setAdapter(adapter2);
                            StoreDMAdapter adapter3 = new StoreDMAdapter((ArrayList<StoreItems>) dm_items, itemClick);
                            rv_dm_booster.setAdapter(adapter3);


                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(context, jObjError.getString("message"), Toast.LENGTH_SHORT).show();
//                        clsCommon.showDialogMsgfrag((FragmentActivity) context, "PlayDate", jObjError.getString("message"), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(context, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<StoreModel> call, Throwable t) {
                pd.cancel();
                t.printStackTrace();
                Toast.makeText(context, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();

            }
        });
    }


}


//class FragStore1 extends Fragment {
//
//    ImageView cancel;
//    ImageView iv_leaderboard;
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.frag_coupon_store, container, false);
//        RecyclerView rv_game_coin = view.findViewById(R.id.rv_game_coin);
//        RecyclerView rv_date_coin = view.findViewById(R.id.rv_date_coin);
//        RecyclerView rv_multiplier = view.findViewById(R.id.rv_multiplier);
//        RecyclerView rv_dm_booster = view.findViewById(R.id.rv_dm_booster);
//        cancel = view.findViewById(R.id.cancel);
//        iv_leaderboard = view.findViewById(R.id.iv_leaderboard);
//
//        RecyclerView.LayoutManager manager = new GridLayoutManager(getActivity(), 3);
//        rv_game_coin.setLayoutManager(manager);
//        RecyclerView.LayoutManager manager1 = new GridLayoutManager(getActivity(), 3);
//        rv_date_coin.setLayoutManager(manager1);
//        RecyclerView.LayoutManager manager2 = new GridLayoutManager(getActivity(), 3);
//        rv_multiplier.setLayoutManager(manager2);
//        RecyclerView.LayoutManager manager3 = new GridLayoutManager(getActivity(), 3);
//        rv_dm_booster.setLayoutManager(manager3);
//
//        StoreGameCoinAdapter adapter = new StoreGameCoinAdapter();
//        rv_game_coin.setAdapter(adapter);
//        StoreDateCoinAdpter adapter1 = new StoreDateCoinAdpter();
//        rv_date_coin.setAdapter(adapter1);
//        StoreMultiplierAdapter adapter2 = new StoreMultiplierAdapter();
//        rv_multiplier.setAdapter(adapter2);
//        StoreDMAdapter adapter3 = new StoreDMAdapter();
//        rv_dm_booster.setAdapter(adapter3);
//
//        iv_leaderboard.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                OnInnerFragmentClicks frag = (OnInnerFragmentClicks) getActivity();
//                frag.ReplaceFrag(new FragGameLeaderBoard());
//            }
//        });
//
//
//        cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("CANCEL", "onClick: ");
////                getActivity().finish();
//                getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//            }
//        });
//        return view;
//    }
//}
