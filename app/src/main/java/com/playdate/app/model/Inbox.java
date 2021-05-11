package com.playdate.app.model;

public class Inbox {

    String name;
    String msg;
    String notification;
    String ImageUrl;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    String time;


    public Inbox(String name, String imageUrl, String msg, String notification, String time) {
        this.name = name;
        ImageUrl = imageUrl;
        this.msg = msg;
        this.notification = notification;
        this.time = time;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

}
