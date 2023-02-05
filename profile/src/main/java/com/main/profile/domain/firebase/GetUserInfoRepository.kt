package com.main.profile.domain.firebase

import com.main.core.Resource
import com.main.core.entities.User

interface GetUserInfoRepository {

    suspend fun receiveUserInfo(): Resource<User>
}