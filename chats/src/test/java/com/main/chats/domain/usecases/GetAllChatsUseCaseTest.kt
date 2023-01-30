package com.main.chats.domain.usecases

import com.main.chats.domain.firebase.ChatsRepository
import com.main.core.Resource
import com.main.core.base.entity.Chat
import com.main.core.exception.ExceptionMessages.INTERNET_IS_UNAVAILABLE
import com.main.core.exception.NetworkException
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.mockito.Mockito
import org.mockito.kotlin.mock

class GetAllChatsUseCaseTest {

    private val chatsRepository = mock<ChatsRepository>()
    private val getAllChatsUseCase = GetAllChatsUseCase(
        chatsRepository = chatsRepository
    )

    @Test
    fun `test successful get all chats`() = runBlocking {
        Mockito.`when`(chatsRepository.getAllChats()).thenReturn(
            Resource.Success(listOf(Chat()))
        )
        val result = getAllChatsUseCase.execute()
        Assertions.assertTrue(result.data?.isNotEmpty() == true)
    }

    @Test
    fun `test successful get all chats, but chats is empty`() = runBlocking {
        Mockito.`when`(chatsRepository.getAllChats()).thenReturn(
            Resource.Success(emptyList())
        )
        val result = getAllChatsUseCase.execute()
        Assertions.assertTrue(result.data?.isEmpty() == true)
    }

    @Test
    fun `test failure get all chats, internet is not available`() = runBlocking {
        Mockito.`when`(chatsRepository.getAllChats()).thenReturn(
            Resource.Error(emptyList(), NetworkException(INTERNET_IS_UNAVAILABLE))
        )
        val result = getAllChatsUseCase.execute()
        Assertions.assertTrue(result.exception?.message == INTERNET_IS_UNAVAILABLE)
    }
}