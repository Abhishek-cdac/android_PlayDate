package com.playdate.app.ui.register.interest;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.data.api.GetDataService;
import com.playdate.app.data.api.RetrofitClientInstance;
import com.playdate.app.databinding.ActivityInterestBinding;
import com.playdate.app.model.Interest;
import com.playdate.app.model.InterestsMain;
import com.playdate.app.model.LoginResponse;
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
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InterestActivity extends AppCompatActivity implements InterestAdapter.InterestAdapterListner {

    private ActivityInterestBinding binding;
    private ArrayList<Interest> lst_interest;
    private InterestAdapter adapter;
    private Intent mIntent;
    private RecyclerView recyclerView;
    private CommonClass clsCommon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        clsCommon = CommonClass.getInstance();
        InterestViewModel viewModel = new InterestViewModel();
        binding = DataBindingUtil.setContentView(InterestActivity.this, R.layout.activity_interest);
        binding.setLifecycleOwner(this);

        binding.setInterestViewModel(viewModel);
        RelativeLayout rl_interest_bg = findViewById(R.id.rl_interest_bg);
        recyclerView = binding.recyclerviewInterest;
        recyclerView.setLayoutManager(new GridLayoutManager(InterestActivity.this, 2));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new SpacesItemDecoration(5));
        mIntent = getIntent();
        getInterest();


        rl_interest_bg.setOnClickListener(v -> clsCommon.hideKeyboard(v, InterestActivity.this));

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


        viewModel.OnNextClick().observe(InterestActivity.this, aBoolean -> callSaveAPI());
    }

    private void callSaveAPI() {

        if (null == lst_interest) {
            return;
        }
        StringBuilder selected = new StringBuilder();
        StringBuilder selectedText = new StringBuilder();
        int count = 0;
        for (int i = 0; i < lst_interest.size(); i++) {
            if (lst_interest.get(i).isSelected()) {
                count++;
                if (selected.length() == 0) {
                    selected = new StringBuilder(lst_interest.get(i).get_id());
                    selectedText = new StringBuilder(lst_interest.get(i).getName());
                } else {
                    selected.append(",").append(lst_interest.get(i).get_id());
                    selectedText.append(",").append(lst_interest.get(i).getName());
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
        hashMap.put("interested", selected.toString());
        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(this);
        pd.show();
//        SessionPref pref = SessionPref.getInstance(this);

        Call<LoginResponse> call = service.updateProfile("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        String finalSelected = selected.toString();
        String finalSelectedText = selectedText.toString();
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                pd.cancel();
                if (response.code() == 200) {
                    if (Objects.requireNonNull(response.body()).getStatus() == 1) {
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
                        JSONObject jObjError = new JSONObject(Objects.requireNonNull(response.errorBody()).string());
                        clsCommon.showDialogMsg(InterestActivity.this, "PlayDate", jObjError.getString("message"), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(InterestActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }

                }


            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(InterestActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getInterest() {


        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("limit", "100");// format 1990-08-12
        hashMap.put("pageNo", "1");// format 1990-08-12
        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(this);
        pd.show();
        SessionPref pref = SessionPref.getInstance(this);
//        Toast.makeText(this, ""+pref.getStringVal(SessionPref.LoginUsertoken), Toast.LENGTH_SHORT).show();


        Call<InterestsMain> call = service.interested("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new Callback<InterestsMain>() {
            @Override
            public void onResponse(@NonNull Call<InterestsMain> call, @NonNull Response<InterestsMain> response) {
                pd.cancel();
                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {
                        lst_interest = response.body().getLst();
                        if (lst_interest == null) {
                            lst_interest = new ArrayList<>();
                        }

                        if (mIntent.getBooleanExtra("fromProfile", false)) {

                            String[] interestList = pref.getStringVal(SessionPref.LoginUserInterestsIDS).split(",");

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
                        JSONObject jObjError = new JSONObject(Objects.requireNonNull(response.errorBody()).string());
                        clsCommon.showDialogMsg(InterestActivity.this, "PlayDate", jObjError.getString("message"), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(InterestActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }

                }


            }

            @Override
            public void onFailure(@NonNull Call<InterestsMain> call, @NonNull Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(InterestActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });


    }


    @Override
    public void onInterestSelected(Interest interest) {
    }
}
