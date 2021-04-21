package com.playdate.app.ui.register.bio;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.playdate.app.R;
import com.playdate.app.databinding.ActivityBioBinding;
import com.playdate.app.ui.register.interestin.InterestActivity;
import com.playdate.app.ui.register.profile.UploadProfileActivity;
import com.playdate.app.ui.register.relationship.RelationActivity;

public class BioActivity extends AppCompatActivity {
    BioViewModel viewModel;
    ActivityBioBinding bioBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new BioViewModel();
        bioBinding = DataBindingUtil.setContentView(BioActivity.this, R.layout.activity_bio);
        bioBinding.setLifecycleOwner(this);
        bioBinding.setBioViewModel(viewModel);

        viewModel.OnNextClick().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean click) {
                startActivity(new Intent(BioActivity.this, UploadProfileActivity
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
