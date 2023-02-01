package com.main.likes.domain.firebase

import com.main.core.Resource
import com.main.core.entities.User

interface UserRepository {

    suspend fun getCurrentUser(): Resource<User>
}