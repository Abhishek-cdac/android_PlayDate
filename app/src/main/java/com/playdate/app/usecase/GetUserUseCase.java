
package com.playdate.app.usecase;

import com.playdate.app.data.github.GithubDataSource;
import com.playdate.app.model.User;
import com.playdate.app.util.executor.ExecutorProvider;

public class GetUserUseCase extends UseCase<String, User> {

    private GithubDataSource githubDataSource;

    public GetUserUseCase(ExecutorProvider executorProvider, GithubDataSource githubDataSource) {
        super(executorProvider);
        this.githubDataSource = githubDataSource;
    }

    @Override
    public User execute(String username) throws Throwable {
        return githubDataSource.getUser(username);
    }
}
