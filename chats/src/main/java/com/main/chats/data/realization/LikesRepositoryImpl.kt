package com.main.chats.data.realization

import com.main.chats.data.entities.LikeFromUser
import com.main.chats.domain.firebase.LikesRepository
import com.main.core.Resource

class LikesRepositoryImpl : LikesRepository {

    override suspend fun getAllLikes(): Resource<List<LikeFromUser>> {
        TODO("Not yet implemented")
    }
}