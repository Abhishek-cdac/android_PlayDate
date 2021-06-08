package com.playdate.app.model;

import com.google.gson.annotations.Expose;

public class Profile2 {

    @Expose
    private Long age;
    @Expose
    private String fullName;
    @Expose
    private String gender;
    @Expose
    private String personalBio;
    @Expose
    private String profilePicPath;
    @Expose
    private String userId;
    @Expose
    private String username;

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPersonalBio() {
        return personalBio;
    }

    public void setPersonalBio(String personalBio) {
        this.personalBio = personalBio;
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
