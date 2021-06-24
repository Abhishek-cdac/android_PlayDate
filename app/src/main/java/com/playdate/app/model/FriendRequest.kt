package com.playdate.app.model

import com.google.gson.annotations.SerializedName

class FriendRequest {
    @SerializedName("action")
    var action: String? = null

    @SerializedName("requestId")
    var requestId: String? = null

    @SerializedName("status")
    var status: String? = null

    @SerializedName("toUserID")
    var toUserID: String? = null

    @SerializedName("userID")
    var userID: String? = null

    @SerializedName("UserInfo")
    var userInfo: List<UserInfo>? = null

    @SerializedName("_id")
    private var m_id: String? = null
    fun get_id(): String? {
        return m_id
    }

    fun set_id(_id: String?) {
        m_id = _id
    }
}