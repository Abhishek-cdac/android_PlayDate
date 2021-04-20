package com.playdate.app.ui.register.relationship;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;
import androidx.lifecycle.Observer;

import com.playdate.app.R;
import com.playdate.app.databinding.ActivityRelationshipBinding;
import com.playdate.app.ui.register.interestin.InterestActivity;

public class RelationActivity extends AppCompatActivity {

    RelatiponShipViewModel viewModel;
    ActivityRelationshipBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new RelatiponShipViewModel();
        binding = DataBindingUtil.setContentView(RelationActivity.this, R.layout.activity_relationship);
        binding.setLifecycleOwner(this);
        binding.setRelatiponShipViewModel(viewModel);

        viewModel.OnTakenClick().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean click) {
                binding.btnTaken.setBackground(getDrawable(R.drawable.selected_btn_back));
                binding.btnSingle.setBackground(getDrawable(R.drawable.normal_btn_back));

            }
        });
        viewModel.OnSingleClick().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean click) {
                binding.btnTaken.setBackground(getDrawable(R.drawable.normal_btn_back));
                binding.btnSingle.setBackground(getDrawable(R.drawable.selected_btn_back));

            }
        });

        viewModel.OnNextClick().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean click) {
                startActivity(new Intent(RelationActivity.this, InterestActivity
                        .class));

            }
        });

        viewModel.onBackClick().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean click) {
                finish();
            }
        });
    }
}
