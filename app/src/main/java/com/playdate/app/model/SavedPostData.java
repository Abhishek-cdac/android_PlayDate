package com.playdate.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SavedPostData {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("mediaFullPath")
    @Expose
    private String mediaFullPath;
    private String mediaType;
    private String mediaThumbName;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMediaFullPath() {
        return mediaFullPath;
    }

    public void setMediaFullPath(String mediaFullPath) {
        this.mediaFullPath = mediaFullPath;
    }
}
