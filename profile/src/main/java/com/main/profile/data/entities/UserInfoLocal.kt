package com.main.profile.data.entities

import android.graphics.Bitmap

data class UserInfoLocal(
    val firstName: String = "",
    val lastName: String = "",
    val avatar: Bitmap? = null,
    val email: String = "",
    val uid: String = "",
    val likeFromAnotherUser: HashMap<String, String?> = hashMapOf(),
    val age: Int? = null,
    val city: String = "",
    val region: String = "",
    val aboutMe: String = "",
    val token: String = ""
) {

    fun mapUserInfo(): UserInfo {
        return UserInfo(
            firstName = firstName,
            lastName = lastName,
            email = email,
            uid = uid,
            likeFromAnotherUser = likeFromAnotherUser,
            age = age,
            city = city,
            region = region,
            aboutMe = aboutMe,
            token = token
        )
    }
}