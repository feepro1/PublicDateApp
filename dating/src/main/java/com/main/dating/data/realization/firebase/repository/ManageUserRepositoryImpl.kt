package com.main.dating.data.realization.firebase.repository

import com.main.core.Resource
import com.main.dating.data.entities.User
import com.main.dating.domain.firebase.repository.ManageUserRepository

class ManageUserRepositoryImpl : ManageUserRepository {

    override suspend fun likeUser(user: User): Resource<Boolean> {
        return Resource.Success(true)
    }

    override suspend fun dislikeUser(user: User): Resource<Boolean> {
        return Resource.Success(true)
    }
}