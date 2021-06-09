package com.playdate.app.model.chat_models;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.playdate.app.model.chat_models.ChatAttachment;


public class ChatMessage {

    Bitmap bitmap;
    Drawable drawable;
    Uri uri;
    double lattitude, longitude;

    public double getLattitude() {
        return lattitude;
    }

    public void setLattitude(double lattitude) {
        this.lattitude = lattitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
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

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("text")
    @Expose
    private String text;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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

    public ChatMessage(String type, String from, String to, List<ChatAttachment> attachment) {
        this.type = type;
        this.from = from;
        this.to = to;
        this.attachment = attachment;
    }

    public ChatMessage(String type, String from, String to, Uri uri) {
        this.uri = uri;
        this.type = type;
        this.from = from;
        this.to = to;
    }

    public ChatMessage(String type, String from, String to, String text) {
        this.type = type;
        this.text = text;
        this.from = from;
        this.to = to;
    }



    public ChatMessage(String type, String from, String to, Drawable drawable) {
        this.type = type;
        this.from = from;
        this.to = to;
        this.drawable = drawable;
    }

    public ChatMessage(String type, String from, String to, double lattitude, double longitude) {
        this.lattitude = lattitude;
        this.longitude = longitude;
        this.type = type;
        this.from = from;
        this.to = to;
    }
}
