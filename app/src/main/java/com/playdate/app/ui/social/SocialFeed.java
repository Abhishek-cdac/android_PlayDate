package com.playdate.app.ui.social;

public class SocialFeed {
    String userName;
    String HeartCount;
    boolean HeartSelected;

    public SocialFeed(String userName, String heartCount, boolean heartSelected) {
        this.userName = userName;
        HeartCount = heartCount;
        HeartSelected = heartSelected;
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

    public void setHeartSelected(boolean heartSelected) {
        HeartSelected = heartSelected;
    }
}
