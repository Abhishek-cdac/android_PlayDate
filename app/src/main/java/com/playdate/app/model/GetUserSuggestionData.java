
package com.playdate.app.model;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class GetUserSuggestionData {

    @SerializedName("__v")
    private Long _V;
    @Expose
    private String _id;
    @Expose
    private Object aboutBio;
    @Expose
    private String address;
    @Expose
    private Object age;
    @Expose
    private Object birthDate;
    @Expose
    private String confirmOTP;
    @Expose
    private String deviceType;
    @Expose
    private String email;
    @Expose
    private String entryDate;
    @Expose
    private List<Firend> firends;
    @Expose
    private String fullName;
    @Expose
    private Object gender;
    @Expose
    private List<Object> interested;
    @Expose
    private Object interestedIn;
    @Expose
    private Long inviteCode;
    @Expose
    private String ipAddress;
    @Expose
    private String isSocialSignup;
    @Expose
    private Object lastLoginDate;
    @Expose
    private String onlineStatus;
    @Expose
    private Long otpTries;
    @Expose
    private String password;
    @Expose
    private String paymentMode;
    @Expose
    private Object personalBio;
    @Expose
    private String phoneNo;
    @Expose
    private Object profilePic;
    @Expose
    private Object profilePicPath;
    @Expose
    private Object profileVideo;
    @Expose
    private Object profileVideoPath;
    @Expose
    private Object relationship;
    @Expose
    private List<Object> restaurants;
    @Expose
    private Object sourceSocialId;
    @Expose
    private String sourceType;
    @Expose
    private Boolean status;
    @Expose
    private String userRoleType;
    @Expose
    private String userType;
    @Expose
    private Object username;

    public Long get_V() {
        return _V;
    }

    public void set_V(Long _V) {
        this._V = _V;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Object getAboutBio() {
        return aboutBio;
    }

    public void setAboutBio(Object aboutBio) {
        this.aboutBio = aboutBio;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Object getAge() {
        return age;
    }

    public void setAge(Object age) {
        this.age = age;
    }

    public Object getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Object birthDate) {
        this.birthDate = birthDate;
    }

    public String getConfirmOTP() {
        return confirmOTP;
    }

    public void setConfirmOTP(String confirmOTP) {
        this.confirmOTP = confirmOTP;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public List<Firend> getFirends() {
        return firends;
    }

    public void setFirends(List<Firend> firends) {
        this.firends = firends;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Object getGender() {
        return gender;
    }

    public void setGender(Object gender) {
        this.gender = gender;
    }

    public List<Object> getInterested() {
        return interested;
    }

    public void setInterested(List<Object> interested) {
        this.interested = interested;
    }

    public Object getInterestedIn() {
        return interestedIn;
    }

    public void setInterestedIn(Object interestedIn) {
        this.interestedIn = interestedIn;
    }

    public Long getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(Long inviteCode) {
        this.inviteCode = inviteCode;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getIsSocialSignup() {
        return isSocialSignup;
    }

    public void setIsSocialSignup(String isSocialSignup) {
        this.isSocialSignup = isSocialSignup;
    }

    public Object getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Object lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public String getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(String onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public Long getOtpTries() {
        return otpTries;
    }

    public void setOtpTries(Long otpTries) {
        this.otpTries = otpTries;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public Object getPersonalBio() {
        return personalBio;
    }

    public void setPersonalBio(Object personalBio) {
        this.personalBio = personalBio;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public Object getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(Object profilePic) {
        this.profilePic = profilePic;
    }

    public Object getProfilePicPath() {
        return profilePicPath;
    }

    public void setProfilePicPath(Object profilePicPath) {
        this.profilePicPath = profilePicPath;
    }

    public Object getProfileVideo() {
        return profileVideo;
    }

    public void setProfileVideo(Object profileVideo) {
        this.profileVideo = profileVideo;
    }

    public Object getProfileVideoPath() {
        return profileVideoPath;
    }

    public void setProfileVideoPath(Object profileVideoPath) {
        this.profileVideoPath = profileVideoPath;
    }

    public Object getRelationship() {
        return relationship;
    }

    public void setRelationship(Object relationship) {
        this.relationship = relationship;
    }

    public List<Object> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(List<Object> restaurants) {
        this.restaurants = restaurants;
    }

    public Object getSourceSocialId() {
        return sourceSocialId;
    }

    public void setSourceSocialId(Object sourceSocialId) {
        this.sourceSocialId = sourceSocialId;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getUserRoleType() {
        return userRoleType;
    }

    public void setUserRoleType(String userRoleType) {
        this.userRoleType = userRoleType;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Object getUsername() {
        return username;
    }

    public void setUsername(Object username) {
        this.username = username;
    }

}
