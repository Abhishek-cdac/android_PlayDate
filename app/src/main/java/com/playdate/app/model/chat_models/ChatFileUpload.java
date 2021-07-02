package com.playdate.app.model.chat_models;

import com.google.gson.annotations.SerializedName;

public class ChatFileUpload {
    int status;
    String message;
    @SerializedName("data")
    ChatFile chatFile;

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

    public ChatFile getChatFile() {
        return chatFile;
    }

    public void setChatFile(ChatFile chatFile) {
        this.chatFile = chatFile;
    }
}
