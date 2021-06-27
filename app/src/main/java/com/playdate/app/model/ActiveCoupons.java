package com.playdate.app.model;

import android.graphics.drawable.Drawable;

public class ActiveCoupons {
    String name, desc, expiryDate;
    int image;

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public ActiveCoupons(String name, String desc, String expiryDate,int image) {
        this.name = name;
        this.desc = desc;
        this.expiryDate = expiryDate;
        this.image=image;
    }
}
