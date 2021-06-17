
package com.playdate.app.model;

import com.google.gson.annotations.Expose;

public class Medium {

    @Expose
    private String mediaFullPath;
    @Expose
    private String mediaId;
    @Expose
    private String mediaThumbName;
    @Expose
    private String mediaType;

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

    public String getMediaThumbName() {
        return mediaThumbName;
    }

    public void setMediaThumbName(String mediaThumbName) {
        this.mediaThumbName = mediaThumbName;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

}
