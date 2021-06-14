package com.playdate.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DatingRequestStatus {
    @Expose
    private String message;
    @Expose
    private Long status;

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

    @SerializedName("data")
    public List<DateRequestData> data;

    public List<DateRequestData> getData() {
        return data;
    }

    public void setData(List<DateRequestData> data) {
        this.data = data;
    }
}
