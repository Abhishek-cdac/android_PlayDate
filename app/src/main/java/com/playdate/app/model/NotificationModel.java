
package com.playdate.app.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class NotificationModel {

    @SerializedName("data")
    private List<NotificationData> mData;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("status")
    private Long mStatus;

    public List<NotificationData> getData() {
        return mData;
    }

    public void setData(List<NotificationData> data) {
        mData = data;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public Long getStatus() {
        return mStatus;
    }

    public void setStatus(Long status) {
        mStatus = status;
    }

}
