package com.main.dating.domain.usecases

import com.main.core.Resource
import com.main.dating.data.entities.User
import com.main.dating.data.realization.firebase.database.DatabaseRepository

class GetUsersFromDatabaseUseCase(
    private val databaseRepository: DatabaseRepository
) {

    suspend fun execute(): Resource<List<User>> {
        return databaseRepository.getUsers()
    }
}