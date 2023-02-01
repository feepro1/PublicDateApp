package com.main.likes.domain.usecases

import com.main.core.Resource
import com.main.core.entities.User
import com.main.likes.domain.firebase.UserRepository

class GetCurrentUserUseCase(
    private val userRepository: UserRepository
) {

    suspend fun execute(): Resource<User> {
        return userRepository.getCurrentUser()
    }
}