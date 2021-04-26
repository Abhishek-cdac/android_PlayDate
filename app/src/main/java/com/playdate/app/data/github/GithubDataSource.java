

package com.playdate.app.data.github;

import com.playdate.app.model.SearchUserResult;
import com.playdate.app.model.User;


public interface GithubDataSource {

    SearchUserResult searchUser(String query) throws Throwable;

    User getUser(String username) throws Throwable;
}
