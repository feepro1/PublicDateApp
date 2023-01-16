package com.example.profile.data.firebase

import com.example.profile.data.entities.UserInfo
import com.example.profile.domain.firebase.GetUserInfoRepository
import com.main.core.Resource

class GetUserInfoRepositoryImpl : GetUserInfoRepository {
    override suspend fun receiveUserInfo(): Resource<UserInfo> {
        TODO("Not yet implemented")
    }
}