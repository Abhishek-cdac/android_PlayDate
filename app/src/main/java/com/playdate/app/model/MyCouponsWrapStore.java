package com.playdate.app.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MyCouponsWrapStore {

    @SerializedName("account")
    private Account account;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @SerializedName("coupondata")
    private ArrayList<GetCouponsData> getAllCoupons;

    public ArrayList<GetCouponsData> getGetAllCoupons() {
        return getAllCoupons;
    }

    public void setData(ArrayList<GetCouponsData> getAllCoupons) {
        this.getAllCoupons = getAllCoupons;
    }
}
