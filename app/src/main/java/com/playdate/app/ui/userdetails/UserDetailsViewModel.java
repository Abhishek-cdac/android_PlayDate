package com.playdate.app.ui.userdetails;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.playdate.app.model.User;
import com.playdate.app.ui.Event;
import com.playdate.app.usecase.GetUserUseCase;
import com.playdate.app.usecase.UseCase;
import com.playdate.app.util.logging.LoggingHelper;
import com.playdate.app.R;

public class UserDetailsViewModel extends ViewModel {

    private static final String TAG = "UserDetailsViewModel";

    private MediatorLiveData<UseCase.Result<User>> getUserResult = new MediatorLiveData<>();

    private LiveData<Boolean> isLoading;

    private LiveData<User> user;

    private LiveData<Boolean> shouldShowUserDetails;

    private MutableLiveData<Event<String>> openBrowserEvent = new MutableLiveData<>();

    private MediatorLiveData<Event<Integer>> showToastEvent = new MediatorLiveData<>();

    private MediatorLiveData<Event<Void>> closeActivityEvent = new MediatorLiveData<>();

    public UserDetailsViewModel(String username, GetUserUseCase getUserUseCase, LoggingHelper loggingHelper) {
        getUserResult.addSource(
                getUserUseCase.executeAsync(username),
                result -> {
                    if (result.status == UseCase.Result.Status.SUCCESS) {
                        loggingHelper.debug(TAG, String.format("result.data: %s", result.data));
                    } else if (result.status == UseCase.Result.Status.ERROR) {
                        loggingHelper.warn(TAG, "An error occurred while getting a user", result.error);
                    }

                    getUserResult.setValue(result);
                }
        );

        isLoading = Transformations.map(
                getUserResult,
                result -> result.status == UseCase.Result.Status.LOADING
        );

        user = Transformations.map(
                getUserResult,
                result -> result.data
        );

        shouldShowUserDetails = Transformations.map(
                getUserResult,
                result -> result.status == UseCase.Result.Status.SUCCESS
        );

        showToastEvent.addSource(
                getUserResult,
                result -> {
                    if (result.status == UseCase.Result.Status.ERROR) {
                        showToastEvent.setValue(new Event<>(R.string.an_error_occurred));
                    }
                }
        );

        closeActivityEvent.addSource(
                getUserResult,
                result -> {
                    if (result.status == UseCase.Result.Status.ERROR) {
                        closeActivityEvent.setValue(new Event<>(null));
                    }
                }
        );
    }

    public void onBlogClick() {
        User u = user.getValue();
        if (u != null) {
            openBrowserEvent.setValue(new Event<>(u.getBlog()));
        }
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<User> getUser() {
        return user;
    }

    public LiveData<Boolean> getShouldShowUserDetails() {
        return shouldShowUserDetails;
    }

    public MutableLiveData<Event<String>> getOpenBrowserEvent() {
        return openBrowserEvent;
    }

    public MediatorLiveData<Event<Integer>> getShowToastEvent() {
        return showToastEvent;
    }

    public MediatorLiveData<Event<Void>> getCloseActivityEvent() {
        return closeActivityEvent;
    }
}
