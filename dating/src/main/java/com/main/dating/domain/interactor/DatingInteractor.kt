package com.main.dating.domain.interactor

import com.main.core.Resource
import com.main.core.entities.User
import com.main.dating.domain.usecases.DislikeUserUseCase
import com.main.dating.domain.usecases.GetUsersFromDatabaseUseCase
import com.main.dating.domain.usecases.LikeUserUseCase

class DatingInteractor(
    private val getUsersFromDatabaseUseCase: GetUsersFromDatabaseUseCase,
    private val likeUserUseCase: LikeUserUseCase,
    private val dislikeUserUseCase: DislikeUserUseCase
) {

    suspend fun getUsersFromDatabase(): Resource<List<User>> {
        return getUsersFromDatabaseUseCase.execute()
    }

    suspend fun likeUser(user: User): Resource<Boolean> {
        return likeUserUseCase.execute(user)
    }

    suspend fun dislikeUser(user: User): Resource<Boolean> {
        return dislikeUserUseCase.execute(user)
    }
}