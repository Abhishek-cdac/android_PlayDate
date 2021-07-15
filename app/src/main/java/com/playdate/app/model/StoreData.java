package com.playdate.app.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StoreData {
    @SerializedName("storeType")
    private String storeType;

    @SerializedName("storeId")
    private String storeId;

    @SerializedName("storeItems")
    private List<StoreItems> storeItems;

    public String getStoreType() {
        return storeType;
    }

    public void setStoreType(String storeType) {
        this.storeType = storeType;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public List<StoreItems> getStoreItems() {
        return storeItems;
    }

    public void setStoreItems(List<StoreItems> storeItems) {
        this.storeItems = storeItems;
    }
}
