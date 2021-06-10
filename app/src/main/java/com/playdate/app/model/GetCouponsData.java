
package com.playdate.app.model;

import java.util.List;

import com.google.gson.annotations.Expose;

public class GetCouponsData {

    @Expose
    private String couponCode;
    @Expose
    private String couponId;
    @Expose
    private String couponTitle;
    @Expose
    private String couponType;
    @Expose
    private String couponValidTillDate;
    @Expose
    private String couponValue;

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

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

}
