package com.playdate.app.ui.dashboard;

public interface OnFriendSelected {
    void OnSingleFriendSelected(String ID,String FreindID);
    void OnFrinedDataClosed();
    void OnSuggestionClosed();
    void OnSuggestionClosed(boolean isFriend,String ID);
}
