package com.main.chats.domain.usecases

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
        Mockito.`when`(likesRepository.getAllChats()).thenReturn(
            Resource.Success(listOf(Like()))
        )
        val result = getAllLikesUseCase.execute()
        Assertions.assertTrue(result.data.isNotEmpty())
    }

    @Test
    fun `test successful get all likes, but likes is empty`() = runBlocking {
        Mockito.`when`(likesRepository.getAllChats()).thenReturn(
            Resource.Success(emptyList())
        )
        val result = getAllLikesUseCase.execute()
        Assertions.assertTrue(result.data.isNotEmpty())
    }

    @Test
    fun `test failure get all likes, internet is not available`() = runBlocking {
        Mockito.`when`(likesRepository.getAllChats()).thenReturn(
            Resource.Error(emptyList(), NetworkException(INTERNET_IS_UNAVAILABLE))
        )
        val result = getAllLikesUseCase.execute()
        Assertions.assertTrue(result.exception?.message == INTERNET_IS_UNAVAILABLE)
    }
}