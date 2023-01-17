package com.main.profile.data.realization

import com.main.core.Resource
import com.main.core.exception.DefaultException
import com.main.profile.data.database.ProfileFirebaseRepository
import com.main.profile.data.entities.UserInfoLocal
import com.main.profile.domain.firebase.SaveUserInfoRepository
import kotlinx.coroutines.tasks.await

class SaveUserInfoRepositoryImpl(
    private val profileFirebaseRepository: ProfileFirebaseRepository
) : SaveUserInfoRepository {
    override suspend fun saveUserInfo(userInfoLocal: UserInfoLocal): Resource<Boolean> {
        val uploadTask = userInfoLocal.avatar?.let { profileFirebaseRepository.addImageToStorage(it) }
        try {
            uploadTask?.await()
        } catch (e: Exception) {
            return Resource.Error(false, DefaultException("Default"))
        }
        return profileFirebaseRepository.saveUserInfo(userInfoLocal.mapUserInfo())
    }
}