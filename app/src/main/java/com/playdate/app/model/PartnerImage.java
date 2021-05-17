package com.playdate.app.model;

public class PartnerImage {
    String image;
    String name;
    String points;

    public PartnerImage(String image, String name, String points) {
        this.image = image;
        this.name = name;
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
