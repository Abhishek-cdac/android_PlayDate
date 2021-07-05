package com.playdate.app.model.chat_models;

import com.google.gson.annotations.SerializedName;
import com.playdate.app.model.UserInfo;

import java.util.ArrayList;

public class ChatMessages {
    String message;
    String status;
    String userId;
    String messageId;
    String entryDate;
    String messageType;

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    @SerializedName("UserInfo")
    ArrayList<UserInfo>lstUser;

    @SerializedName("media")
    ArrayList<MediaInfo>lstMedia;

    public ArrayList<MediaInfo> getLstMedia() {
        return lstMedia;
    }

    public ArrayList<UserInfo> getLstUser() {
        return lstUser;
    }

    public void setLstUser(ArrayList<UserInfo> lstUser) {
        this.lstUser = lstUser;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getEntryDate() {
        return entryDate;
    }
}
