
package com.playdate.app.model;

import com.google.gson.annotations.Expose;

public class User {

    @Expose
    private Object businessImage;
    @Expose
    private String fullName;
    @Expose
    private String userId;
    @Expose
    private Object username;

    public Object getBusinessImage() {
        return businessImage;
    }

    public void setBusinessImage(Object businessImage) {
        this.businessImage = businessImage;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Object getUsername() {
        return username;
    }

    public void setUsername(Object username) {
        this.username = username;
    }

}
