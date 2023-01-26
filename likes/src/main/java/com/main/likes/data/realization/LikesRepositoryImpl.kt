package com.main.likes.data.realization

import com.main.core.Resource
import com.main.likes.data.entities.LikeFromUser
import com.main.likes.data.firebase.LikesFirebaseRepository
import com.main.likes.domain.firebase.LikesRepository

class LikesRepositoryImpl(
    private val likesFirebaseRepository: LikesFirebaseRepository
) : LikesRepository {

    override suspend fun getAllLikes(): Resource<LikeFromUser> {
        return likesFirebaseRepository.getAllLikes()
    }

}