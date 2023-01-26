package com.main.likes.domain.usecase

import com.main.core.Resource
import com.main.core.entities.Like
import com.main.core.exception.ExceptionMessages.INTERNET_IS_UNAVAILABLE
import com.main.core.exception.NetworkException
import com.main.likes.data.entities.LikeFromUser
import com.main.likes.domain.firebase.LikesRepository
import com.main.likes.domain.usecases.GetAllLikesUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.mockito.Mockito
import org.mockito.kotlin.mock

class GetAllLikesUseCaseTest {

    private val likesRepository = mock<LikesRepository>()
    private val getAllLikesUseCase = GetAllLikesUseCase(
        likesRepository = likesRepository
    )

    @Test
    fun `test successful get all likes`() = runBlocking {
        Mockito.`when`(likesRepository.getAllLikes()).thenReturn(
            Resource.Success(LikeFromUser())
        )
        val result = getAllLikesUseCase.execute()
        Assertions.assertTrue(result.data?.likeFromAnotherUser?.isEmpty() == true)
    }

    @Test
    fun `test successful get all likes, but likes is empty`() = runBlocking {
        Mockito.`when`(likesRepository.getAllLikes()).thenReturn(
            Resource.Success(LikeFromUser())
        )
        val result = getAllLikesUseCase.execute()
        Assertions.assertTrue(result.data?.likeFromAnotherUser?.isEmpty() == true)
    }

    @Test
    fun `test failure get all likes, internet is not available`() = runBlocking {
        Mockito.`when`(likesRepository.getAllLikes()).thenReturn(
            Resource.Error(LikeFromUser(), NetworkException(INTERNET_IS_UNAVAILABLE))
        )
        val result = getAllLikesUseCase.execute()
        Assertions.assertTrue(result.exception?.message == INTERNET_IS_UNAVAILABLE)
    }
}