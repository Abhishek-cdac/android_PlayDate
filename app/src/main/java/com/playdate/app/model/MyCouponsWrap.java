package com.playdate.app.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MyCouponsWrap {
    @SerializedName("coupondata")
    ArrayList<MyCoupons> lst;

    public ArrayList<MyCoupons> getLst() {
        return lst;
    }

    public void setLst(ArrayList<MyCoupons> lst) {
        this.lst = lst;
    }

    @SerializedName("account")
    Account account;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

}
