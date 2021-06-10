package com.playdate.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DatingRequest {
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
    public DateRequestData data;

    public DateRequestData getData() {
        return data;
    }

    public void setData(DateRequestData data) {
        this.data = data;
    }


}
