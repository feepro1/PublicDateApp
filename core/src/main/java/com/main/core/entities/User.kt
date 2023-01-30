package com.main.core.entities

import com.main.core.base.entity.Chat

data class User(
    val firstName: String = "",
    val lastName: String = "",
    val avatarUrl: String = "",
    val email: String = "",
    val uid: String = "",
    val likeFromAnotherUser: HashMap<String, Like> = hashMapOf(),
    val userChats: HashMap<String, Chat> = hashMapOf(),
    val age: Int? = null,
    val city: String = "",
    val region: String = "",
    val aboutMe: String = "",
    val token: String = ""
) {
    fun mapToChat(): Chat {
        return Chat(
            firstName = firstName,
            lastName = lastName,
            avatarUrl = avatarUrl,
            uid = uid
        )
    }
}