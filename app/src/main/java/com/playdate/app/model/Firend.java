
package com.playdate.app.model;


import com.google.gson.annotations.Expose;


public class Firend {

    @Expose
    private String _id;
    @Expose
    private String status;
    @Expose
    private String userID;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

}
