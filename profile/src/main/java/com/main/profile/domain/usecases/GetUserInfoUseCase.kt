package com.main.profile.domain.usecases

import com.main.core.Resource
import com.main.core.entities.User
import com.main.profile.domain.firebase.GetUserInfoRepository

class GetUserInfoUseCase(
    private val getUserInfoRepository: GetUserInfoRepository
) {

    suspend fun execute(): Resource<User> {
        return getUserInfoRepository.receiveUserInfo()
    }
}