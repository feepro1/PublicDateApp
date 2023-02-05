package com.main.likes.domain.usecases

import com.main.core.Resource
import com.main.core.entities.User
import com.main.core.exception.ExceptionMessages.INTERNET_IS_UNAVAILABLE
import com.main.core.exception.ExceptionMessages.UID_IS_EMPTY
import com.main.core.exception.NetworkException
import com.main.core.exception.UidException
import com.main.likes.domain.firebase.UserRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.mockito.Mockito
import org.mockito.kotlin.mock

class GetUserByUidUseCaseTest {

    private val userRepository = mock<UserRepository>()
    private val getUserByUidUseCase = GetUserByUidUseCase(
        userRepository = userRepository
    )

    @Test
    fun `test successful get user by uid`() = runBlocking {
        Mockito.`when`(userRepository.getUserByUid("test_uid")).thenReturn(
            Resource.Success(User())
        )
        val result = getUserByUidUseCase.execute("test_uid")
        Assertions.assertTrue(result.exception == null)
    }

    @Test
    fun `test failure get user by uid, internet is not available`() = runBlocking {
        Mockito.`when`(userRepository.getUserByUid("test_uid")).thenReturn(
            Resource.Error(User(), NetworkException(INTERNET_IS_UNAVAILABLE))
        )
        val result = getUserByUidUseCase.execute("test_uid")
        Assertions.assertTrue(result.exception?.message == INTERNET_IS_UNAVAILABLE)
    }

    @Test
    fun `test failure get user by uid, uid is empty`() = runBlocking {
        Mockito.`when`(userRepository.getUserByUid("")).thenReturn(
            Resource.Error(User(), UidException(UID_IS_EMPTY))
        )
        val result = getUserByUidUseCase.execute("")
        Assertions.assertTrue(result.exception?.message == UID_IS_EMPTY)
    }
}