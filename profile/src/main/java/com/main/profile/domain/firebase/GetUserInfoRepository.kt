package com.main.profile.domain.firebase

import com.main.profile.data.entities.UserInfo
import com.main.core.Resource

interface GetUserInfoRepository {

    suspend fun receiveUserInfo(): Resource<UserInfo>
}