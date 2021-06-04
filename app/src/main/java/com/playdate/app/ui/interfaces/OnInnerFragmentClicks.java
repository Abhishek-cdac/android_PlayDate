package com.playdate.app.ui.interfaces;

import androidx.fragment.app.Fragment;

public interface OnInnerFragmentClicks {
    void ReplaceFrag(Fragment fragment);
//    void ReplaceFragWithStack(Fragment fragment);
    void NoFriends();
    void Reset();
    void loadProfile(String UserID);
    void loadMatchProfile(String UserID);
}
