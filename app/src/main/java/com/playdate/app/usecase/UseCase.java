package com.playdate.app.usecase;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.playdate.app.util.executor.ExecutorProvider;

public abstract class UseCase<P, R> {

    private ExecutorProvider executorProvider;

    public UseCase(ExecutorProvider executorProvider) {
        this.executorProvider = executorProvider;
    }

    public LiveData<Result<R>> executeAsync(P params) {
        MutableLiveData<Result<R>> result = new MutableLiveData<>();
        result.setValue(Result.loading());
        executorProvider.io().submit(() -> {
            try {
                result.postValue(Result.success(execute(params)));
            } catch (Throwable error) {
                result.postValue(Result.error(error));
            }
        });
        return result;
    }

    abstract R execute(P params) throws Throwable;

    public static class Result<T> {

        public enum Status {
            SUCCESS,
            ERROR,
            LOADING
        }

        public Status status;

        public T data;

        public Throwable error;

        private Result(Status status, T data, Throwable error) {
            this.status = status;
            this.data = data;
            this.error = error;
        }

        public static <T> Result<T> success(T data) {
            return new Result<>(Status.SUCCESS, data, null);
        }

        public static <T> Result<T> error(Throwable error) {
            return new Result<>(Status.ERROR, null, error);
        }

        public static <T> Result<T> loading() {
            return new Result<>(Status.LOADING, null, null);
        }
    }
}
