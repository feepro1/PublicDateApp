package com.main.core.base.entity

data class Chat(
    val firstName: String = "",
    val lastName: String = "",
    val avatarUrl: String = "",
    val uid: String = "",
    var lastMessage: String = ""
)