package com.playdate.app.model;

import com.google.gson.annotations.Expose;

import java.util.List;

public class MyCouponsModel {
    @Expose
    private List<MyCoupons> data;



    @Expose
    private String message;
    @Expose
    private Long status;

    public List<MyCoupons> getData() {
        return data;
    }

    public void setData(List<MyCoupons> data) {
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
