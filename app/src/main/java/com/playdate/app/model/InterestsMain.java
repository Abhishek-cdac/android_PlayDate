package com.playdate.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class InterestsMain {

    @SerializedName("data")
    @Expose
    private ArrayList<Interest> lst;
    private int status;
    private String message;

    public ArrayList<Interest> getLst() {
        return lst;
    }

    public void setLst(ArrayList<Interest> lst) {
        this.lst = lst;
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
