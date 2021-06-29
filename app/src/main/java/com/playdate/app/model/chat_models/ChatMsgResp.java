package com.playdate.app.model.chat_models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ChatMsgResp {

    private int status;
    private String message;

    @SerializedName("data")
    private ArrayList<ChatMessage> lstChatMsg;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<ChatMessage> getLstChatMsg() {
        return lstChatMsg;
    }

    public void setLstChatMsg(ArrayList<ChatMessage> lstChatMsg) {
        this.lstChatMsg = lstChatMsg;
    }
}
