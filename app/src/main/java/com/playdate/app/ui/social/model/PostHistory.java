package com.playdate.app.ui.social.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PostHistory {

    @SerializedName("data")
    private ArrayList<PostDetails> postDetails;

    private String message;

    private int status;

    public ArrayList<PostDetails> getPostDetails() {
        return postDetails;
    }

    public void setPostDetails(ArrayList<PostDetails> postDetails) {
        this.postDetails = postDetails;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
