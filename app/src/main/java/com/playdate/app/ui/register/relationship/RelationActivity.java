package com.playdate.app.ui.register.relationship;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.playdate.app.R;
import com.playdate.app.databinding.ActivityRelationshipBinding;
import com.playdate.app.ui.register.interestin.InterestActivity;
import com.playdate.app.util.customcamera.otalia.CameraActivity;

public class RelationActivity extends AppCompatActivity {

    RelatiponShipViewModel viewModel;
    ActivityRelationshipBinding binding;

    boolean once = false;

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
                binding.ivNext.setVisibility(View.VISIBLE);
//                if (!once) {
//                    Animation fadeInAnimation = AnimationUtils.loadAnimation(RelationActivity.this, R.anim.slide_in_left);
//                    binding.ivNext.startAnimation(fadeInAnimation);
//                    once=true;
//                }
            }
        });
        viewModel.OnSingleClick().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean click) {
                binding.btnTaken.setBackground(getDrawable(R.drawable.normal_btn_back));
                binding.btnSingle.setBackground(getDrawable(R.drawable.selected_btn_back));
                binding.ivNext.setVisibility(View.VISIBLE);
//                if (!once) {
//                    Animation fadeInAnimation = AnimationUtils.loadAnimation(RelationActivity.this, R.anim.slide_in_left);
//                    binding.ivNext.startAnimation(fadeInAnimation);
//                    once=true;
//                }
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
