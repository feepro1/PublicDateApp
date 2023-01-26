package com.main.likes.domain.usecases


import com.main.core.Resource
import com.main.likes.data.entities.LikeFromUser
import com.main.likes.domain.firebase.LikesRepository

class GetAllLikesUseCase(
    private val likesRepository: LikesRepository
) {

    suspend fun execute(): Resource<LikeFromUser> {
        return likesRepository.getAllLikes()
    }
}