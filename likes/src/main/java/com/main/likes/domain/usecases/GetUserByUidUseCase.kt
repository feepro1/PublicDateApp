package com.main.likes.domain.usecases

import com.main.core.Resource
import com.main.core.entities.User
import com.main.likes.domain.firebase.UserRepository

class GetUserByUidUseCase(
    private val userRepository: UserRepository
) {

    suspend fun execute(uid: String): Resource<User> {
        return userRepository.getUserByUid(uid)
    }
}