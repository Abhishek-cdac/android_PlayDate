
package com.playdate.app.model;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class GetUserSuggestionData {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("fullName")
    @Expose
    private String fullName;
    @SerializedName("phoneNo")
    @Expose
    private String phoneNo;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("profilePicPath")
    @Expose
    private String profilePicPath;
    @SerializedName("profileVideoPath")
    @Expose
    private String profileVideoPath;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("friendRequest")
    @Expose
    private List<FriendRequest> friendRequest = null;

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

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<FriendRequest> getFriendRequest() {
        return friendRequest;
    }

    public void setFriendRequest(List<FriendRequest> friendRequest) {
        this.friendRequest = friendRequest;
    }
}