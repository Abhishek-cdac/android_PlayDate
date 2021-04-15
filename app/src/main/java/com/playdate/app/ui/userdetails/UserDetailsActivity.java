package com.playdate.app.ui.userdetails;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.playdate.app.data.github.GithubDataSourceImpl;
import com.playdate.app.usecase.GetUserUseCase;
import com.playdate.app.util.executor.ExecutorProviderImpl;
import com.playdate.app.util.logging.LoggingHelperImpl;
import com.playdate.app.R;
import com.playdate.app.databinding.ActivityUserDetailsBinding;

public class UserDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_USERNAME = "username";

    private UserDetailsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String username = getIntent().getStringExtra(EXTRA_USERNAME);

        UserDetailsViewModelFactory viewModelFactory =
                new UserDetailsViewModelFactory(
                        username,
                        new GetUserUseCase(
                                ExecutorProviderImpl.getInstance(),
                                GithubDataSourceImpl.getInstance(this)
                        ),
                        LoggingHelperImpl.getInstance()
                );

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(UserDetailsViewModel.class);

        ActivityUserDetailsBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_user_details);
        binding.setLifecycleOwner(this);
        binding.setViewModel(viewModel);

        viewModel.getOpenBrowserEvent().observe(this, event -> {
            if (!event.hasBeenHandled()) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(event.getContentIfNotHandled()));

                startActivity(intent);
            }
        });

        viewModel.getShowToastEvent().observe(this, event -> {
            if (!event.hasBeenHandled()) {
                Toast.makeText(this, event.getContentIfNotHandled(), Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.getCloseActivityEvent().observe(this, event -> finish());
    }
}
