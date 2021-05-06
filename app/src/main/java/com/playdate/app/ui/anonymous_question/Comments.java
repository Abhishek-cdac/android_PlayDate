package com.playdate.app.ui.anonymous_question;

public class Comments {
    String name, comment;
    boolean isSelected = false;
    boolean isDeleted = false;

    public Comments(String name, String comment, boolean isSelected, boolean isDeleted) {
        this.name = name;
        this.comment = comment;
        this.isSelected = isSelected;
        this.isDeleted = isDeleted;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}