package com.playdate.app.model;

import android.util.Patterns;

public class RegisterUser {


    String Fullname;
    String Address;
    String PhoneNumber;
    String Email;
    String Password;

    public RegisterUser(String fullname, String address, String phoneNumber, String email, String password) {
        Fullname = fullname;
        Address = address;
        PhoneNumber = phoneNumber;
        Email = email;
        Password = password;
    }

    public String getFullname() {
        return Fullname;
    }

    public void setFullname(String fullname) {
        Fullname = fullname;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
    public boolean isEmailValid() {
        return Patterns.EMAIL_ADDRESS.matcher(getEmail()).matches();
    }
    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
