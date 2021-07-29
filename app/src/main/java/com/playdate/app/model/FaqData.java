
package com.playdate.app.model;

import com.google.gson.annotations.Expose;

public class FaqData {

    @Expose
    private String answer;
    @Expose
    private String question;

    public String getAnswer() {
        return answer;
    }

//    public void setAnswer(String answer) {
//        this.answer = answer;
//    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

}
