package com.main.core.entities

data class User(
    val firstName: String = "",
    val lastName: String = "",
    val avatarUrl: String = "",
    val email: String = "",
    val uid: String = "",
    val likeFromAnotherUser: MutableMap<String, Like> = mutableMapOf(),
    val userChats: MutableMap<String, Chat> = mutableMapOf(),
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