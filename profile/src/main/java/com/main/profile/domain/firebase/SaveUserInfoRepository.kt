package com.main.profile.domain.firebase

import com.main.profile.data.entities.UserInfo
import com.main.core.Resource

interface SaveUserInfoRepository {

    suspend fun saveUserInfo(userInfo: UserInfo): Resource<Boolean>
}