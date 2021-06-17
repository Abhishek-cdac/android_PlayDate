package com.playdate.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyCouponsModelStore {
    @SerializedName("data")
    private MyCouponsWrapStore data;

    @Expose
    private String message;
    @Expose
    private Long status;

    public MyCouponsWrapStore getData() {
        return data;
    }

    public void setData(MyCouponsWrapStore data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }
}
