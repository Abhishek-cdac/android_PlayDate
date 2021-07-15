package com.playdate.app.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RelationRequest {
    @SerializedName("action")
    private String mAction;
    @SerializedName("requestId")
    private String mRequestId;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("toUserID")
    private String mToUserID;
    @SerializedName("userID")
    private List<UserInfo> mUserInfo;
    @SerializedName("_id")
    private String m_id;
    @SerializedName("UserInfo")
    private List<UserInformation> mUserInformation;


    public String getmRequestId() {
        return mRequestId;
    }

    public void setmRequestId(String mRequestId) {
        this.mRequestId = mRequestId;
    }

    public String getmStatus() {
        return mStatus;
    }

    public void setmStatus(String mStatus) {
        this.mStatus = mStatus;
    }

    public String getmToUserID() {
        return mToUserID;
    }

    public void setmToUserID(String mToUserID) {
        this.mToUserID = mToUserID;
    }


    public String getM_id() {
        return m_id;
    }

    public void setM_id(String m_id) {
        this.m_id = m_id;
    }

    public List<UserInformation> getmUserInformation() {
        return mUserInformation;
    }

}
