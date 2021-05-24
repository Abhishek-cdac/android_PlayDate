package com.playdate.app.ui.social.model;

public class PostMedia {
    private String mediaFullPath;
    private String mediaThumbName;

    public String getMediaThumbName() {
        return mediaThumbName;
    }

    public void setMediaThumbName(String mediaThumbName) {
        this.mediaThumbName = mediaThumbName;
    }

    private String mediaId;

    public String getMediaFullPath() {
        return mediaFullPath;
    }

    public void setMediaFullPath(String mediaFullPath) {
        this.mediaFullPath = mediaFullPath;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }
}
