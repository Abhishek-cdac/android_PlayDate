package com.playdate.app.model;

public class Account {


    private int totalPoints;
    private int currentPoints;
    private int gameCoins;
    private int dateCoins;
    private int dmBooster;
    private int multiplayer;
    private String exclusiveDiscounts;
    private String accessAllGames;
    private String userId;

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    public int getCurrentPoints() {
        return currentPoints;
    }

    public void setCurrentPoints(int currentPoints) {
        this.currentPoints = currentPoints;
    }

    public int getGameCoins() {
        return gameCoins;
    }

    public void setGameCoins(int gameCoins) {
        this.gameCoins = gameCoins;
    }

    public int getDateCoins() {
        return dateCoins;
    }

    public void setDateCoins(int dateCoins) {
        this.dateCoins = dateCoins;
    }

    public int getDmBooster() {
        return dmBooster;
    }

    public void setDmBooster(int dmBooster) {
        this.dmBooster = dmBooster;
    }

    public int getMultiplayer() {
        return multiplayer;
    }

    public void setMultiplayer(int multiplayer) {
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
