package com.playdate.app.ui.social;

public class SocialFeed {
    String userName;
    int HeartCount;
    boolean HeartSelected;
    int tapCount;
    int type;
    String Image;
    String SmallUserImage;

    public SocialFeed(String userName, int heartCount, boolean heartSelected,int tapCount,int type,String Image,String SmallUserImage) {
        this.userName = userName;
        HeartCount = heartCount;
        HeartSelected = heartSelected;
        this.tapCount = tapCount;
        this.type = type;
        this.Image = Image;
        this.SmallUserImage = SmallUserImage;
    }

    public String getSmallUserImage() {
        return SmallUserImage;
    }

    public void setSmallUserImage(String smallUserImage) {
        SmallUserImage = smallUserImage;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getHeartCount() {
        return HeartCount;
    }

    public void setHeartCount(int heartCount) {
        HeartCount = heartCount;
    }

    public boolean isHeartSelected() {
        return HeartSelected;
    }

    public int getTapCount() {
        return tapCount;
    }

    public void setTapCount(int tapCount) {
        this.tapCount = tapCount;
    }

    public void setHeartSelected(boolean heartSelected) {
        HeartSelected = heartSelected;
    }
}
