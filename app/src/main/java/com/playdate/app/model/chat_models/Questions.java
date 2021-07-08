package com.playdate.app.model.chat_models;

import java.util.ArrayList;
import java.util.List;

public class Questions {


    private String question;
    private String status;
    private String entryDate;
    private String questionId;

    public ArrayList<Option> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<Option> options) {
        this.options = options;
    }

    public ArrayList<Object> getTotalAnswered() {
        return totalAnswered;
    }

    public void setTotalAnswered(ArrayList<Object> totalAnswered) {
        this.totalAnswered = totalAnswered;
    }

    private ArrayList<Option> options ;
    private ArrayList<Object> totalAnswered ;
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



}
