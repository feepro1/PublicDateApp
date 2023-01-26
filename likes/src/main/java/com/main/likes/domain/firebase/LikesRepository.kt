package com.main.likes.domain.firebase

import com.main.core.Resource
import com.main.likes.data.entities.LikeFromUser

interface LikesRepository {

    suspend fun getAllLikes(): Resource<List<LikeFromUser>>
}