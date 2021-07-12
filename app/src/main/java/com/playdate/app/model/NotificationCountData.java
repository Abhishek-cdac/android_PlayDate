
package com.playdate.app.model;

import com.google.gson.annotations.Expose;

public class NotificationCountData {

    @Expose
    private int totalUnreadNotification;

    public int getTotalUnreadNotification() {
        return totalUnreadNotification;
    }

    public void setTotalUnreadNotification(int totalUnreadNotification) {
        this.totalUnreadNotification = totalUnreadNotification;
    }

}
