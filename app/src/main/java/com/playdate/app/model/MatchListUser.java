package com.playdate.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MatchListUser {
    private String profilePicPath;
    boolean Selected;

    public boolean isSelected() {
        return Selected;
    }

    public void setSelected(boolean selected) {
        Selected = selected;
    }

    private String gender;

    private String fullName;
    private String userId;
    private String friendId;

    public String getFriendId() {
        return friendId;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    private ArrayList<String> restaurants;

    private String _id;
    private String paymentMode;
    private int age;

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    private ArrayList<String> interested;

    private String profileVideoPath;

    private String phoneNo;

    private String username;

    private String status;

    @SerializedName("chatStatusFrom")
    @Expose
    private List<ChatStatusFrom> chatStatusFrom = null;

    public List<ChatStatusFrom> getChatStatusFrom() {
        return chatStatusFrom;
    }

    public void setChatStatusFrom(List<ChatStatusFrom> chatStatusFrom) {
        this.chatStatusFrom = chatStatusFrom;
    }


    public String getProfilePicPath() {
        return profilePicPath;
    }

    public void setProfilePicPath(String profilePicPath) {
        this.profilePicPath = profilePicPath;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public ArrayList<String> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(ArrayList<String> restaurants) {
        this.restaurants = restaurants;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public ArrayList<String> getInterested() {
        return interested;
    }

    public void setInterested(ArrayList<String> interested) {
        this.interested = interested;
    }

    public String getProfileVideoPath() {
        return profileVideoPath;
    }

    public void setProfileVideoPath(String profileVideoPath) {
        this.profileVideoPath = profileVideoPath;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
