package com.main.profile.data.realization

import com.main.core.Resource
import com.main.core.entities.User
import com.main.profile.data.database.ProfileFirebaseRepository
import com.main.profile.domain.firebase.GetUserInfoRepository

class GetUserInfoRepositoryImpl(
    private val profileFirebaseRepository: ProfileFirebaseRepository
) : GetUserInfoRepository {
    override suspend fun receiveUserInfo(): Resource<User> {
        return profileFirebaseRepository.getUserInfo()
    }
}