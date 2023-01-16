package com.main.profile.domain.usecases

import com.main.core.Resource
import com.main.profile.data.entities.UserInfo
import com.main.profile.domain.firebase.GetUserInfoRepository

class GetUserInfoUseCase(
    private val getUserInfoRepository: GetUserInfoRepository
) {

    suspend fun execute(): Resource<UserInfo> {
        return getUserInfoRepository.receiveUserInfo()
    }
}