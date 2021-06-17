
package com.playdate.app.model;

import java.util.List;
import com.google.gson.annotations.Expose;

public class PostInfo {

    @Expose
    private List<Medium> media;
    @Expose
    private String mediaId;
    @Expose
    private String postId;
    @Expose
    private String postType;

    public List<Medium> getMedia() {
        return media;
    }

    public void setMedia(List<Medium> media) {
        this.media = media;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

}
