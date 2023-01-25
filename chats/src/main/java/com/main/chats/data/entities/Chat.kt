package com.main.chats.data.entities

import com.main.core.base.entity.Chat

data class Chat(
    val firstName: String = "",
    val lastName: String = "",
    val avatarUrl: String = "",
    val uid: String = "",
    val lastMessage: String = ""
) {

    fun mapToCoreChat(): Chat {
        return Chat(
            firstName = firstName,
            lastName = lastName,
            avatarUrl = avatarUrl,
            uid = uid
        )
    }
}