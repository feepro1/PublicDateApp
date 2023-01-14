package com.main.dating.domain.usecases

import com.main.core.Resource
import com.main.core.exception.NetworkException
import com.main.dating.data.entities.User
import com.main.dating.data.exception.message.DatingExceptionMessages.INTERNET_IS_UNAVAILABLE
import com.main.dating.domain.firebase.database.DatabaseRepository
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class GetUsersFromDatabaseUseCaseUseCaseTest {

    private val databaseRepository = mock<DatabaseRepository>()
    private val getUsersFromDatabaseUseCase = GetUsersFromDatabaseUseCase(
        databaseRepository = databaseRepository
    )

    @Test
    fun `test successful get users`() = runBlocking {
        Mockito.`when`(databaseRepository.getUsers()).thenReturn(
            Resource.Success(
                listOf(
                    User(
                        firstName = "Vadym", lastName = "Hrynyk",
                        avatarUrl = "https://some", email = "vadwwxz@mail.com"
                    )
                )
            )
        )
        val result = getUsersFromDatabaseUseCase.execute()

        Assertions.assertTrue(result.data?.isNotEmpty() == true)
    }

    @Test
    fun `test failure get users, internet is unavailable`() = runBlocking {
        Mockito.`when`(databaseRepository.getUsers()).thenReturn(
            Resource.Error(emptyList(), NetworkException(INTERNET_IS_UNAVAILABLE))
        )

        val result = getUsersFromDatabaseUseCase.execute()

        val assertsResult = result.exception?.message == INTERNET_IS_UNAVAILABLE
        Assertions.assertTrue(assertsResult)
    }
}