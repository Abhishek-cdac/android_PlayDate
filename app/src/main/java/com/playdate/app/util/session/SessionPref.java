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

    public static void logout(Context mContext) {
        pref = mContext.getSharedPreferences("playdate_shared_pref", 0); // 0 - for private mode
        editor = pref.edit();
        editor.clear();
        editor.commit();
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

    public String getStringVal(String key) {
        return pref.getString(key, null);
    }

    public boolean getBoolVal(String key) {
        return pref.getBoolean(key, false);
    }

    public int getIntVal(String key) {
        return pref.getInt(key, 0);
    }


    public static String LoginUserID = "id";
    public static String LoginVerified = "LoginVerified";
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
    public static String LoginUserinterestedIn = "interestedIn";
    public static String LoginUserrestaurants = "restaurants";
 public static String LoginUserSourceType = "sourceType";
 public static String LoginUsersourceSocialId = "sourceSocialId";
 public static String LoginUserinviteCode = "inviteCode";
 public static String LoginUserpaymentMode = "paymentMode";


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
                              String interestedIn,
                              String restaurants,
                               String sourceType,
                              String sourceSocialId,
                              String inviteCode,
                              String paymentMode

    ) {
        editor.putString(LoginUserID, id);
        editor.putString(LoginUserfullName, fullName);
        editor.putString(LoginUseremail, email);
        editor.putString(LoginUserusername, username);
        editor.putString(LoginUserphoneNo, phoneNo);
        editor.putString(LoginUserstatus, status);
        editor.putString(LoginUsertoken, token);
        editor.putString(LoginUsergender, gender);
        editor.putString(LoginUserbirthDate, birthDate);
        editor.putString(LoginUserage, age);
        editor.putString(LoginUserprofilePic, profilePic);
        editor.putString(LoginUserprofileVideo, profileVideo);
        editor.putString(LoginUserrelationship, relationship);
        editor.putString(LoginUserpersonalBio, personalBio);
        editor.putString(LoginUserinterested, interested);
        editor.putString(LoginUserinterestedIn, interestedIn);
        editor.putString(LoginUserrestaurants, restaurants);
        editor.putString(LoginUserSourceType, sourceType);
        editor.putString(LoginUsersourceSocialId, sourceSocialId);
        editor.putString(LoginUserinviteCode, inviteCode);
        editor.putString(LoginUserpaymentMode, paymentMode);
        editor.commit();
    }


}
