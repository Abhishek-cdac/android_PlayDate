//package com.playdate.app.ui.onboarding;
//
//import androidx.annotation.NonNull;
//import androidx.lifecycle.ViewModel;
//import androidx.lifecycle.ViewModelProvider;
//
//import com.playdate.app.util.logging.LoggingHelper;
//
//public class OnBoardingViewModelFactory extends ViewModelProvider.NewInstanceFactory {
//
//    private SearchUseCase searchUseCase;
//
//    private GetHistoryUseCase getHistoryUseCase;
//
//    private LoggingHelper loggingHelper;
//
//    public OnBoardingViewModelFactory(SearchUseCase searchUseCase, GetHistoryUseCase getHistoryUseCase,
//                                  LoggingHelper loggingHelper) {
//        this.searchUseCase = searchUseCase;
//        this.getHistoryUseCase = getHistoryUseCase;
//        this.loggingHelper = loggingHelper;
//    }
//
//    @NonNull
//    @Override
//    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
//        // noinspection unchecked
//        return (T) new SearchViewModel(searchUseCase, getHistoryUseCase, loggingHelper);
//    }
//}
