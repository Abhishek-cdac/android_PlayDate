
package com.playdate.app.model;

import com.google.gson.annotations.SerializedName;


public class FriendRequest {

    @SerializedName("action")
    private String mAction;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("toUserID")
    private String mToUserID;
    @SerializedName("userID")
    private String mUserID;
    @SerializedName("_id")
    private String m_id;

    public String getAction() {
        return mAction;
    }

    public void setAction(String action) {
        mAction = action;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public String getToUserID() {
        return mToUserID;
    }

    public void setToUserID(String toUserID) {
        mToUserID = toUserID;
    }

    public String getUserID() {
        return mUserID;
    }

    public void setUserID(String userID) {
        mUserID = userID;
    }

    public String get_id() {
        return m_id;
    }

    public void set_id(String _id) {
        m_id = _id;
    }

}
