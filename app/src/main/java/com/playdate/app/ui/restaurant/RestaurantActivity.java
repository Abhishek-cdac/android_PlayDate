package com.playdate.app.ui.restaurant;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.data.api.GetDataService;
import com.playdate.app.data.api.RetrofitClientInstance;
import com.playdate.app.databinding.ActivityRestaurantBinding;
import com.playdate.app.model.LoginResponse;
import com.playdate.app.model.RestMain;
import com.playdate.app.ui.dashboard.DashboardActivity;
import com.playdate.app.ui.restaurant.adapter.Restaurant;
import com.playdate.app.ui.restaurant.adapter.RestaurantAdapter;
import com.playdate.app.util.common.CommonClass;
import com.playdate.app.util.common.TransparentProgressDialog;
import com.playdate.app.util.customcamera.otalia.CameraActivity;
import com.playdate.app.util.session.SessionPref;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantActivity extends AppCompatActivity {
    RestaurantViewModel viewModel;
    ActivityRestaurantBinding binding;

    ArrayList<Restaurant> rest_list;
    RestaurantAdapter adapter;
    CommonClass clsCommon;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new RestaurantViewModel();
        clsCommon = CommonClass.getInstance();
        binding = DataBindingUtil.setContentView(RestaurantActivity.this, R.layout.activity_restaurant);
        binding.setLifecycleOwner(this);
        binding.setRestaurantViewModel(viewModel);

        getRest();

        // bind RecyclerView
        recyclerView = binding.recyclerviewInterest;
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setHasFixedSize(true);
//        recyclerView.addItemDecoration(new SpacesItemDecoration(15));


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


        viewModel.OnNextClick().observe(RestaurantActivity.this, aBoolean -> {
            callAPI();

        });
        viewModel.onBackClick().observe(RestaurantActivity.this, aBoolean -> finish());

    }

    private void callAPI() {
        if (rest_list == null) {
            return;
        }

        int count = 0;
        String Selected = "";
        for (int i = 0; i < rest_list.size(); i++) {
            if (rest_list.get(i).isSelected()) {
                count++;
                if (Selected.isEmpty()) {
                    Selected = rest_list.get(i).get_id();
                } else {
                    Selected = Selected + "," + rest_list.get(i).get_id();
                }
            }
        }

        if (count == 0) {
            clsCommon.showDialogMsg(RestaurantActivity.this, "PlayDate", "Please select at least 1 restaurant", "Ok");
            return;
        }


        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
//        String bio = viewModel.BioText.getValue();
        hashMap.put("restaurants", Selected);// format 1990-08-12
        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(this);
        pd.show();
        SessionPref pref = SessionPref.getInstance(this);
//        Toast.makeText(this, ""+pref.getStringVal(SessionPref.LoginUsertoken), Toast.LENGTH_SHORT).show();


        Call<LoginResponse> call = service.updateProfile("Bareer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        String finalSelected = Selected;
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                pd.cancel();
                if (response.code() == 200) {
                    if (response.body().getStatus() == 1) {
                        pref.saveStringKeyVal(SessionPref.LoginUserrestaurants, finalSelected);
                        startActivity(new Intent(RestaurantActivity.this, CameraActivity
                                .class));
                    } else {
                        clsCommon.showDialogMsg(RestaurantActivity.this, "PlayDate", response.body().getMessage(), "Ok");
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsg(RestaurantActivity.this, "PlayDate", jObjError.getString("message").toString(), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(RestaurantActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }

                }


            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(RestaurantActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void filter(String str) {
        if (str.length() > 0) {
            ArrayList<Restaurant> filteredNames = new ArrayList<>();
            for (Restaurant s : rest_list) {
                if (s.getName().toLowerCase().contains(str.toLowerCase())) {
                    filteredNames.add(s);
                }
            }
            adapter.updateList(filteredNames);
            adapter.notifyDataSetChanged();
        } else {
            adapter.updateList(rest_list);
            adapter.notifyDataSetChanged();
        }

    }

    private void getRest() {


        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("limit", "50");// format 1990-08-12
        hashMap.put("pageNo", "1");// format 1990-08-12
        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(this);
        pd.show();
        SessionPref pref = SessionPref.getInstance(this);
//        Toast.makeText(this, ""+pref.getStringVal(SessionPref.LoginUsertoken), Toast.LENGTH_SHORT).show();


        Call<RestMain> call = service.restaurants("Bareer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new Callback<RestMain>() {
            @Override
            public void onResponse(Call<RestMain> call, Response<RestMain> response) {
                pd.cancel();
                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {
                        rest_list = response.body().getLst();
                        if (rest_list == null) {
                            rest_list = new ArrayList<>();
                        }
                        binding.ivNext.setVisibility(View.VISIBLE);
                        adapter = new RestaurantAdapter(rest_list);
                        recyclerView.setAdapter(adapter);
                    } else {
                        clsCommon.showDialogMsg(RestaurantActivity.this, "PlayDate", response.body().getMessage(), "Ok");
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsg(RestaurantActivity.this, "PlayDate", jObjError.getString("message").toString(), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(RestaurantActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }

                }


            }

            @Override
            public void onFailure(Call<RestMain> call, Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(RestaurantActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
