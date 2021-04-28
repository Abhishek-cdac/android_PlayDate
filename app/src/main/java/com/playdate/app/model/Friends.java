package com.playdate.app.model;

public class Friends {
    String Name;
    String ImageURL;
    boolean requestSent;

    public Friends(String name, String image) {
        Name = name;
        ImageURL = image;
    }

    public Friends(String name, String imageURL, boolean requestSent) {
        Name = name;
        ImageURL = imageURL;
        this.requestSent = requestSent;
    }

    public boolean isRequestSent() {
        return requestSent;
    }

    public void setRequestSent(boolean requestSent) {
        this.requestSent = requestSent;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return ImageURL;
    }

    public void setImage(String image) {
        ImageURL = image;
    }
}
