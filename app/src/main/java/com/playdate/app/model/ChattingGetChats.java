package com.playdate.app.model;

public class ChattingGetChats {

    String type;
    String text;
    String sender_name;
    String from;

    public ChattingGetChats(String type, String text, String sender_name, String from) {
        this.type = type;
        this.text = text;
        this.sender_name = sender_name;
        this.from = from;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSender_name() {
        return sender_name;
    }

    public void setSender_name(String sender_name) {
        this.sender_name = sender_name;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}
