package com.example.profile.data.firebase

import com.example.profile.data.entities.UserInfo
import com.example.profile.domain.firebase.SaveUserInfoRepository
import com.main.core.Resource

class SaveUserInfoRepositoryImpl : SaveUserInfoRepository {
    override suspend fun saveUserInfo(userInfo: UserInfo): Resource<Boolean> {
        TODO("Not yet implemented")
    }
}