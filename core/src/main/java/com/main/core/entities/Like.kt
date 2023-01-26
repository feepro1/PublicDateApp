package com.main.core.entities

data class Like(
    val message: String = "",
    val isMutualLike: Boolean = false,
    val firstName: String = "",
    val lastName: String = "",
    val age: Int = 0,
    val city: String = "",
    val avatarUrl: String = ""
)
