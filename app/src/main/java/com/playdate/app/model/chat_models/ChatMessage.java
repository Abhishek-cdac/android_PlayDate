package com.playdate.app.model.chat_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.playdate.app.model.UserInfo;

import java.util.ArrayList;


public class ChatMessage {

    @SerializedName("lat")
    String lattitude;

    @SerializedName("long")
    String longitude;


    @SerializedName("messageType")
    @Expose
    private String type;
    private String message;
    private String entryDate;
    private String userId;
    private String chatId;
    private String messageId;

    @SerializedName("from")
    @Expose
    private String from;
    @SerializedName("sender_name")
    @Expose
    private String senderName;

    @SerializedName("UserInfo")
    ArrayList<UserInfo> UserInfo;

    @SerializedName("mediaInfo")
    ArrayList<MediaInfo> mediaInfo;

    String UserName;
    String UserImage;

    public ArrayList<MediaInfo> getMediaInfo() {
        return mediaInfo;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public void setMediaInfo(ArrayList<MediaInfo> mediaInfo) {
        this.mediaInfo = mediaInfo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public ArrayList<com.playdate.app.model.UserInfo> getUserInfo() {
        return UserInfo;
    }

    public void setUserInfo(ArrayList<com.playdate.app.model.UserInfo> userInfo) {
        UserInfo = userInfo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }


    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserImage() {
        return UserImage;
    }

    public void setUserImage(String userImage) {
        UserImage = userImage;
    }

    public String getUserID() {
        return userId;
    }

    public void setUserID(String userID) {
        userId = userID;
    }

    public String getLattitude() {
        return lattitude;
    }

    public void setLattitude(String lattitude) {
        this.lattitude = lattitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }


}
