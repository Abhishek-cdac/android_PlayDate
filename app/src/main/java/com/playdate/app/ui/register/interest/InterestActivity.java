package com.playdate.app.ui.register.interest;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.databinding.ActivityInterestBinding;
import com.playdate.app.ui.register.interest.adapter.InterestAdapter;
import com.playdate.app.ui.register.relationship.RelationActivity;
import com.playdate.app.ui.restaurant.RestaurantActivity;
import com.playdate.app.util.common.SpacesItemDecoration;

public class InterestActivity extends AppCompatActivity {

    InterestViewModel viewModel;
    ActivityInterestBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new InterestViewModel();
        binding = DataBindingUtil.setContentView(InterestActivity.this, R.layout.activity_interest);
        binding.setLifecycleOwner(this);
        binding.setInterestViewModel(viewModel);

        // bind RecyclerView
        RecyclerView recyclerView = binding.recyclerviewInterest;
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new SpacesItemDecoration(5));
        InterestAdapter adapter = new InterestAdapter();
        recyclerView.setAdapter(adapter);
        viewModel.OnNextClick().observe(InterestActivity.this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                startActivity(new Intent(InterestActivity.this, RestaurantActivity
                        .class));
            }
        });
    }
}
