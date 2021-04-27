package com.playdate.app.ui.restaurant.adapter;

public class Restaurant {
    int image;
    String name;
    boolean selected;

    public Restaurant(int image, String name, boolean selected) {
        this.image = image;
        this.name = name;
        this.selected = selected;


    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
