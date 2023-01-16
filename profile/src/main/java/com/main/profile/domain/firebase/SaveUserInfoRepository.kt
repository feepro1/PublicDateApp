package com.main.profile.domain.firebase

import com.main.core.Resource
import com.main.profile.data.entities.UserInfoLocal

interface SaveUserInfoRepository {

    suspend fun saveUserInfo(userInfoLocal: UserInfoLocal): Resource<Boolean>
}