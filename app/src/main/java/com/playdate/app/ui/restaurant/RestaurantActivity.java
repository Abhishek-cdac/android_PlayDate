package com.playdate.app.ui.restaurant;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.databinding.ActivityRestaurantBinding;
import com.playdate.app.ui.card_swipe.TinderSwipeActivity;
import com.playdate.app.ui.dashboard.DashboardActivity;
import com.playdate.app.ui.record_video.RecordVideoActivity;
import com.playdate.app.ui.restaurant.adapter.Restaurant;
import com.playdate.app.ui.restaurant.adapter.RestaurantAdapter;

import java.util.ArrayList;

public class RestaurantActivity extends AppCompatActivity {
    RestaurantViewModel viewModel;
    ActivityRestaurantBinding binding;

    ArrayList<Restaurant> rest_list = new ArrayList<>();
    RestaurantAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new RestaurantViewModel();
        binding = DataBindingUtil.setContentView(RestaurantActivity.this, R.layout.activity_restaurant);
        binding.setLifecycleOwner(this);
        binding.setRestaurantViewModel(viewModel);

        init();

        // bind RecyclerView
        RecyclerView recyclerView = binding.recyclerviewInterest;
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setHasFixedSize(true);
//        recyclerView.addItemDecoration(new SpacesItemDecoration(15));

        adapter = new RestaurantAdapter(rest_list);
        recyclerView.setAdapter(adapter);

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


        viewModel.OnNextClick().observe(RestaurantActivity.this, aBoolean -> startActivity(new Intent(RestaurantActivity.this, DashboardActivity.class)));
        viewModel.onBackClick().observe(RestaurantActivity.this, aBoolean -> finish());

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

    private void init() {
        rest_list.add(new Restaurant(R.drawable.rest1, "Benihana grill", false));
        rest_list.add(new Restaurant(R.drawable.rest2, "olive garden", false));
        rest_list.add(new Restaurant(R.drawable.rest3, "tete restaurant", false));
        rest_list.add(new Restaurant(R.drawable.rest4, "friendly's", false));
        rest_list.add(new Restaurant(R.drawable.rest5, "the taste of mexico montezuma's", false));
        rest_list.add(new Restaurant(R.drawable.rest6, "arbys", false));
    }
}
