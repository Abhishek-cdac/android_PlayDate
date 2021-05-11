package com.playdate.app.model;

public class RegisterResult {
    int status;
    String message;
    LoginUserDetails data;

    public RegisterResult(int status, String message, LoginUserDetails data) {
        this.status = status;
        this.message = message;
        this.data = data;
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

    public LoginUserDetails getData() {
        return data;
    }

    public void setData(LoginUserDetails data) {
        this.data = data;
    }


}


