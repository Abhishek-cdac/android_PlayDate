package com.playdate.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.playdate.app.ui.restaurant.adapter.Restaurant;

import java.util.ArrayList;

public class RestMain {
    @SerializedName("data")
    @Expose
    ArrayList<Restaurant> lst;
    int status;
    String message;

    public ArrayList<Restaurant> getLst() {
        return lst;
    }

    public void setLst(ArrayList<Restaurant> lst) {
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
