package com.main.likes.domain.usecases

import com.main.core.Resource
import com.main.core.entities.User
import com.main.core.exception.ExceptionMessages.INTERNET_IS_UNAVAILABLE
import com.main.core.exception.NetworkException
import com.main.likes.domain.firebase.UserRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.mockito.Mockito
import org.mockito.kotlin.mock

class GetCurrentUserUseCaseTest {

    private val userRepository = mock<UserRepository>()
    private val getCurrentUserUseCase = GetCurrentUserUseCase(
        userRepository = userRepository
    )

    @Test
    fun `test successful get current user`() = runBlocking {
        Mockito.`when`(userRepository.getCurrentUser()).thenReturn(
            Resource.Success(User())
        )
        val result = getCurrentUserUseCase.execute()
        Assertions.assertTrue(result.exception == null)
    }

    @Test
    fun `test failure get current user, internet is not available`() = runBlocking {
        Mockito.`when`(userRepository.getCurrentUser()).thenReturn(
            Resource.Error(User(), NetworkException(INTERNET_IS_UNAVAILABLE))
        )
        val result = getCurrentUserUseCase.execute()
        Assertions.assertTrue(result.exception?.message == INTERNET_IS_UNAVAILABLE)
    }
}