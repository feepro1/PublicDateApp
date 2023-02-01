package com.main.likes.domain.firebase

import com.main.core.Resource
import com.main.core.entities.User
import com.main.core.exception.ExceptionMessages
import com.main.core.exception.NetworkException
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.mockito.Mockito
import org.mockito.kotlin.mock

class UserRepositoryTest {

    private val userRepository = mock<UserRepository>()

    @Test
    fun `test successful get current user`() = runBlocking {
        Mockito.`when`(userRepository.getCurrentUser()).thenReturn(
            Resource.Success(User())
        )
        val result = userRepository.getCurrentUser()
        Assertions.assertTrue(result.exception == null)
    }

    @Test
    fun `test failure get current user, internet is not available`() = runBlocking {
        Mockito.`when`(userRepository.getCurrentUser()).thenReturn(
            Resource.Error(User(), NetworkException(ExceptionMessages.INTERNET_IS_UNAVAILABLE))
        )
        val result = userRepository.getCurrentUser()
        Assertions.assertTrue(result.exception?.message == ExceptionMessages.INTERNET_IS_UNAVAILABLE)
    }
}