package com.example.profile.domain.usecases

import com.example.profile.data.entities.UserInfo
import com.example.profile.domain.firebase.GetUserInfoRepository
import com.main.core.Resource

class GetUserInfoUseCase(
    private val getUserInfoRepository: GetUserInfoRepository
) {

    suspend fun execute(): Resource<UserInfo> {
        return getUserInfoRepository.receiveUserInfo()
    }
}