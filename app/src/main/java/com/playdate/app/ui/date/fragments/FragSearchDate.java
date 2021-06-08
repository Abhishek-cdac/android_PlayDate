package com.playdate.app.ui.date.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import com.playdate.app.model.CreateDateGetPartnerData;
import com.playdate.app.model.CreateDateGetPartnerModel;
import com.playdate.app.model.GetUserSuggestionData;
import com.playdate.app.ui.chat.request.Onclick;
import com.playdate.app.ui.date.adapter.SuggestedDateAdapter;
import com.playdate.app.ui.interfaces.OnInnerFragmentClicks;
import com.playdate.app.util.common.CommonClass;
import com.playdate.app.util.session.SessionPref;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragSearchDate  extends Fragment implements SuggestedDateAdapter.SuggestionsAdapterListner  {
    EditText edt_search;
    TextView txt_cancel;
    RecyclerView recyclerView;
    private ArrayList<GetUserSuggestionData> lst_getUserSuggestions;
    private CommonClass clsCommon;
    private Onclick itemClick;
    private SuggestedDateAdapter adapter;
    ArrayList<CreateDateGetPartnerData> lst_CreateDateGetPartner;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_date_user, container, false);


        clsCommon = CommonClass.getInstance();
        edt_search = view.findViewById(R.id.edt_search);
        txt_cancel = view.findViewById(R.id.txt_cancel);
        recyclerView = view.findViewById(R.id.recycler_view);
        itemClick = new Onclick() {
            @Override
            public void onItemClick(View view, int position, int value) {

            }

            @Override
            public void onItemClicks(View view, int position, int value, String id) {
                if (value == 10) {

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
          /*      if (i == 22) {
                    OnInnerFragmentClicks frag = (OnInnerFragmentClicks) getActivity();

                    Fragment fragment = new FragPartnerSelected();
                    Bundle bundle = new Bundle();

                    bundle.putString("profile_name", lst_CreateDateGetPartner.get(position).getUsername());
                    bundle.putString("profile_points", lst_CreateDateGetPartner.get(position).getTotalPoints());
                    bundle.putString("profile_userId", lst_CreateDateGetPartner.get(position).getId());
                    bundle.putString("profile_image", lst_CreateDateGetPartner.get(position).getProfilePicPath());

                    Log.e("SearchDate_toUserID..", "" + lst_CreateDateGetPartner.get(position).getId());

                    fragment.setArguments(bundle);
                    frag.ReplaceFrag(fragment);
                }*/
            }
        };

        txt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt_search.setText("");
//                OnFriendSelected inf = (OnFriendSelected) getApplicationContext();
//                inf.OnSuggestionClosed();


            }
        });




    /*    edt_search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });*/

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


        callCreateDateGetPartnerListAPI();

//        view.setFocusableInTouchMode(true);
//        view.requestFocus();
//        view.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if (keyCode == KeyEvent.KEYCODE_BACK) {
//                    OnFriendSelected inf = (OnFriendSelected) getApplicationContext();
//                    inf.OnSuggestionClosed();
//                    return true;
//                }
//                return false;
//            }
//        });

        return  view;

    }

    private void callCreateDateGetPartnerListAPI() {

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("limit", "50");
        hashMap.put("pageNo", "1");//Hardcode
//        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(getApplicationContext());
//        pd.show();
        SessionPref pref = SessionPref.getInstance(getActivity());
        Call<CreateDateGetPartnerModel> call = service.createDateGetPartnerList("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new Callback<CreateDateGetPartnerModel>() {
            @Override
            public void onResponse(Call<CreateDateGetPartnerModel> call, Response<CreateDateGetPartnerModel> response) {
                //  pd.cancel();
                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {

                        Log.e("CreateDate", "CreateDateGetPartnerData");
                        lst_CreateDateGetPartner = (ArrayList<CreateDateGetPartnerData>) response.body().getData();
                        if (lst_CreateDateGetPartner == null) {
                            lst_CreateDateGetPartner = new ArrayList<>();
                        }
                        Log.e("CreateDate", "" + lst_CreateDateGetPartner.size());


                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
                        adapter = new SuggestedDateAdapter(getActivity(), lst_CreateDateGetPartner, itemClick);
                        recyclerView.setAdapter(adapter);

                       /* PartnerViewPagerAdapter adapter = new PartnerViewPagerAdapter(getActivity(), lst_CreateDateGetPartner, itemClick);
                        // adapter = new PartnerViewPagerAdapter(getActivity(), lst_CreateDateGetPartner);
                        vp_partners.setAdapter(adapter);
                        vp_partners.setClipToPadding(false);
                        vp_partners.setPageMargin(-450);
*/
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsgfrag(getActivity(), "PlayDate", jObjError.getString("message").toString(), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }

                }

            }

            @Override
            public void onFailure(Call<CreateDateGetPartnerModel> call, Throwable t) {
                t.printStackTrace();
                // pd.cancel();
                Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public void onSuggestionSelected(CreateDateGetPartnerData createDateGetPartnerData) {

        Log.e("filter user", "" + createDateGetPartnerData);
    }
}