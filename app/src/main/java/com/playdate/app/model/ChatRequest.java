package com.playdate.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChatRequest {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("activeStatus")
    @Expose
    private String activeStatus;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("toUserId")
    @Expose
    private String toUserId;
    @SerializedName("requestId")
    @Expose
    private String requestId;
    @SerializedName("UserInfo")
    @Expose
    private List<UserInfo> userInfo = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getActiveStatus() {
        return activeStatus;
    }

//    public void setActiveStatus(String activeStatus) {
//        this.activeStatus = activeStatus;
//    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public List<UserInfo> getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(List<UserInfo> userInfo) {
        this.userInfo = userInfo;
    }


}
