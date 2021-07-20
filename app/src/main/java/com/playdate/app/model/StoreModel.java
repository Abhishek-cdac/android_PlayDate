package com.playdate.app.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StoreModel {
    @SerializedName("data")
    private List<StoreData> mData;

    @SerializedName("accountDetails")
    private List<StoreAccountDetails> accountDetails;

    public List<StoreAccountDetails> getAccountDetails() {
        return accountDetails;
    }

    public void setAccountDetails(List<StoreAccountDetails> accountDetails) {
        this.accountDetails = accountDetails;
    }

    @SerializedName("message")
    private String mMessage;

    @SerializedName("status")
    private Long mStatus;

    public List<StoreData> getmData() {
        return mData;
    }

    public void setmData(List<StoreData> mData) {
        this.mData = mData;
    }

    public String getmMessage() {
        return mMessage;
    }

    public void setmMessage(String mMessage) {
        this.mMessage = mMessage;
    }

    public Long getmStatus() {
        return mStatus;
    }

    public void setmStatus(Long mStatus) {
        this.mStatus = mStatus;
    }
}
