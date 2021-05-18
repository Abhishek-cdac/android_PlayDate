package com.playdate.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    int status;
    String message;

    @SerializedName("data")
    @Expose
    LoginUserDetails objdata;

    public LoginUserDetails getUserData() {
        return objdata;
    }

    public void setData(LoginUserDetails data) {
        this.objdata = data;
    }

    public LoginResponse(int status, String message, LoginUserDetails data) {
        this.status = status;
        this.message = message;
        this.objdata = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }




}
