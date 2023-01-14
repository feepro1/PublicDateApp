package com.main.dating.data.realization.firebase.database

import com.main.core.Resource
import com.main.dating.data.entities.User
import com.main.dating.domain.firebase.database.DatabaseRepository

class DatabaseRepositoryImpl : DatabaseRepository {

    override suspend fun getUsers(): Resource<List<User>> {
        TODO("Not yet implemented")
    }
}