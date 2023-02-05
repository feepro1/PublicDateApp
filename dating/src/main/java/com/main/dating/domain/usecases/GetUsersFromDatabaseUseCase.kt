package com.main.dating.domain.usecases

import com.main.core.Resource
import com.main.core.entities.User
import com.main.dating.domain.firebase.database.DatabaseRepository

class GetUsersFromDatabaseUseCase(
    private val databaseRepository: DatabaseRepository
) {

    suspend fun execute(): Resource<List<User>> {
        return databaseRepository.getUsers()
    }
}