package com.playdate.app.model.chat_models;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ChatExample {


    boolean isSelected;

    public ChatExample(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("profile_photo")
    @Expose
    private String profilePhoto;
    @SerializedName("messages")
    @Expose
    private List<ChatMessage> messages = null;

    public List<ChatMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<ChatMessage> messages) {
        this.messages = messages;
    }

    public String getSenderName() {
        return name;
    }

    public void setSenderName(String senderName) {
        this.name = senderName;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public ChatExample(String name, String profilePhoto) {
        this.name = name;
        this.profilePhoto = profilePhoto;
    }
}
