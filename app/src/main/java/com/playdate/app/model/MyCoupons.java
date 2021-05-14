package com.playdate.app.model;

public class MyCoupons {
    String rest_name;
    String discount_desc;
    String valid;
    String coupon_code;
    String image;

    public String getRest_name() {
        return rest_name;
    }

    public void setRest_name(String rest_name) {
        this.rest_name = rest_name;
    }

    public String getDiscount_desc() {
        return discount_desc;
    }

    public void setDiscount_desc(String discount_desc) {
        this.discount_desc = discount_desc;
    }

    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }

    public String getCoupon_code() {
        return coupon_code;
    }

    public void setCoupon_code(String coupon_code) {
        this.coupon_code = coupon_code;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public MyCoupons(String rest_name, String discount_desc, String valid, String coupon_code, String image) {
        this.rest_name = rest_name;
        this.discount_desc = discount_desc;
        this.valid = valid;
        this.coupon_code = coupon_code;
        this.image = image;
    }


}
