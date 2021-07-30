package com.playdate.app.ui.dashboard

interface OnFriendSelected {
    fun onSingleFriendSelected(ID: String?, FreindID: String?)
    fun onFrinedDataClosed()
    fun onSuggestionClosed()
    fun onSuggestionClosed(isFriend: Boolean, ID: String?)
}