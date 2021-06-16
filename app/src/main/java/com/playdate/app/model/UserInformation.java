
package com.playdate.app.model;

import com.google.gson.annotations.Expose;

public class UserInformation {

    @Expose
    private String fullName;
    @Expose
    private String id;
    @Expose
    private String profilePicPath;
    @Expose
    private String profileVideoPath;
    @Expose
    private String username;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProfilePicPath() {
        return profilePicPath;
    }

    public void setProfilePicPath(String profilePicPath) {
        this.profilePicPath = profilePicPath;
    }

    public String getProfileVideoPath() {
        return profileVideoPath;
    }

    public void setProfileVideoPath(String profileVideoPath) {
        this.profileVideoPath = profileVideoPath;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
