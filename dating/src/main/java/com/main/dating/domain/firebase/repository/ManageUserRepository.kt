package com.main.dating.domain.firebase.repository

import com.main.core.Resource
import com.main.dating.data.entities.User

interface ManageUserRepository {

    suspend fun likeUser(user: User): Resource<Boolean>

    suspend fun dislikeUser(user: User): Resource<Boolean>
}