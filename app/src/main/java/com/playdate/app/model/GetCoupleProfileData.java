
package com.playdate.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetCoupleProfileData {

    @Expose
    private String action;
    @Expose
    private String coupleId;
    @SerializedName("Profile1")
    private List<Profile1> profile1;
    @SerializedName("Profile2")
    private List<Profile2> profile2;
    @Expose
    private String status;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getCoupleId() {
        return coupleId;
    }

    public void setCoupleId(String coupleId) {
        this.coupleId = coupleId;
    }

    public List<Profile1> getProfile1() {
        return profile1;
    }

    public void setProfile1(List<Profile1> profile1) {
        this.profile1 = profile1;
    }

    public List<Profile2> getProfile2() {
        return profile2;
    }

    public void setProfile2(List<Profile2> profile2) {
        this.profile2 = profile2;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
