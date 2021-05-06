package com.playdate.app.model;

public class LoginUserDetails {

    public LoginUserDetails(String id, String fullName, String email, String username, String phoneNo, String status, String token, String gender, String birthDate, String age, String profilePic, String profileVideo, String relationship, String personalBio, String interested, String restaurants) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.username = username;
        this.phoneNo = phoneNo;
        this.status = status;
        this.token = token;
        this.gender = gender;
        this.birthDate = birthDate;
        this.age = age;
        this.profilePic = profilePic;
        this.profileVideo = profileVideo;
        this.relationship = relationship;
        this.personalBio = personalBio;
        this.interested = interested;
        this.restaurants = restaurants;
    }

    String id;
    String fullName;
    String email;
    String username;
    String phoneNo;
    String status;
    String token;
    String gender;
    String birthDate;
    String age;
    String profilePic;
    String profileVideo;
    String relationship;
    String personalBio;
    String interested;
    String restaurants;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getProfileVideo() {
        return profileVideo;
    }

    public void setProfileVideo(String profileVideo) {
        this.profileVideo = profileVideo;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getPersonalBio() {
        return personalBio;
    }

    public void setPersonalBio(String personalBio) {
        this.personalBio = personalBio;
    }

    public String getInterested() {
        return interested;
    }

    public void setInterested(String interested) {
        this.interested = interested;
    }

    public String getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(String restaurants) {
        this.restaurants = restaurants;
    }
}