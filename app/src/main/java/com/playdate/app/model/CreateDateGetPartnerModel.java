
package com.playdate.app.model;

import java.util.List;

import com.google.gson.annotations.Expose;

public class CreateDateGetPartnerModel {

    @Expose
    private List<CreateDateGetPartnerData> data;
    @Expose
    private String message;
    @Expose
    private Long status;

    public List<CreateDateGetPartnerData> getData() {
        return data;
    }

    public void setData(List<CreateDateGetPartnerData> data) {
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
