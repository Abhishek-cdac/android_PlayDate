
package com.playdate.app.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class FriendRequest {

    @SerializedName("action")
    private String mAction;
    @SerializedName("requestId")
    private String mRequestId;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("toUserID")
    private String mToUserID;
    @SerializedName("userID")
    private String mUserID;
    @SerializedName("UserInfo")
    private List<UserInfo> mUserInfo;
    @SerializedName("_id")
    private String m_id;

    public String getAction() {
        return mAction;
    }

    public void setAction(String action) {
        mAction = action;
    }

    public String getRequestId() {
        return mRequestId;
    }

    public void setRequestId(String requestId) {
        mRequestId = requestId;
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

    public List<UserInfo> getUserInfo() {
        return mUserInfo;
    }

    public void setUserInfo(List<UserInfo> userInfo) {
        mUserInfo = userInfo;
    }

    public String get_id() {
        return m_id;
    }

    public void set_id(String _id) {
        m_id = _id;
    }

}
