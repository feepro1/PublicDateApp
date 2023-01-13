package com.main.dating.domain.firebase.database

import com.main.core.Resource
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class DatabaseRepositoryTest {

    private val databaseRepository = mock<DatabaseRepository>()
    private val users = listOf(
        User(
            firstName = "Vadym", lastName = "Hrynyk",
            avatarUrl = "https://some", email = "vadwwxz@mail.com"
        )
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
        val result = databaseRepository.getUsers()

        Assertions.assertTrue(result.data.isNotEmpty())
    }

    @Test
    fun `test failure get users, internet is unavailable`() = runBlocking {
        Mockito.`when`(databaseRepository.getUsers()).thenReturn(
            Resource.Error(emptyList(), NetworkException(INTERNET_IS_UNAVAILABLE))
        )

        val result = databaseRepository.getUsers()

        val assertsResult = result.exception.message == INTERNET_IS_UNAVAILABLE
        Assertions.assertTrue(assertsResult)
    }

}