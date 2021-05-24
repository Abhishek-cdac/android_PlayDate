
package com.playdate.app.model;

import java.util.List;

import com.google.gson.annotations.Expose;

public class GetProfileDetails {

    @Expose
    private List<GetProileDetailData> data;
    @Expose
    private String message;
    @Expose
    private Long status;

    public List<GetProileDetailData> getData() {
        return data;
    }

    public void setData(List<GetProileDetailData> data) {
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
