package com.playdate.app.ui.register.interest;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
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

public class InterestActivity extends AppCompatActivity {

    InterestViewModel viewModel;
    ActivityInterestBinding binding;

    ArrayList<Interest> lst_interest;
    InterestAdapter adapter;
    Intent mIntent;
    RecyclerView recyclerView;
    CommonClass clsCommon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        clsCommon = CommonClass.getInstance();
        viewModel = new InterestViewModel();
        binding = DataBindingUtil.setContentView(InterestActivity.this, R.layout.activity_interest);
        binding.setLifecycleOwner(this);
        binding.setInterestViewModel(viewModel);
        recyclerView = binding.recyclerviewInterest;
        recyclerView.setLayoutManager(new GridLayoutManager(InterestActivity.this, 2));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new SpacesItemDecoration(5));
        mIntent = getIntent();
        getInterest();


        binding.edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());

            }
        });

        viewModel.OnNextClick().observe(InterestActivity.this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (mIntent.getBooleanExtra("fromProfile", false)) {
                    Intent mIntent = new Intent();
                    setResult(409, mIntent);
                    finish();
                } else {
//                    startActivity(new Intent(InterestActivity.this, RestaurantActivity
//                            .class));
                    callSaveAPI();

                }

            }
        });
    }

    private void callSaveAPI() {

        if (null == lst_interest) {
            return;
        }
        String selected = "";
        int count = 0;
        for (int i = 0; i < lst_interest.size(); i++) {
            if (lst_interest.get(i).isSelected()) {
                count++;
                if (selected.isEmpty()) {
                    selected = lst_interest.get(i).get_id();
                } else {
                    selected = selected + "," + lst_interest.get(i).get_id();
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


        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("interested", selected);// format 1990-08-12
        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(this);
        pd.show();
        SessionPref pref = SessionPref.getInstance(this);
//        Toast.makeText(this, ""+pref.getStringVal(SessionPref.LoginUsertoken), Toast.LENGTH_SHORT).show();


        Call<LoginResponse> call = service.updateProfile("Bareer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        String finalSelected = selected;
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                pd.cancel();
                if (response.code() == 200) {
                    if (response.body().getStatus() == 1) {
                        pref.saveStringKeyVal(SessionPref.LoginUserinterested, finalSelected);
                        startActivity(new Intent(InterestActivity.this, RestaurantActivity
                                .class));
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
}
