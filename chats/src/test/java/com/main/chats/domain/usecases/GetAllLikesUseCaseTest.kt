package com.main.chats.domain.usecases

import com.main.chats.data.entities.LikeFromUser
import com.main.chats.data.exception.messages.ChatsExceptionMessages.INTERNET_IS_UNAVAILABLE
import com.main.chats.domain.firebase.LikesRepository
import com.main.core.Resource
import com.main.core.exception.NetworkException
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
            Resource.Success(listOf(LikeFromUser()))
        )
        val result = getAllLikesUseCase.execute()
        Assertions.assertTrue(result.data?.isNotEmpty() == true)
    }

    @Test
    fun `test successful get all likes, but likes is empty`() = runBlocking {
        Mockito.`when`(likesRepository.getAllLikes()).thenReturn(
            Resource.Success(emptyList())
        )
        val result = getAllLikesUseCase.execute()
        Assertions.assertTrue(result.data?.isEmpty() == true)
    }

    @Test
    fun `test failure get all likes, internet is not available`() = runBlocking {
        Mockito.`when`(likesRepository.getAllLikes()).thenReturn(
            Resource.Error(emptyList(), NetworkException(INTERNET_IS_UNAVAILABLE))
        )
        val result = getAllLikesUseCase.execute()
        Assertions.assertTrue(result.exception?.message == INTERNET_IS_UNAVAILABLE)
    }
}