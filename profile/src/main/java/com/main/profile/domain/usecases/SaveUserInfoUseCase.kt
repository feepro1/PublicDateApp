package com.main.profile.domain.usecases

import com.main.profile.data.entities.UserInfo
import com.main.profile.domain.firebase.SaveUserInfoRepository
import com.main.core.Resource

class SaveUserInfoUseCase(
    private val saveUserInfoRepository: SaveUserInfoRepository
) {

    suspend fun execute(userInfo: UserInfo): Resource<Boolean> {
        return saveUserInfoRepository.saveUserInfo(userInfo)
    }
}