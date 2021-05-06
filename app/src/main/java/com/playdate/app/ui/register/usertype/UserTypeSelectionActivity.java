package com.playdate.app.ui.register.usertype;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.playdate.app.R;
import com.playdate.app.databinding.ActivityUserTypeBinding;
import com.playdate.app.ui.register.RegisterActivity;
import com.playdate.app.util.common.CommonClass;

public class UserTypeSelectionActivity extends AppCompatActivity {

    UserTypeViewModel viewModel;
    ActivityUserTypeBinding binding;

    boolean once = false;
    int selectedUserType = -1;
    CommonClass clsCommon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new UserTypeViewModel();
        clsCommon = CommonClass.getInstance();
        binding = DataBindingUtil.setContentView(UserTypeSelectionActivity.this, R.layout.activity_user_type);
        binding.setLifecycleOwner(this);
        binding.setUserTypeViewModel(viewModel);

        viewModel.onBusinessClick().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean click) {
                selectedUserType = 1;
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
        viewModel.OnSingleClick().observe(this, click -> {
            selectedUserType = 0;
            binding.btnTaken.setBackground(getDrawable(R.drawable.normal_btn_back));
            binding.btnSingle.setBackground(getDrawable(R.drawable.selected_btn_back));
            binding.ivNext.setVisibility(View.VISIBLE);
//                if (!once) {
//                    Animation fadeInAnimation = AnimationUtils.loadAnimation(RelationActivity.this, R.anim.slide_in_left);
//                    binding.ivNext.startAnimation(fadeInAnimation);
//                    once=true;
//                }
        });

        viewModel.OnNextClick().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean click) {
                if (selectedUserType == -1) {
                    clsCommon.showDialogMsg(UserTypeSelectionActivity.this, "PlayDate", "Please select relationship", "Ok");
                } else {
                    nextPage();
                }


            }
        });

        viewModel.onBackClick().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean click) {
                finish();
            }
        });
    }

    private void nextPage() {
        Intent mIntent =new Intent(UserTypeSelectionActivity.this, RegisterActivity.class);
        mIntent.putExtra("userType",selectedUserType==0?"Person":"Business");
        startActivity(mIntent);
        finish();
    }

}
