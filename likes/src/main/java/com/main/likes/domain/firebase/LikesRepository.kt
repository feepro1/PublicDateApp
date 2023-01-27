package com.main.likes.domain.firebase

import com.main.core.Resource
import com.main.likes.data.entities.LikeFromUser
import com.main.likes.data.entities.User

interface LikesRepository {

    suspend fun getAllLikes(): Resource<LikeFromUser>

    suspend fun likeUser(user: User): Resource<Boolean>
}