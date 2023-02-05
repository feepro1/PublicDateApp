package com.main.likes.domain.usecases

import com.main.core.Resource
import com.main.core.entities.User
import com.main.likes.domain.firebase.LikesRepository

class LikeUserUseCase(
    private val likesRepository: LikesRepository
) {

    suspend fun execute(user: User): Resource<Boolean> {
        return likesRepository.likeUser(user)
    }
}