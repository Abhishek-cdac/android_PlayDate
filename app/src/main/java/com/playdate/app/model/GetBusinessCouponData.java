package com.playdate.app.model;

import com.google.gson.annotations.Expose;

import java.util.List;

public class GetBusinessCouponData {

    @Expose
    private String awardedBy;
    @Expose
    private String couponCode;
    @Expose
    private String awardlevelValue;
    @Expose
    private String couponAmountOf;
    @Expose
    private String couponId;
    @Expose
    private String couponImage;
    @Expose
    private String couponPercentageValue;
    @Expose
    private String couponPurchasePoint;
    @Expose
    private String couponTitle;
    @Expose
    private String couponValidTillDate;
    @Expose
    private String freeItem;
    @Expose
    private String newPrice;
    @Expose
    private String status;
    @Expose
    private List<User> user;
    @Expose
    private String userId;

    public String getAwardedBy() {
        return awardedBy;
    }

//    public void setAwardedBy(String awardedBy) {
//        this.awardedBy = awardedBy;
//    }

    public String getAwardlevelValue() {
        return awardlevelValue;
    }

//    public void setAwardlevelValue(String awardlevelValue) {
//        this.awardlevelValue = awardlevelValue;
//    }

    public String getCouponAmountOf() {
        return couponAmountOf;
    }

//    public void setCouponAmountOf(String couponAmountOf) {
//        this.couponAmountOf = couponAmountOf;
//    }

    public String getCouponId() {
        return couponId;
    }

//    public void setCouponId(String couponId) {
//        this.couponId = couponId;
//    }

    public String getCouponImage() {
        return couponImage;
    }

//    public void setCouponImage(String couponImage) {
//        this.couponImage = couponImage;
//    }

    public String getCouponPercentageValue() {
        return couponPercentageValue;
    }

//    public void setCouponPercentageValue(String couponPercentageValue) {
//        this.couponPercentageValue = couponPercentageValue;
//    }

    public String getCouponPurchasePoint() {
        return couponPurchasePoint;
    }

//    public void setCouponPurchasePoint(String couponPurchasePoint) {
//        this.couponPurchasePoint = couponPurchasePoint;
//    }

    public String getCouponTitle() {
        return couponTitle;
    }

    public void setCouponTitle(String couponTitle) {
        this.couponTitle = couponTitle;
    }

    public String getCouponValidTillDate() {
        return couponValidTillDate;
    }

//    public void setCouponValidTillDate(String couponValidTillDate) {
//        this.couponValidTillDate = couponValidTillDate;
//    }

    public String getFreeItem() {
        return freeItem;
    }

//    public void setFreeItem(String freeItem) {
//        this.freeItem = freeItem;
//    }

    public String getNewPrice() {
        return newPrice;
    }

//    public void setNewPrice(String newPrice) {
//        this.newPrice = newPrice;
//    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<User> getUser() {
        return user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

//    public String getCouponCode() {
//        return couponCode;
//    }

//    public void setCouponCode(String couponCode) {
//        this.couponCode = couponCode;
//    }
}
