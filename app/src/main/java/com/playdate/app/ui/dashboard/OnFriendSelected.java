package com.playdate.app.ui.dashboard;

public interface OnFriendSelected {
    void OnSingleFriendSelected(String ID);
    void OnFrinedDataClosed();
    void OnSuggestionClosed();
    void OnSuggestionClosed(boolean isFriend,String ID);
}
