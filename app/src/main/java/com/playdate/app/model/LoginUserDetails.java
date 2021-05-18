package com.playdate.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class LoginUserDetails {


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
    String profilePicPath;
    String profileVideo;
    String profileVideoPath;
    String interestedIn;
    String sourceType;
    String sourceSocialId;
    String inviteCode;
    String paymentMode;
    String mediaId;
    String fullPath;

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getInterestedIn() {
        return interestedIn;
    }

    public void setInterestedIn(String interestedIn) {
        this.interestedIn = interestedIn;
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

    String relationship;
    String personalBio;



    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getSourceSocialId() {
        return sourceSocialId;
    }

    public void setSourceSocialId(String sourceSocialId) {
        this.sourceSocialId = sourceSocialId;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    @SerializedName("interested")
    @Expose
    ArrayList<String> interested;

    @SerializedName("restaurants")
    @Expose
    ArrayList<String> restaurants;

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

    public ArrayList<String> getInterested() {
        return interested;
    }

    public void setInterested(ArrayList<String> interested) {
        this.interested = interested;
    }

    public ArrayList<String> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(ArrayList<String> restaurants) {
        this.restaurants = restaurants;
    }
}