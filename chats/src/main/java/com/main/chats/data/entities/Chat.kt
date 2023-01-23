package com.main.chats.data.entities

data class Chat(
    val firstName: String = "",
    val lastName: String = "",
    val avatarUrl: String = "",
    val uid: String = "",
    val lastMessage: String = ""
) {

    fun mapToCoreChat(): com.main.core.entity.Chat {
        return com.main.core.entity.Chat(
            firstName = firstName,
            lastName = lastName,
            avatarUrl = avatarUrl,
            uid = uid
        )
    }
}