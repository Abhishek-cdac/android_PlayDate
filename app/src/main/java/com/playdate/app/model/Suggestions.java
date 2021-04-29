package com.playdate.app.model;

public class Suggestions {
    String name;
    String ImageUrl;
    boolean requestSent;
    boolean premium;

    public Suggestions(String name, String imageUrl, boolean requestSent, boolean premium) {
        this.name = name;
        ImageUrl = imageUrl;
        this.requestSent = requestSent;
        this.premium = premium;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public boolean isRequestSent() {
        return requestSent;
    }

    public void setRequestSent(boolean requestSent) {
        this.requestSent = requestSent;
    }

    public boolean isPremium() {
        return premium;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }
}
