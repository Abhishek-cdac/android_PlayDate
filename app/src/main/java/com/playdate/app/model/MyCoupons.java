package com.playdate.app.model;

import com.google.gson.annotations.Expose;

import java.util.List;

public class MyCoupons {
    @Expose
    private String newPrice;
    @Expose
    private String couponAmountOf;
    @Expose
    private String awardedBy;
    @Expose
    private String awardlevelValue;
    @Expose
    private String freeItem;
    @Expose
    private List<User> users;
    @Expose
    private List<Purchased> purchaseds;
    @Expose
    private String couponPercentageValue;
    @Expose
    private String couponCode;
    @Expose
    private String couponId;
    @Expose
    private String couponDescription;
    @Expose
    private int couponPurchasePoint;
    @Expose
    private String couponTitle;
    @Expose
    private String couponType;
    @Expose
    private String couponValidTillDate;
    @Expose
    private String couponValue;
    @Expose
    private String points;
    @Expose
    private List<Restaurant> restaurants;
    @Expose
    private String status;

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public String getCouponTitle() {
        return couponTitle;
    }

    public void setCouponTitle(String couponTitle) {
        this.couponTitle = couponTitle;
    }

    public String getCouponType() {
        return couponType;
    }

    public void setCouponType(String couponType) {
        this.couponType = couponType;
    }

    public String getCouponValidTillDate() {
        return couponValidTillDate;
    }

    public void setCouponValidTillDate(String couponValidTillDate) {
        this.couponValidTillDate = couponValidTillDate;
    }

    public String getCouponValue() {
        return couponValue;
    }

    public void setCouponValue(String couponValue) {
        this.couponValue = couponValue;
    }

    public List<Restaurant> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCouponPurchasePoint() {
        return couponPurchasePoint;
    }

    public void setCouponPurchasePoint(int couponPurchasePoint) {
        this.couponPurchasePoint = couponPurchasePoint;
    }

    public String getCouponDescription() {
        return couponDescription;
    }

    public void setCouponDescription(String couponDescription) {
        this.couponDescription = couponDescription;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(String newPrice) {
        this.newPrice = newPrice;
    }

    public String getCouponAmountOf() {
        return couponAmountOf;
    }

    public void setCouponAmountOf(String couponAmountOf) {
        this.couponAmountOf = couponAmountOf;
    }

    public String getAwardedBy() {
        return awardedBy;
    }

    public void setAwardedBy(String awardedBy) {
        this.awardedBy = awardedBy;
    }

    public String getAwardlevelValue() {
        return awardlevelValue;
    }

    public void setAwardlevelValue(String awardlevelValue) {
        this.awardlevelValue = awardlevelValue;
    }

    public String getFreeItem() {
        return freeItem;
    }

    public void setFreeItem(String freeItem) {
        this.freeItem = freeItem;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String getCouponPercentageValue() {
        return couponPercentageValue;
    }

    public void setCouponPercentageValue(String couponPercentageValue) {
        this.couponPercentageValue = couponPercentageValue;
    }


    public List<Purchased> getPurchaseds() {
        return purchaseds;
    }

    public void setPurchaseds(List<Purchased> purchaseds) {
        this.purchaseds = purchaseds;
    }





}
