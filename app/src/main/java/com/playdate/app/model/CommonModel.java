
package com.playdate.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CommonModel {

    @Expose
    private String message;
    @Expose
    private Long status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    @SerializedName("data")
    public ArrayList<BlockedUser> blockedUsers;

    public ArrayList<BlockedUser> getBlockedUsers() {
        return blockedUsers;
    }

    public void setBlockedUsers(ArrayList<BlockedUser> blockedUsers) {
        this.blockedUsers = blockedUsers;
    }
}
