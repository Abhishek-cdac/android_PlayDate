package com.playdate.app.util.session;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionPref {
    static SessionPref ref;

    public static SessionPref getInstance(Context mContext) {
        if (ref == null) {
            ref = new SessionPref();
            init(mContext);
        }
        return ref;
    }

    static SharedPreferences pref;
    static SharedPreferences.Editor editor;

    private static void init(Context mContext) {
        pref = mContext.getSharedPreferences("playdate_shared_pref", 0); // 0 - for private mode
        editor = pref.edit();
    }


    public void saveStringKeyVal(String key, String val) {
        editor.putString(key, val);
        editor.commit();
    }

    public void saveBoolKeyVal(String key, boolean val) {
        editor.putBoolean(key, val);
        editor.commit();
    }

    public void saveIntKeyVal(String key, int val) {
        editor.putInt(key, val);
        editor.commit();
    }

    public void getStringVal(String key) {
        pref.getString(key, null);
    }

    public void getBoolVal(String key) {
        pref.getBoolean(key, false);
    }

    public void getIntVal(String key) {
        pref.getInt(key, 0);
    }


    public static String LoginUserID = "id";
    public static String LoginUserfullName = "fullName";
    public static String LoginUseremail = "email";
    public static String LoginUserusername = "username";
    public static String LoginUserphoneNo = "phoneNo";
    public static String LoginUserstatus = "status";
    public static String LoginUsertoken = "token";
    public static String LoginUsergender = "gender";
    public static String LoginUserbirthDate = "birthDate";
    public static String LoginUserage = "age";
    public static String LoginUserprofilePic = "profilePic";
    public static String LoginUserprofileVideo = "profileVideo";
    public static String LoginUserrelationship = "relationship";
    public static String LoginUserpersonalBio = "personalBio";
    public static String LoginUserinterested = "interested";
    public static String LoginUserrestaurants = "restaurants";


    public void saveLoginUser(String id,
                              String fullName,
                              String email,
                              String username,
                              String phoneNo,
                              String status,
                              String token,
                              String gender,
                              String birthDate,
                              String age,
                              String profilePic,
                              String profileVideo,
                              String relationship,
                              String personalBio,
                              String interested,
                              String restaurants) {
        editor.putString(LoginUserID, id == null ? "null" : id);
        editor.putString(LoginUserfullName, fullName == null ? "null" : fullName);
        editor.putString(LoginUseremail, email == null ? "null" : email);
        editor.putString(LoginUserusername, username == null ? "null" : username);
        editor.putString(LoginUserphoneNo, phoneNo == null ? "null" : phoneNo);
        editor.putString(LoginUserstatus, status == null ? "null" : status);
        editor.putString(LoginUsertoken, token == null ? "null" : token);
        editor.putString(LoginUsergender, gender == null ? "null" : gender);
        editor.putString(LoginUserbirthDate, birthDate == null ? "null" : birthDate);
        editor.putString(LoginUserage, age == null ? "null" : age);
        editor.putString(LoginUserprofilePic, profilePic == null ? "null" : profilePic);
        editor.putString(LoginUserprofileVideo, profileVideo == null ? "null" : profileVideo);
        editor.putString(LoginUserrelationship, relationship == null ? "null" : relationship);
        editor.putString(LoginUserpersonalBio, personalBio == null ? "null" : personalBio);
        editor.putString(LoginUserinterested, interested == null ? "null" : interested);
        editor.putString(LoginUserrestaurants, restaurants == null ? "null" : restaurants);
        editor.commit();
    }


}
