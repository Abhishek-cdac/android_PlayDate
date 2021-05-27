package com.playdate.app.model.chat_models;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChatUserList {
    @SerializedName("chats")
    @Expose
    private List<ChatExample> chats = null;

    public List<ChatExample> getChats() {
        return chats;
    }

    public void setChats(List<ChatExample> chats) {
        this.chats = chats;
    }

}
