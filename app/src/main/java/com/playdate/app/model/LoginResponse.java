package com.playdate.app.model;

public class LoginResponse {
    public LoginResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    int status;
    String message;

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
