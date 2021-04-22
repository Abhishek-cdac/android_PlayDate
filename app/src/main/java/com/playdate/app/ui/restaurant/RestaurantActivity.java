package com.playdate.app.ui.restaurant;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.databinding.ActivityRestaurantBinding;
import com.playdate.app.ui.dashboard.DashboardActivity;
import com.playdate.app.ui.restaurant.adapter.RestaurantAdapter;

public class RestaurantActivity extends AppCompatActivity {
    RestaurantViewModel viewModel;
    ActivityRestaurantBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new RestaurantViewModel();
        binding = DataBindingUtil.setContentView(RestaurantActivity.this, R.layout.activity_restaurant);
        binding.setLifecycleOwner(this);
        binding.setRestaurantViewModel(viewModel);

        RestaurantAdapter adapter = new RestaurantAdapter();

        // bind RecyclerView
        RecyclerView recyclerView = binding.recyclerviewInterest;
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setHasFixedSize(true);
//        recyclerView.addItemDecoration(new SpacesItemDecoration(15));

        recyclerView.setAdapter(adapter);


        viewModel.OnNextClick().observe(RestaurantActivity.this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                startActivity(new Intent(RestaurantActivity.this, DashboardActivity.class));
            }
        });
        viewModel.onBackClick().observe(RestaurantActivity.this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                finish();
            }
        });


    }
}
