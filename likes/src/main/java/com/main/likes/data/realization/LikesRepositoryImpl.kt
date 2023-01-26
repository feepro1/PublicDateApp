package com.main.likes.data.realization

import com.main.core.Resource
import com.main.likes.data.entities.LikeFromUser
import com.main.likes.domain.firebase.LikesRepository

class LikesRepositoryImpl : LikesRepository {

    override suspend fun getAllLikes(): Resource<List<LikeFromUser>> {
        TODO("Not yet implemented")
    }


}