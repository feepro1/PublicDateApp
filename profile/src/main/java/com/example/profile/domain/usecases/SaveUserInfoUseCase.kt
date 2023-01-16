package com.example.profile.domain.usecases

import com.example.profile.data.entities.UserInfo
import com.example.profile.domain.firebase.SaveUserInfoRepository
import com.main.core.Resource

class SaveUserInfoUseCase(
    private val saveUserInfoRepository: SaveUserInfoRepository
) {

    suspend fun execute(userInfo: UserInfo): Resource<Boolean> {
        return saveUserInfoRepository.saveUserInfo(userInfo)
    }
}