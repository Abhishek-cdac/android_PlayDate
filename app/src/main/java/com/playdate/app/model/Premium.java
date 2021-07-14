package com.playdate.app.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Premium {
    @SerializedName("data")
    private ArrayList<PremiumPlans> lstPremiumPlan;

    private String message;

    private int status;

    public ArrayList<PremiumPlans> getLstPremiumPlan() {
        return lstPremiumPlan;
    }

    public void setLstPremiumPlan(ArrayList<PremiumPlans> lstPremiumPlan) {
        this.lstPremiumPlan = lstPremiumPlan;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
