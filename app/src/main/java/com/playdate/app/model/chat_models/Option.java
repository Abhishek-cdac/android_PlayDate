package com.playdate.app.model.chat_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Option {
    @SerializedName("questionId")
    @Expose
    private String questionId;
    @SerializedName("option")
    @Expose
    private String option;
    @SerializedName("optionId")
    @Expose
    private String optionId;

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getOptionId() {
        return optionId;
    }

    public void setOptionId(String optionId) {
        this.optionId = optionId;
    }

}