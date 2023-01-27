package com.main.profile.data.entities

import com.main.core.entities.Like

data class UserInfo(
    val firstName: String = "",
    val lastName: String = "",
    val avatarUrl: String = "",
    val email: String = "",
    val uid: String = "",
    val likeFromAnotherUser: HashMap<String, Like> = hashMapOf(),
    val age: Int? = null,
    val city: String = "",
    val region: String = "",
    val aboutMe: String = "",
    val token: String = ""
)