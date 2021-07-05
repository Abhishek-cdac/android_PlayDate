package com.playdate.app.ui.dashboard

interface OnFriendSelected {
    fun OnSingleFriendSelected(ID: String?, FreindID: String?)
    fun OnFrinedDataClosed()
    fun OnSuggestionClosed()
    fun OnSuggestionClosed(isFriend: Boolean, ID: String?)
}