package com.playdate.app.ui.register.interest;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.databinding.ActivityInterestBinding;
import com.playdate.app.model.Interest;
import com.playdate.app.ui.register.interest.adapter.InterestAdapter;
import com.playdate.app.ui.register.relationship.RelationActivity;
import com.playdate.app.ui.restaurant.RestaurantActivity;
import com.playdate.app.util.common.SpacesItemDecoration;

import java.util.ArrayList;

public class InterestActivity extends AppCompatActivity {

    InterestViewModel viewModel;
    ActivityInterestBinding binding;

    ArrayList<Interest> lst_interest = new ArrayList();
    InterestAdapter adapter;
    Intent mIntent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new InterestViewModel();
        binding = DataBindingUtil.setContentView(InterestActivity.this, R.layout.activity_interest);
        binding.setLifecycleOwner(this);
        binding.setInterestViewModel(viewModel);
        mIntent=getIntent();
        init();

        // bind RecyclerView
        RecyclerView recyclerView = binding.recyclerviewInterest;
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new SpacesItemDecoration(5));
         adapter = new InterestAdapter(lst_interest);
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

        viewModel.OnNextClick().observe(InterestActivity.this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(mIntent.getBooleanExtra("fromProfile", false)){
                    Intent mIntent=new Intent();
                    setResult(409,mIntent);
                    finish();
                }else{
                    startActivity(new Intent(InterestActivity.this, RestaurantActivity
                            .class));
                }

            }
        });
    }

    private void init() {
            lst_interest.add(new Interest("Neflix", false));
            lst_interest.add(new Interest("Football", false));
            lst_interest.add(new Interest("Anime", false));
            lst_interest.add(new Interest("Manga", false));
            lst_interest.add(new Interest("Swimmng", false));
            lst_interest.add(new Interest("Wine Testing", false));
            lst_interest.add(new Interest("Soccer", false));
            lst_interest.add(new Interest("Backet ball", false));
            lst_interest.add(new Interest("Shopping", false));
            lst_interest.add(new Interest("Design", false));
            lst_interest.add(new Interest("Gym", false));
            lst_interest.add(new Interest("Reading", false));
            lst_interest.add(new Interest("Jogging", false));
            lst_interest.add(new Interest("Dinning", false));
            lst_interest.add(new Interest("Drawing", false));
            lst_interest.add(new Interest("Cooking", false));

    }

    private void filter(String str) {
        if(str.length()>0){
            ArrayList<Interest> filteredNames = new ArrayList<>();
            for (Interest s : lst_interest) {
                if (s.getInterest().toLowerCase().contains(str.toLowerCase())) {
                    filteredNames.add(s);
                }
            }
            adapter.updateList(filteredNames);
            adapter.notifyDataSetChanged();
        }else{
            adapter.updateList(lst_interest);
            adapter.notifyDataSetChanged();
        }




    }
}
