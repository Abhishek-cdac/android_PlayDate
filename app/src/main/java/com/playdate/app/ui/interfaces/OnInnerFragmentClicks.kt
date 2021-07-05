package com.playdate.app.ui.interfaces

import androidx.fragment.app.Fragment

interface OnInnerFragmentClicks {
    fun ReplaceFrag(fragment: Fragment?)
    fun ReplaceFragWithStack(fragment: Fragment?)
    fun NoFriends()
    fun Reset()
    fun loadProfile(UserID: String?)
    fun loadMatchProfile(UserID: String?)
}