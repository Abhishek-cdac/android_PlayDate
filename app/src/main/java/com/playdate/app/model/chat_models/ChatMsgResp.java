package com.playdate.app.model.chat_models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ChatMsgResp {

    private int status;
    private String message;

    @SerializedName("data")
    private ArrayList<ChatMessage> lstChatMsg;

    @SerializedName("questions")
    private ArrayList<PollingQuestion> lstPollingQuestion;

    public ArrayList<String> getLstPromotions() {
        return lstPromotions;
    }

    public void setLstPromotions(ArrayList<String> lstPromotions) {
        this.lstPromotions = lstPromotions;
    }

    @SerializedName("promotions")
    private ArrayList<String> lstPromotions;

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

    public ArrayList<PollingQuestion> getLstPollingQuestion() {
        return lstPollingQuestion;
    }

    public void setLstPollingQuestion(ArrayList<PollingQuestion> lstPollingQuestion) {
        this.lstPollingQuestion = lstPollingQuestion;
    }
}
