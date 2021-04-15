
package com.playdate.app.usecase;

import com.playdate.app.data.github.GithubDataSource;
import com.playdate.app.data.searchhistory.SearchHistoryDataSource;
import com.playdate.app.model.SearchHistoryItem;
import com.playdate.app.model.SearchUserResult;
import com.playdate.app.util.executor.ExecutorProvider;
import com.playdate.app.util.logging.LoggingHelper;

public class SearchUseCase extends UseCase<String, SearchUserResult> {

    private static final String TAG = "SearchUseCase";

    private GithubDataSource githubDataSource;

    private SearchHistoryDataSource searchHistoryDataSource;

    private LoggingHelper loggingHelper;

    public SearchUseCase(ExecutorProvider executorProvider, GithubDataSource githubDataSource,
                         SearchHistoryDataSource searchHistoryDataSource,
                         LoggingHelper loggingHelper) {
        super(executorProvider);
        this.githubDataSource = githubDataSource;
        this.searchHistoryDataSource = searchHistoryDataSource;
        this.loggingHelper = loggingHelper;
    }

    @Override
    public SearchUserResult execute(String query) throws Throwable {
        SearchHistoryItem searchHistoryItem = new SearchHistoryItem();
        searchHistoryItem.setQuery(query);

        try {
            searchHistoryDataSource.add(searchHistoryItem);
            loggingHelper.debug(TAG, "Query added to the search history");
        } catch (Throwable error) {
            loggingHelper.warn(TAG, "An error occurred while adding a query to the search history", error);
        }

        return githubDataSource.searchUser(query);
    }
}
