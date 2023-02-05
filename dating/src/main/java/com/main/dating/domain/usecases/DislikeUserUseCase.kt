package com.main.dating.domain.usecases

import com.main.core.Resource
import com.main.core.entities.User
import com.main.dating.domain.firebase.repository.ManageUserRepository

class DislikeUserUseCase(
    private val manageUserRepository: ManageUserRepository
) {

    suspend fun execute(user: User): Resource<Boolean> {
        return manageUserRepository.dislikeUser(user)
    }
}