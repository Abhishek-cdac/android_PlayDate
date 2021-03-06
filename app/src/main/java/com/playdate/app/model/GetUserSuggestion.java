
package com.playdate.app.model;

import com.google.gson.annotations.Expose;

import java.util.List;


public class GetUserSuggestion {

    @Expose
    private List<GetUserSuggestionData> data;
    @Expose
    private String message;
    @Expose
    private Long status;

    public List<GetUserSuggestionData> getData() {
        return data;
    }

    public void setData(List<GetUserSuggestionData> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

}
