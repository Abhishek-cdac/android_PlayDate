package com.playdate.app.model;

public class GameCoin {
    private String amount, totalDollar;
    private int image;

    public GameCoin(String amount, String totalDollar) {
        this.amount = amount;
        this.totalDollar = totalDollar;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTotalDollar() {
        return totalDollar;
    }

    public void setTotalDollar(String totalDollar) {
        this.totalDollar = totalDollar;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
