package com.main.likes.domain.firebase

import com.main.core.Resource
import com.main.core.entities.User
import com.main.core.exception.ExceptionMessages.INTERNET_IS_UNAVAILABLE
import com.main.core.exception.NetworkException
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.mockito.Mockito
import org.mockito.kotlin.mock

class LikesRepositoryTest {

    private val likesRepository = mock<LikesRepository>()

    @Test
    fun `test successful get all likes`() = runBlocking {
        Mockito.`when`(likesRepository.getAllLikes()).thenReturn(
            Resource.Success(User())
        )
        val result = likesRepository.getAllLikes()
        Assertions.assertTrue(result.data?.likeFromAnotherUser?.isEmpty() == true)
    }

    @Test
    fun `test successful get all likes, but likes is empty`() = runBlocking {
        Mockito.`when`(likesRepository.getAllLikes()).thenReturn(
            Resource.Success(User())
        )
        val result = likesRepository.getAllLikes()
        Assertions.assertTrue(result.data?.likeFromAnotherUser?.isEmpty() == true)
    }

    @Test
    fun `test failure get all likes, internet is not available`() = runBlocking {
        Mockito.`when`(likesRepository.getAllLikes()).thenReturn(
            Resource.Error(User(), NetworkException(INTERNET_IS_UNAVAILABLE))
        )
        val result = likesRepository.getAllLikes()
        Assertions.assertTrue(result.exception?.message == INTERNET_IS_UNAVAILABLE)
    }

    @Test
    fun `test successful like user`() = runBlocking {
        Mockito.`when`(likesRepository.likeUser(User())).thenReturn(
            Resource.Success(true)
        )
        val result = likesRepository.likeUser(User())
        Assertions.assertTrue(result.data == true)
    }

    @Test
    fun `test failure like user, internet is not available`() = runBlocking {
        Mockito.`when`(likesRepository.likeUser(User())).thenReturn(
            Resource.Error(false, NetworkException(INTERNET_IS_UNAVAILABLE))
        )
        val result = likesRepository.likeUser(User())
        Assertions.assertTrue(result.exception?.message == INTERNET_IS_UNAVAILABLE)
    }
}