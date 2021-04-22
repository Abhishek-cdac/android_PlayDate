package com.playdate.app.ui.restaurant.adapter;

public class Restaurant {
    int image;
    boolean selected;

    public Restaurant(int image, boolean selected) {
        this.image = image;
        this.selected = selected;


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
