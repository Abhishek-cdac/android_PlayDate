package com.playdate.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyCouponsModel {

    @SerializedName("data")
    private MyCouponsWrap data;

    @Expose
    private String message;
    @Expose
    private Long status;

    public MyCouponsWrap getData() {
        return data;
    }

    public void setData(MyCouponsWrap data) {
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
