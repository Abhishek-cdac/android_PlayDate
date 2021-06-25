package com.playdate.app.model.chat_models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ChatResponse {

    int status;
    String message;
    @SerializedName("data")
    ArrayList<ChatList> lst;

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

    public ArrayList<ChatList> getLst() {
        return lst;
    }

    public void setLst(ArrayList<ChatList> lst) {
        this.lst = lst;
    }
}
