package com.playdate.app.model;

public class Interest {
    String Interest;
    boolean selected;

    public Interest(String interest, boolean selected) {
        Interest = interest;
        this.selected = selected;
    }

    public String getInterest() {
        return Interest;
    }

    public void setInterest(String interest) {
        Interest = interest;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
