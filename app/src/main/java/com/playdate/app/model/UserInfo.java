
package com.playdate.app.model;

import com.google.gson.annotations.SerializedName;

public class UserInfo {

    @SerializedName("profilePicPath")
    private String mProfilePicPath;
    @SerializedName("profileVideoPath")
    private String mProfileVideoPath;
    @SerializedName("username")
    private String mUsername;

    @SerializedName("_id")
    private String m_id;

    @SerializedName("userId")
    private String userId;

    public String getUserId() {
        return userId;
    }

    public String getProfilePicPath() {
        return mProfilePicPath;
    }

    public void setProfilePicPath(String profilePicPath) {
        mProfilePicPath = profilePicPath;
    }

    public String getProfileVideoPath() {
        return mProfileVideoPath;
    }

    public void setProfileVideoPath(String profileVideoPath) {
        mProfileVideoPath = profileVideoPath;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public String get_id() {
        return m_id;
    }

    public void set_id(String _id) {
        m_id = _id;
    }

}
