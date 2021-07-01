package com.playdate.app.model.chat_models;

public class MediaInfo {
    String mediaType;
    String mediaId;
    String mediaFullPath;
    String mediaThumbName;

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public void setMediaFullPath(String mediaFullPath) {
        this.mediaFullPath = mediaFullPath;
    }

    public void setMediaThumbName(String mediaThumbName) {
        this.mediaThumbName = mediaThumbName;
    }

    public String getMediaType() {
        return mediaType;
    }

    public String getMediaId() {
        return mediaId;
    }

    public String getMediaFullPath() {
        return mediaFullPath;
    }

    public String getMediaThumbName() {
        return mediaThumbName;
    }
}
