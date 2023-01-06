package com.main.register.data.entities

data class RegisterData(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val avatar: ByteArray = byteArrayOf(),
    val firstName: String = "",
    val lastName: String = ""
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RegisterData

        if (email != other.email) return false
        if (password != other.password) return false
        if (confirmPassword != other.confirmPassword) return false
        if (!avatar.contentEquals(other.avatar)) return false
        if (firstName != other.firstName) return false
        if (lastName != other.lastName) return false

        return true
    }

    override fun hashCode(): Int {
        var result = email.hashCode()
        result = 31 * result + password.hashCode()
        result = 31 * result + confirmPassword.hashCode()
        result = 31 * result + avatar.contentHashCode()
        result = 31 * result + firstName.hashCode()
        result = 31 * result + lastName.hashCode()
        return result
    }
}
