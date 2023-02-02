package com.main.likes.data.realization

import com.main.core.Resource
import com.main.core.entities.User
import com.main.likes.data.firebase.UserFirebaseRepository
import com.main.likes.domain.firebase.UserRepository

class UserRepositoryImpl(
    private val userFirebaseRepository: UserFirebaseRepository
) : UserRepository {
    override suspend fun getUserByUid(uid: String): Resource<User> {
        return userFirebaseRepository.getCurrentUser(uid)
    }
}