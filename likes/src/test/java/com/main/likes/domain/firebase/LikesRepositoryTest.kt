package com.main.likes.domain.firebase

import com.main.core.Resource
import com.main.core.exception.ExceptionMessages.INTERNET_IS_UNAVAILABLE
import com.main.core.exception.NetworkException
import com.main.likes.data.entities.LikeFromUser
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
            Resource.Success(LikeFromUser())
        )
        val result = likesRepository.getAllLikes()
        Assertions.assertTrue(result.data?.likeFromAnotherUser?.isEmpty() == true)
    }

    @Test
    fun `test successful get all likes, but likes is empty`() = runBlocking {
        Mockito.`when`(likesRepository.getAllLikes()).thenReturn(
            Resource.Success(LikeFromUser())
        )
        val result = likesRepository.getAllLikes()
        Assertions.assertTrue(result.data?.likeFromAnotherUser?.isEmpty() == true)
    }

    @Test
    fun `test failure get all likes, internet is not available`() = runBlocking {
        Mockito.`when`(likesRepository.getAllLikes()).thenReturn(
            Resource.Error(LikeFromUser(), NetworkException(INTERNET_IS_UNAVAILABLE))
        )
        val result = likesRepository.getAllLikes()
        Assertions.assertTrue(result.exception?.message == INTERNET_IS_UNAVAILABLE)
    }

}