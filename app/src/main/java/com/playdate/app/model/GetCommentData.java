
package com.playdate.app.model;


import com.google.gson.annotations.Expose;

public class GetCommentData {

    @Expose
    private Comments comments;
    @Expose
    private String fullName;
    @Expose
    private String profilePicPath;
    @Expose
    private String userId;
    @Expose
    private String username;

    public Comments getComments() {
        return comments;
    }

    public void setComments(Comments comments) {
        this.comments = comments;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getProfilePicPath() {
        return profilePicPath;
    }

    public void setProfilePicPath(String profilePicPath) {
        this.profilePicPath = profilePicPath;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
