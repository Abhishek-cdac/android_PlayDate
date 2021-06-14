package com.playdate.app.ui.register.interest;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.data.api.GetDataService;
import com.playdate.app.data.api.RetrofitClientInstance;
import com.playdate.app.databinding.ActivityInterestBinding;
import com.playdate.app.model.Interest;
import com.playdate.app.model.InterestsMain;
import com.playdate.app.model.LoginResponse;
import com.playdate.app.ui.register.bio.BioActivity;
import com.playdate.app.ui.register.interest.adapter.InterestAdapter;
import com.playdate.app.ui.restaurant.RestaurantActivity;
import com.playdate.app.util.common.CommonClass;
import com.playdate.app.util.common.SpacesItemDecoration;
import com.playdate.app.util.common.TransparentProgressDialog;
import com.playdate.app.util.session.SessionPref;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InterestActivity extends AppCompatActivity implements InterestAdapter.InterestAdapterListner {

    InterestViewModel viewModel;
    ActivityInterestBinding binding;

    ArrayList<Interest> lst_interest;
    InterestAdapter adapter;
    Intent mIntent;
    RecyclerView recyclerView;
    RelativeLayout rl_interest_bg;
    CommonClass clsCommon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        clsCommon = CommonClass.getInstance();
        viewModel = new InterestViewModel();
        binding = DataBindingUtil.setContentView(InterestActivity.this, R.layout.activity_interest);
        binding.setLifecycleOwner(this);

        binding.setInterestViewModel(viewModel);
        rl_interest_bg = findViewById(R.id.rl_interest_bg);
        recyclerView = binding.recyclerviewInterest;
        recyclerView.setLayoutManager(new GridLayoutManager(InterestActivity.this, 2));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new SpacesItemDecoration(5));
        mIntent = getIntent();
        getInterest();


      /*  binding.edtSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

        rl_interest_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("linear", "onClick:");
                clsCommon.hideKeyboard(v, InterestActivity.this);
            }
        });
        binding.edtSearch.addTextChangedListener(new TextWatcher() {
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

        viewModel.OnNextClick().observe(InterestActivity.this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {

//                    startActivity(new Intent(InterestActivity.this, RestaurantActivity
//                            .class));
                callSaveAPI();

//                }

            }
        });
    }

    private void callSaveAPI() {

        if (null == lst_interest) {
            return;
        }
        String selected = "";
        String selectedText = "";
        int count = 0;
        for (int i = 0; i < lst_interest.size(); i++) {
            if (lst_interest.get(i).isSelected()) {
                count++;
                if (selected.isEmpty()) {
                    selected = lst_interest.get(i).get_id();
                    selectedText = lst_interest.get(i).getName();
                } else {
                    selected = selected + "," + lst_interest.get(i).get_id();
                    selectedText = selectedText + "," + lst_interest.get(i).getName();
                }
            }
        }

        if (count == 0) {
            clsCommon.showDialogMsg(InterestActivity.this, "PlayDate", "Please select at least 2 interests", "Ok");
            return;
        } else if (count < 2) {
            clsCommon.showDialogMsg(InterestActivity.this, "PlayDate", "Please select at least 2 interests", "Ok");
            return;
        } else if (count > 10) {
            clsCommon.showDialogMsg(InterestActivity.this, "PlayDate", "You can select max 10 interests", "Ok");
            return;
        }

        SessionPref pref = SessionPref.getInstance(this);

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("userId", pref.getStringVal(SessionPref.LoginUserID));
        hashMap.put("interested", selected);
        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(this);
        pd.show();
//        SessionPref pref = SessionPref.getInstance(this);

        Call<LoginResponse> call = service.updateProfile("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        String finalSelected = selected;
        String finalSelectedText = selectedText;
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                pd.cancel();
                if (response.code() == 200) {
                    if (response.body().getStatus() == 1) {
                        pref.saveStringKeyVal(SessionPref.LoginUserinterested, finalSelectedText);
                        pref.saveStringKeyVal(SessionPref.LoginUserInterestsIDS, finalSelected);
                        if (mIntent.getBooleanExtra("fromProfile", false)) {
                            Intent mIntent = new Intent();
                            setResult(409, mIntent);
                            finish();
                        } else {
                            startActivity(new Intent(InterestActivity.this, RestaurantActivity
                                    .class));
                        }

                    } else {
                        clsCommon.showDialogMsg(InterestActivity.this, "PlayDate", response.body().getMessage(), "Ok");
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsg(InterestActivity.this, "PlayDate", jObjError.getString("message").toString(), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(InterestActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }

                }


            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(InterestActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getInterest() {


        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("limit", "50");// format 1990-08-12
        hashMap.put("pageNo", "1");// format 1990-08-12
        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(this);
        pd.show();
        SessionPref pref = SessionPref.getInstance(this);
//        Toast.makeText(this, ""+pref.getStringVal(SessionPref.LoginUsertoken), Toast.LENGTH_SHORT).show();


        Call<InterestsMain> call = service.interested("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
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

                        if (mIntent.getBooleanExtra("fromProfile", false)) {

                            String interestList[] = pref.getStringVal(SessionPref.LoginUserInterestsIDS).split(",");

                            for (int i = 0; i < lst_interest.size(); i++) {
                                for (String s : interestList) {
                                    if (s.trim().equals(lst_interest.get(i).get_id())) {
                                        lst_interest.get(i).setSelected(true);
                                    }
                                }
                            }


                        }


                        binding.ivNext.setVisibility(View.VISIBLE);
                        adapter = new InterestAdapter(lst_interest);
                        recyclerView.setAdapter(adapter);
                    } else {
                        clsCommon.showDialogMsg(InterestActivity.this, "PlayDate", response.body().getMessage(), "Ok");
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsg(InterestActivity.this, "PlayDate", jObjError.getString("message").toString(), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(InterestActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }

                }


            }

            @Override
            public void onFailure(Call<InterestsMain> call, Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(InterestActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void filter(String str) {
//        if (str.length() > 0) {
//            ArrayList<Interest> filteredNames = new ArrayList<>();
//            for (Interest s : lst_interest) {
//                if (s.getName().toLowerCase().contains(str.toLowerCase())) {
//                    filteredNames.add(s);
//                }
//            }
//            adapter.updateList(filteredNames);
//            adapter.notifyDataSetChanged();
//        } else {
//            adapter.updateList(lst_interest);
//            adapter.notifyDataSetChanged();
//        }


    }

    @Override
    public void onInterestSelected(Interest interest) {
        Log.e("filter interest", "" + interest);
    }
}
