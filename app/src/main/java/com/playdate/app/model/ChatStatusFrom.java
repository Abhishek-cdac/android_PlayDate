package com.playdate.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChatStatusFrom {
    @SerializedName("activeStatus")
    @Expose
    private String activeStatus;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("toUserId")
    @Expose
    private String toUserId;
    @SerializedName("chatId")
    @Expose
    private String chatId;

    public String getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(String activeStatus) {
        this.activeStatus = activeStatus;
    }

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

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }
}
