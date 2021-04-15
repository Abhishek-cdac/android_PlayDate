package com.playdate.app.ui.userdetails;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.playdate.app.usecase.GetUserUseCase;
import com.playdate.app.util.logging.LoggingHelper;

public class UserDetailsViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private String username;

    private GetUserUseCase getUserUseCase;

    private LoggingHelper loggingHelper;

    public UserDetailsViewModelFactory(String username, GetUserUseCase getUserUseCase, LoggingHelper loggingHelper) {
        this.username = username;
        this.getUserUseCase = getUserUseCase;
        this.loggingHelper = loggingHelper;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        // noinspection unchecked
        return (T) new UserDetailsViewModel(username, getUserUseCase, loggingHelper);
    }
}
