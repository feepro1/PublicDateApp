package com.main.likes.domain.firebase

import com.main.core.Resource
import com.main.core.entities.User

interface LikesRepository {

    suspend fun getAllLikes(): Resource<User>

    suspend fun likeUser(user: User): Resource<Boolean>
}