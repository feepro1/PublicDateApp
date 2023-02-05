package com.main.likes.domain.firebase

import com.main.core.Resource
import com.main.core.entities.User
import com.main.core.exception.ExceptionMessages.INTERNET_IS_UNAVAILABLE
import com.main.core.exception.ExceptionMessages.UID_IS_EMPTY
import com.main.core.exception.NetworkException
import com.main.core.exception.UidException
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.mockito.Mockito
import org.mockito.kotlin.mock

class UserRepositoryTest {

    private val userRepository = mock<UserRepository>()

    @Test
    fun `test successful get user by uid`() = runBlocking {
        Mockito.`when`(userRepository.getUserByUid("test_uid")).thenReturn(
            Resource.Success(User())
        )
        val result = userRepository.getUserByUid("test_uid")
        Assertions.assertTrue(result.exception == null)
    }

    @Test
    fun `test failure get user by uid, internet is not available`() = runBlocking {
        Mockito.`when`(userRepository.getUserByUid("test_uid")).thenReturn(
            Resource.Error(User(), NetworkException(INTERNET_IS_UNAVAILABLE))
        )
        val result = userRepository.getUserByUid("test_uid")
        Assertions.assertTrue(result.exception?.message == INTERNET_IS_UNAVAILABLE)
    }

    fun `test failure get user by uid, uid is empty`() = runBlocking {
        Mockito.`when`(userRepository.getUserByUid("")).thenReturn(
            Resource.Error(User(), UidException(UID_IS_EMPTY))
        )
        val result = userRepository.getUserByUid("")
        Assertions.assertTrue(result.exception?.message == UID_IS_EMPTY)
    }
}