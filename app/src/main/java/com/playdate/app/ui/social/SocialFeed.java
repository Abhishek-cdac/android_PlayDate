package com.playdate.app.ui.social;

public class SocialFeed {
    String userName;
    String HeartCount;
    boolean HeartSelected;
    int tapCount;

    public SocialFeed(String userName, String heartCount, boolean heartSelected,int tapCount) {
        this.userName = userName;
        HeartCount = heartCount;
        HeartSelected = heartSelected;
        this.tapCount = tapCount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getHeartCount() {
        return HeartCount;
    }

    public void setHeartCount(String heartCount) {
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
