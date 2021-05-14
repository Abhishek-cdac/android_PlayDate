package com.playdate.app.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class FriendsListModel {

    @SerializedName("data")
    private ArrayList<MatchListUser> users;

    private String message;

    private int status;

    public ArrayList<MatchListUser> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<MatchListUser> users) {
        this.users = users;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
