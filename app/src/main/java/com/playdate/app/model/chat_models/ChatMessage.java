package com.playdate.app.model.chat_models;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.playdate.app.model.UserInfo;

import java.util.ArrayList;
import java.util.List;


public class ChatMessage {

    Bitmap bitmap;
    Drawable drawable;
    Uri uri;
    @SerializedName("lat")
    String lattitude;

    @SerializedName("long")
    String longitude;


    public String getLattitude() {
        return lattitude;
    }

    public void setLattitude(String lattitude) {
        this.lattitude = lattitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    @SerializedName("messageType")
    @Expose
    private String type;


    private String message;
    private String entryDate;
    private String userId;
    private String chatId;
    private String messageId;

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @SerializedName("UserInfo")
    ArrayList<UserInfo> UserInfo;
    @SerializedName("mediaInfo")
    ArrayList<MediaInfo> mediaInfo;

    public ArrayList<MediaInfo> getMediaInfo() {
        return mediaInfo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public ArrayList<com.playdate.app.model.UserInfo> getUserInfo() {
        return UserInfo;
    }

    public void setUserInfo(ArrayList<com.playdate.app.model.UserInfo> userInfo) {
        UserInfo = userInfo;
    }

    @SerializedName("from")
    @Expose
    private String from;
    @SerializedName("sender_name")
    @Expose
    private String senderName;
    @SerializedName("profile_photo")
    @Expose
    private String profilePhoto;
    @SerializedName("to")
    @Expose
    private String to;
    @SerializedName("attachment")
    @Expose
    private List<ChatAttachment> attachment = null;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public List<ChatAttachment> getAttachment() {
        return attachment;
    }

    public void setAttachment(List<ChatAttachment> attachment) {
        this.attachment = attachment;
    }

//    public ChatMessage(String type, String from, String to, List<ChatAttachment> attachment) {
//        this.type = type;
//        this.from = from;
//        this.to = to;
//        this.attachment = attachment;
//    }
//    public ChatMessage(String type, String UserID, String UserName, String ImagePath) {
//        this.type = type;
//        this.from = UserID;
//        this.to = to;
//        this.attachment = attachment;
//    }
//
//    public ChatMessage(String type, String from, String to, Uri uri) {
//        this.uri = uri;
//        this.type = type;
//        this.from = from;
//        this.to = to;
//    }
//
//    public ChatMessage(String type, String from, String to, String text) {
//        this.type = type;
//        this.text = text;
//        this.from = from;
//        this.to = to;
//    }


//    public ChatMessage(String type, String from, String to, Drawable drawable) {
//        this.type = type;
//        this.from = from;
//        this.to = to;
//        this.drawable = drawable;
//    }
//
//    public ChatMessage(String type, String from, String to, double lattitude, double longitude) {
//        this.lattitude = lattitude;
//        this.longitude = longitude;
//        this.type = type;
//        this.from = from;
//        this.to = to;
//    }

    String UserName;
    String UserImage;


    public ChatMessage(String Type, String userName, String userImage, String userID, String msg) {
        this.type = Type;
        UserName = userName;
        UserImage = userImage;
        userId = userID;
        this.message = msg;
    }

    public ChatMessage(String Type, String userName, String userImage, String userID, Drawable drawable) {
        this.type = Type;
        UserName = userName;
        UserImage = userImage;
        userId = userID;
        this.drawable = drawable;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserImage() {
        return UserImage;
    }

    public void setUserImage(String userImage) {
        UserImage = userImage;
    }

    public String getUserID() {
        return userId;
    }

    public void setUserID(String userID) {
        userId = userID;
    }


}
