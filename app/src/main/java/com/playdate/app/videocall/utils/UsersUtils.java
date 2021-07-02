package com.playdate.app.videocall.utils;

import android.content.Context;

import com.playdate.app.videocall.db.QbUsersDbManager;
import com.quickblox.users.model.QBUser;

import java.util.ArrayList;
import java.util.List;


public class UsersUtils {


    public static ArrayList<QBUser> getListAllUsersFromIds(ArrayList<QBUser> existedUsers, List<Integer> allIds) {
        ArrayList<QBUser> qbUsers = new ArrayList<>();
        for (Integer userId : allIds) {
            QBUser stubUser = createStubUserById(userId);
            if (!existedUsers.contains(stubUser)) {
                qbUsers.add(stubUser);
            }
        }
        qbUsers.addAll(existedUsers);

        return qbUsers;
    }

    private static QBUser createStubUserById(Integer userId) {
        QBUser stubUser = new QBUser(userId);
        stubUser.setFullName(String.valueOf(userId));
        return stubUser;
    }

}