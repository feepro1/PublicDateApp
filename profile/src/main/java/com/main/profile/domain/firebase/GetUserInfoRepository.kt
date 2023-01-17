package com.main.profile.domain.firebase

import com.main.core.Resource
import com.main.profile.data.entities.UserInfo

interface GetUserInfoRepository {

    suspend fun receiveUserInfo(): Resource<UserInfo>
}