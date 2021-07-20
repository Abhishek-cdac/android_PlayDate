package com.playdate.app.model;

import com.google.gson.annotations.SerializedName;

public class StoreAccountDetails {
    @SerializedName("totalPoints")
    private String totalPoints;

    @SerializedName("currentPoints")
    private String currentPoints;

    @SerializedName("gameCoins")
    private String gameCoins;

    @SerializedName("dateCoins")
    private String dateCoins;

    @SerializedName("dmBooster")
    private String dmBooster;

    @SerializedName("multiplayer")
    private String multiplayer;

    @SerializedName("exclusiveDiscounts")
    private String exclusiveDiscounts;

    @SerializedName("accessAllGames")
    private String accessAllGames;

    @SerializedName("updateDate")
    private Object updateDate;

    @SerializedName("_id")
    private String id;

    @SerializedName("userId")
    private String userId;

    public String getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(String totalPoints) {
        this.totalPoints = totalPoints;
    }

    public String getCurrentPoints() {
        return currentPoints;
    }

    public void setCurrentPoints(String currentPoints) {
        this.currentPoints = currentPoints;
    }

    public String getGameCoins() {
        return gameCoins;
    }

    public void setGameCoins(String gameCoins) {
        this.gameCoins = gameCoins;
    }

    public String getDateCoins() {
        return dateCoins;
    }

    public void setDateCoins(String dateCoins) {
        this.dateCoins = dateCoins;
    }

    public String getDmBooster() {
        return dmBooster;
    }

    public void setDmBooster(String dmBooster) {
        this.dmBooster = dmBooster;
    }

    public String getMultiplayer() {
        return multiplayer;
    }

    public void setMultiplayer(String multiplayer) {
        this.multiplayer = multiplayer;
    }

    public String getExclusiveDiscounts() {
        return exclusiveDiscounts;
    }

    public void setExclusiveDiscounts(String exclusiveDiscounts) {
        this.exclusiveDiscounts = exclusiveDiscounts;
    }

    public String getAccessAllGames() {
        return accessAllGames;
    }

    public void setAccessAllGames(String accessAllGames) {
        this.accessAllGames = accessAllGames;
    }

    public Object getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Object updateDate) {
        this.updateDate = updateDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    @SerializedName("entryDate")
    private String entryDate;
}
