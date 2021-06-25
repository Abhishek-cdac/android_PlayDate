package com.playdate.app.model.chat_models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ChatList {
    String userId;
    String toUserId;
    String chatId;
    @SerializedName("fromUser")
    ArrayList<FromUser>lstFrom;

    @SerializedName("toUser")
    ArrayList<FromUser>lstToUser;

    @SerializedName("chatMessage")
    ArrayList<ChatMessages>lastMsg;

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public ArrayList<FromUser> getLstFrom() {
        return lstFrom;
    }

    public void setLstFrom(ArrayList<FromUser> lstFrom) {
        this.lstFrom = lstFrom;
    }

    public ArrayList<FromUser> getLstToUser() {
        return lstToUser;
    }

    public void setLstToUser(ArrayList<FromUser> lstToUser) {
        this.lstToUser = lstToUser;
    }

    public ArrayList<ChatMessages> getLastMsg() {
        return lastMsg;
    }

    public void setLastMsg(ArrayList<ChatMessages> lastMsg) {
        this.lastMsg = lastMsg;
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



    //"chatMessage": []
}
