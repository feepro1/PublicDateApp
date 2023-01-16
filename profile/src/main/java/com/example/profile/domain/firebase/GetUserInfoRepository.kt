package com.example.profile.domain.firebase

import com.example.profile.data.entities.UserInfo
import com.main.core.Resource

interface GetUserInfoRepository {

    suspend fun receiveUserInfo(): Resource<UserInfo>
}