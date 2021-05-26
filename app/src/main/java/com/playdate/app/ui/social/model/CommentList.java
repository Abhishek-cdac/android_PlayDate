package com.playdate.app.ui.social.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CommentList {
    String _id;
    String postId;
    String comment;
    String entryDate;
    String commentId;

    @SerializedName("commentBy")
    ArrayList<commentBy> commentBy;

    public ArrayList<com.playdate.app.ui.social.model.commentBy> getCommentBy() {
        return commentBy;
    }

    public void setCommentBy(ArrayList<com.playdate.app.ui.social.model.commentBy> commentBy) {
        this.commentBy = commentBy;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }
}
