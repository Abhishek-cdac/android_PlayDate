package com.playdate.app.model.chat_models;

public class PollingResponse {

    int answerOrder;

    public int getAnswerOrder() {
        return answerOrder;
    }

    public void setAnswerOrder(int answerOrder) {
        this.answerOrder = answerOrder;
    }

    String chatId;
    String isRightAnswer;
    String optionId;
    String points;
    String profilePic;
    String questionId;
    String userId;
    String username;

    public String getIsRightAnswer() {
        return isRightAnswer;
    }

    public void setIsRightAnswer(String isRightAnswer) {
        this.isRightAnswer = isRightAnswer;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }
}
