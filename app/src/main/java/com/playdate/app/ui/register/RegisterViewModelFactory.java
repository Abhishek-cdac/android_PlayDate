//package com.playdate.app.ui.register;
//
//import androidx.annotation.NonNull;
//import androidx.lifecycle.ViewModel;
//import androidx.lifecycle.ViewModelProvider;
//
//import com.playdate.app.ui.search.SearchViewModel;
//import com.playdate.app.usecase.GetHistoryUseCase;
//import com.playdate.app.usecase.RegisterUserUseCase;
//import com.playdate.app.usecase.SearchUseCase;
//import com.playdate.app.util.logging.LoggingHelper;
//
//    public class RegisterViewModelFactory extends ViewModelProvider.NewInstanceFactory {
//
//        private RegisterUserUseCase registerUserUseCase;
//
//
//        private LoggingHelper loggingHelper;
//
//        public RegisterViewModelFactory(RegisterUserUseCase registerUserUseCase,
//                                      LoggingHelper loggingHelper) {
//            this.registerUserUseCase = registerUserUseCase;
//            this.loggingHelper = loggingHelper;
//        }
//
//        @NonNull
//        @Override
//        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
//            // noinspection unchecked
//            return (T) new RegisterViewModel(registerUserUseCase, loggingHelper);
//        }
//    }
//
//
//
