
package com.playdate.app.model;


import com.google.gson.annotations.Expose;

public class CreateDateGetPartnerData {

    @Expose
    private String _id;
    @Expose
    private Long currentPoints;
    @Expose
    private String fullName;
    @Expose
    private String id;
    @Expose
    private String phoneNo;
    @Expose
    private String profilePicPath;
    @Expose
    private Object profileVideoPath;
    @Expose
    private Boolean status;
    @Expose
    private String totalPoints;
    @Expose
    private String username;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Long getCurrentPoints() {
        return currentPoints;
    }

    public void setCurrentPoints(Long currentPoints) {
        this.currentPoints = currentPoints;
    }

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

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getProfilePicPath() {
        return profilePicPath;
    }

    public void setProfilePicPath(String profilePicPath) {
        this.profilePicPath = profilePicPath;
    }

    public Object getProfileVideoPath() {
        return profileVideoPath;
    }

    public void setProfileVideoPath(Object profileVideoPath) {
        this.profileVideoPath = profileVideoPath;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(String totalPoints) {
        this.totalPoints = totalPoints;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
