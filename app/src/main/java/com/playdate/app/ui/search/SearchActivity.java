package com.playdate.app.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.playdate.app.data.github.GithubDataSourceImpl;
import com.playdate.app.data.searchhistory.SearchHistoryDataSourceImpl;
import com.playdate.app.ui.userdetails.UserDetailsActivity;
import com.playdate.app.usecase.GetHistoryUseCase;
import com.playdate.app.usecase.SearchUseCase;
import com.playdate.app.util.executor.ExecutorProviderImpl;
import com.playdate.app.util.logging.LoggingHelperImpl;
import com.playdate.app.R;
import com.playdate.app.databinding.ActivitySearchBinding;

public class SearchActivity extends AppCompatActivity {

    private SearchViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        SearchViewModelFactory viewModelFactory =
                new SearchViewModelFactory(
                        new SearchUseCase(
                                ExecutorProviderImpl.getInstance(),
                                GithubDataSourceImpl.getInstance(this),
                                SearchHistoryDataSourceImpl.getInstance(this),
                                LoggingHelperImpl.getInstance()
                        ),
                        new GetHistoryUseCase(
                                ExecutorProviderImpl.getInstance(),
                                SearchHistoryDataSourceImpl.getInstance(this)
                        ),
                        LoggingHelperImpl.getInstance()
                );

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SearchViewModel.class);

        ActivitySearchBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        binding.setLifecycleOwner(this);
        binding.setViewModel(viewModel);

        viewModel.getOpenUserDetailsActivityEvent().observe(this, event -> {
            if (!event.hasBeenHandled()) {
                Intent intent = new Intent(this, UserDetailsActivity.class);
                intent.putExtra(UserDetailsActivity.EXTRA_USERNAME, event.getContentIfNotHandled());

                startActivity(intent);
            }
        });

        viewModel.getShowToastEvent().observe(this, event -> {
            if (!event.hasBeenHandled()) {
                Toast.makeText(SearchActivity.this, event.getContentIfNotHandled(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
