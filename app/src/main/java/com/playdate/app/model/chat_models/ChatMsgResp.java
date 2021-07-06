package com.playdate.app.model.chat_models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ChatMsgResp {

    private int status;
    private String message;

    @SerializedName("data")
    private ArrayList<ChatMessage> lstChatMsg;

    public List<String> getPromotions() {
        return promotions;
    }

    public void setPromotions(List<String> promotions) {
        this.promotions = promotions;
    }

    private List<String> promotions ;

    public ArrayList<Questions> getLstQuestions() {
        return lstQuestions;
    }

    public void setLstQuestions(ArrayList<Questions> lstQuestions) {
        this.lstQuestions = lstQuestions;
    }

    private ArrayList<Questions> lstQuestions;

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
