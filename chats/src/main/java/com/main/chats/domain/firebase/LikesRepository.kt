package com.main.chats.domain.firebase

import com.main.chats.data.entities.LikeFromUser
import com.main.core.Resource

interface LikesRepository {

    suspend fun getAllLikes(): Resource<List<LikeFromUser>>
}