package com.playdate.app.ui.social.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PostDetails {

    @SerializedName("postedBy")
    private ArrayList<PostedBy> lstpostby;
    int TapCount = 0;

    public int getTapCount() {
        return TapCount;
    }

    public void setTapCount(int tapCount) {
        TapCount = tapCount;
    }

    private String postType;

    private String colorCode;
    private int emojiCode;

    public String getColorCode() {
        return colorCode;
    }

//    public void setColorCode(String colorCode) {
//        this.colorCode = colorCode;
//    }

    public int getEmojiCode() {
        return emojiCode;
    }

//    public void setEmojiCode(int emojiCode) {
//        this.emojiCode = emojiCode;
//    }

    boolean commentStatus;

    public boolean isCommentStatus() {
        return commentStatus;
    }

//    public void setCommentStatus(boolean commentStatus) {
//        this.commentStatus = commentStatus;
//    }

    private String tag;
    private String comments;
    private int isLike;
    private String notifyStatus;
    private int isGallerySave;

    @SerializedName("comments_list")
    private ArrayList<CommentList> comments_list;

    public ArrayList<CommentList> getComments_list() {
        return comments_list;
    }

//    public void setComments_list(ArrayList<CommentList> comments_list) {
//        this.comments_list = comments_list;
//    }

    public int getIsGallerySave() {
        return isGallerySave;
    }

    public void setIsGallerySave(int isGallerySave) {
        this.isGallerySave = isGallerySave;
    }

    public String getNotifyStatus() {
        return notifyStatus;
    }

    public void setNotifyStatus(String notifyStatus) {
        this.notifyStatus = notifyStatus;
    }


    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public int getIsLike() {
        return isLike;
    }

    public void setIsLike(int isLike) {
        this.isLike = isLike;
    }

    private String entryDate;
    int likes;

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }



//    private ArrayList<TagFriends> tagFriends;

    private String location;

    private String postId;

    @SerializedName("media")
    private ArrayList<PostMedia> postMedia;

    private String mediaId;

    private String userId;

    public ArrayList<PostedBy> getLstpostby() {
        return lstpostby;
    }

//    public void setLstpostby(ArrayList<PostedBy> lstpostby) {
//        this.lstpostby = lstpostby;
//    }

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

//    public ArrayList<TagFriends> getTagFriends() {
//        return tagFriends;
//    }

//    public void setTagFriends(ArrayList<TagFriends> tagFriends) {
//        this.tagFriends = tagFriends;
//    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public ArrayList<PostMedia> getPostMedia() {
        return postMedia;
    }

//    public void setPostMedia(ArrayList<PostMedia> postMedia) {
//        this.postMedia = postMedia;
//    }
//
//    public String getMediaId() {
//        return mediaId;
//    }
//
//    public void setMediaId(String mediaId) {
//        this.mediaId = mediaId;
//    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
