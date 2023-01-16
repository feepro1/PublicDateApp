package com.main.profile.domain.usecases

import com.main.core.Resource
import com.main.profile.data.entities.UserInfoLocal
import com.main.profile.domain.firebase.SaveUserInfoRepository

class SaveUserInfoUseCase(
    private val saveUserInfoRepository: SaveUserInfoRepository
) {

    suspend fun execute(userInfoLocal: UserInfoLocal): Resource<Boolean> {
        return saveUserInfoRepository.saveUserInfo(userInfoLocal)
    }
}