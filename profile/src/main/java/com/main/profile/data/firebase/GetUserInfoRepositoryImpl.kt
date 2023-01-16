package com.main.profile.data.firebase

import com.main.profile.data.entities.UserInfo
import com.main.profile.domain.firebase.GetUserInfoRepository
import com.main.core.Resource

class GetUserInfoRepositoryImpl : GetUserInfoRepository {
    override suspend fun receiveUserInfo(): Resource<UserInfo> {
        TODO("Not yet implemented")
    }
}