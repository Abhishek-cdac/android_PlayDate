package com.playdate.app.model;

public class Notification {
    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLikedPhoto() {
        return likedPhoto;
    }

    public void setLikedPhoto(String likedPhoto) {
        this.likedPhoto = likedPhoto;
    }

    public String getInnerType() {
        return innerType;
    }

    public void setInnerType(String innerType) {
        this.innerType = innerType;
    }

    public Notification(String profileImage, String name, String likedPhoto, int noti_type, String innerType) {
        this.profileImage = profileImage;
        this.name = name;
        this.likedPhoto = likedPhoto;
        this.noti_type = noti_type;
        this.innerType = innerType;


    }

    String profileImage, name, likedPhoto, innerType;
    int noti_type;

    public int getNoti_type() {
        return noti_type;
    }

    public void setNoti_type(int noti_type) {
        this.noti_type = noti_type;
    }

}
