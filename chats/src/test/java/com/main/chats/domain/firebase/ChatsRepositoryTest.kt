package com.main.chats.domain.firebase

import com.main.core.Resource
import com.main.core.entities.Chat
import com.main.core.exception.ExceptionMessages.INTERNET_IS_UNAVAILABLE
import com.main.core.exception.NetworkException
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.mockito.Mockito
import org.mockito.kotlin.mock

class ChatsRepositoryTest {

    private val chatsRepository = mock<ChatsRepository>()

    @Test
    fun `test successful get all chats`() = runBlocking {
        Mockito.`when`(chatsRepository.getAllChats()).thenReturn(
            Resource.Success(listOf(Chat()))
        )
        val result = chatsRepository.getAllChats()
        Assertions.assertTrue(result.data?.isNotEmpty() == true)
    }

    @Test
    fun `test successful get all chats, but chats is empty`() = runBlocking {
        Mockito.`when`(chatsRepository.getAllChats()).thenReturn(
            Resource.Success(emptyList())
        )
        val result = chatsRepository.getAllChats()
        Assertions.assertTrue(result.data?.isEmpty() == true)
    }

    @Test
    fun `test failure get all chats, internet is not available`() = runBlocking {
        Mockito.`when`(chatsRepository.getAllChats()).thenReturn(
            Resource.Error(emptyList(), NetworkException(INTERNET_IS_UNAVAILABLE))
        )
        val result = chatsRepository.getAllChats()
        Assertions.assertTrue(result.exception?.message == INTERNET_IS_UNAVAILABLE)
    }

    @Test
    fun `test successful delete chat`() = runBlocking {
        Mockito.`when`(chatsRepository.deleteChat(Chat())).thenReturn(
            Resource.Success(true)
        )
        val result = chatsRepository.deleteChat(Chat())
        Assertions.assertTrue(result.data == true)
    }

    @Test
    fun `test failure delete chat, internet is not available`() = runBlocking {
        Mockito.`when`(chatsRepository.deleteChat(Chat())).thenReturn(
            Resource.Error(false, NetworkException(INTERNET_IS_UNAVAILABLE))
        )
        val result = chatsRepository.deleteChat(Chat())
        Assertions.assertTrue(result.exception?.message == INTERNET_IS_UNAVAILABLE)
    }
}