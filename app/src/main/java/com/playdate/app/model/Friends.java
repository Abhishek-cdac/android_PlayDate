package com.playdate.app.model;

public class Friends {
    String Name;
    String ImageURL;

    public Friends(String name, String image) {
        Name = name;
        ImageURL = image;
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
