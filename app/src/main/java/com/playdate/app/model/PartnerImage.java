package com.playdate.app.model;

public class PartnerImage {
    private String image;
    private String name;
    private String points;
    private String position;
    private boolean isIncreased;

    public PartnerImage(String image, String name, String points, String position, boolean isIncreased) {
        this.image = image;
        this.name = name;
        this.points = points;
        this.position = position;
        this.isIncreased = isIncreased;
    }

    public boolean isIncreased() {
        return isIncreased;
    }

    public void setIncreased(boolean increased) {
        isIncreased = increased;
    }

    public PartnerImage(String image, String name, String points) {
        this.image = image;
        this.name = name;
        this.points = points;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
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
