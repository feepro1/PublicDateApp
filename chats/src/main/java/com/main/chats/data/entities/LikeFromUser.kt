package com.main.chats.data.entities

data class LikeFromUser(
    val firstName: String = "",
    val lastName: String = "",
    val avatarUrl: String = "",
    val email: String = "",
    val uid: String = "",
    val likeFromAnotherUser: HashMap<String, String?> = hashMapOf(),
    val age: Int? = null,
    val city: String = "",
    val region: String = "",
    val aboutMe: String = ""
)