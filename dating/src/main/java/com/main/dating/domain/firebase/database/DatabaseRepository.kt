package com.main.dating.domain.firebase.database

import com.main.core.Resource
import com.main.core.entities.User

interface DatabaseRepository {

    suspend fun getUsers(): Resource<List<User>>
}