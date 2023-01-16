package com.main.profile.data.firebase

import com.main.profile.data.entities.UserInfo
import com.main.profile.domain.firebase.SaveUserInfoRepository
import com.main.core.Resource

class SaveUserInfoRepositoryImpl : SaveUserInfoRepository {
    override suspend fun saveUserInfo(userInfo: UserInfo): Resource<Boolean> {
        TODO("Not yet implemented")
    }
}