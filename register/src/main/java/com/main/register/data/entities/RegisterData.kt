package com.main.register.data.entities

import android.graphics.Bitmap

data class RegisterData(
    val password: String = "",
    val confirmPassword: String = "",
    val avatar: Bitmap? = null,
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val uid: String = "",
    val likeFromAnotherUser: HashMap<String, String?> = hashMapOf(),
    val age: Int? = null,
    val city: String = "",
    val region: String = "",
    val aboutMe: String = "",
    val token: String = ""
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RegisterData

        if (email != other.email) return false
        if (password != other.password) return false
        if (confirmPassword != other.confirmPassword) return false
        if (avatar != other.avatar) return false
        if (firstName != other.firstName) return false
        if (lastName != other.lastName) return false

        return true
    }

    override fun hashCode(): Int {
        var result = email.hashCode()
        result = 31 * result + password.hashCode()
        result = 31 * result + confirmPassword.hashCode()
        result = 31 * result + avatar.hashCode()
        result = 31 * result + firstName.hashCode()
        result = 31 * result + lastName.hashCode()
        return result
    }

    fun mapToRegisterDataForDatabase(): RegisterDataForDatabase {
        return RegisterDataForDatabase(
            email = email,
            firstName = firstName,
            lastName = lastName,
            uid = uid
        )
    }
}