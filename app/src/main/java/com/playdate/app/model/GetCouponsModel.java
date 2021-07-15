
package com.playdate.app.model;

import com.google.gson.annotations.Expose;

public class GetCouponsModel {


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

}
