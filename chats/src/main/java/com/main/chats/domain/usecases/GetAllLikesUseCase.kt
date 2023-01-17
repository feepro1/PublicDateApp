package com.main.chats.domain.usecases

import com.main.chats.data.entities.LikeFromUser
import com.main.chats.domain.firebase.LikesRepository
import com.main.core.Resource

class GetAllLikesUseCase(
    private val likesRepository: LikesRepository
) {

    suspend fun execute(): Resource<List<LikeFromUser>> {
        return likesRepository.getAllLikes()
    }
}