package com.main.dating.domain.firebase.database

import com.main.core.Resource
import com.main.dating.data.entities.User

interface DatabaseRepository {

    suspend fun getUsers(): Resource<List<User>>

    class Base : DatabaseRepository {

        override suspend fun getUsers(): Resource<List<User>> {
            TODO("Not yet implemented")
        }
    }

}