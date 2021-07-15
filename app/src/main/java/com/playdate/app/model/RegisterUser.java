package com.playdate.app.model;

public class RegisterUser {

    private String Fullname;
    private String address;
    private String phoneNo;
    private String email;
    private String password;
    private String deviceType;

    public RegisterUser(String fullname, String address, String phoneNo, String email, String password, String deviceType) {
        Fullname = fullname;
        this.address = address;
        this.phoneNo = phoneNo;
        this.email = email;
        this.password = password;
        this.deviceType = deviceType;
    }

    public String getFullname() {
        return Fullname;
    }

    public void setFullname(String fullname) {
        Fullname = fullname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }
}
