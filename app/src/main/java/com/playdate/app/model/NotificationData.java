
package com.playdate.app.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class NotificationData {
    public int getNoti_type() {
        return noti_type;
    }

    public void setNoti_type(int noti_type) {
        this.noti_type = noti_type;
    }

    int noti_type;
    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isSelected = false;

    @SerializedName("entryDate")
    private String mEntryDate;

    @SerializedName("friendRequest")
    private List<FriendRequest> mFriendRequest;

    public List<ChatRequest> getmChatRequest() {
        return mChatRequest;
    }

    public void setmChatRequest(List<ChatRequest> mChatRequest) {
        this.mChatRequest = mChatRequest;
    }

    @SerializedName("chatRequest")
    private List<ChatRequest> mChatRequest;

    @SerializedName("relationRequest")
    private List<RelationRequest> mRelationRequest;

    public List<RelationRequest> getmRelationRequest() {
        return mRelationRequest;
    }

    public void setmRelationRequest(List<RelationRequest> mRelationRequest) {
        this.mRelationRequest = mRelationRequest;
    }

    public List<UserInformation> getmUserInformation() {
        return mUserInformation;
    }

    public void setmUserInformation(List<UserInformation> mUserInformation) {
        this.mUserInformation = mUserInformation;
    }


    @SerializedName("UserInformation")
    private List<UserInformation> mUserInformation;

    public List<PostInfo> getmPostInfo() {
        return mPostInfo;
    }

    public void setmPostInfo(List<PostInfo> mPostInfo) {
        this.mPostInfo = mPostInfo;
    }

    @SerializedName("PostInfo")
    private List<PostInfo> mPostInfo;

    @SerializedName("notificationId")
    private String mNotificationId;
    @SerializedName("notificationMessage")
    private String mNotificationMessage;
    @SerializedName("notificationText")
    private String mNotificationText;
    @SerializedName("patternID")
    private String mPatternID;
    @SerializedName("readStatus")
    private Boolean mReadStatus;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("toUserID")
    private String mToUserID;
    @SerializedName("userID")
    private String mUserID;
    @SerializedName("_id")
    private String m_id;

    public String getEntryDate() {
        return mEntryDate;
    }

    public void setEntryDate(String entryDate) {
        mEntryDate = entryDate;
    }

    public List<FriendRequest> getFriendRequest() {
        return mFriendRequest;
    }

    public void setFriendRequest(List<FriendRequest> friendRequest) {
        mFriendRequest = friendRequest;
    }

    public String getNotificationId() {
        return mNotificationId;
    }

    public void setNotificationId(String notificationId) {
        mNotificationId = notificationId;
    }

    public String getNotificationMessage() {
        return mNotificationMessage;
    }

    public void setNotificationMessage(String notificationMessage) {
        mNotificationMessage = notificationMessage;
    }

    public String getNotificationText() {
        return mNotificationText;
    }

    public void setNotificationText(String notificationText) {
        mNotificationText = notificationText;
    }

    public String getPatternID() {
        return mPatternID;
    }

    public void setPatternID(String patternID) {
        mPatternID = patternID;
    }

    public Boolean getReadStatus() {
        return mReadStatus;
    }

    public void setReadStatus(Boolean readStatus) {
        mReadStatus = readStatus;
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
