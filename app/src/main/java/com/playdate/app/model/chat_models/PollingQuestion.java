package com.playdate.app.model.chat_models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PollingQuestion {

    String question;
    String status;
    String entryDate;
    String questionId;

    @SerializedName("options")
    ArrayList<PollingOption> pollingOption;

    @SerializedName("totalAnswered")
    ArrayList<TotalPollingAnswered> totalAnswered;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public ArrayList<PollingOption> getPollingOption() {
        return pollingOption;
    }

    public void setPollingOption(ArrayList<PollingOption> pollingOption) {
        this.pollingOption = pollingOption;
    }

    public ArrayList<TotalPollingAnswered> getTotalAnswered() {
        return totalAnswered;
    }

    public void setTotalAnswered(ArrayList<TotalPollingAnswered> totalAnswered) {
        this.totalAnswered = totalAnswered;
    }
}
