package com.main.chats.presentation.viewmodel

import com.main.chats.BaseChatsTest
import com.main.chats.domain.firebase.ChatsRepository
import com.main.chats.domain.navigation.ChatsNavigation
import com.main.chats.domain.usecases.DeleteChatUseCase
import com.main.chats.domain.usecases.GetAllChatsUseCase
import com.main.core.Resource
import com.main.core.base.entity.Chat
import com.main.core.exception.ExceptionMessages.INTERNET_IS_UNAVAILABLE
import com.main.core.exception.NetworkException
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.mockito.Mockito
import org.mockito.kotlin.mock

class ChatsViewModelTest : BaseChatsTest() {

    private val chatsCommunication = TestChatsCommunication()
    private val chatsRepository = mock<ChatsRepository>()
    private val getAllChatsUseCase = GetAllChatsUseCase(chatsRepository)
    private val deleteChatUseCase = DeleteChatUseCase(chatsRepository)
    private val chatsViewModel = ChatsViewModel(
        getAllChatsUseCase = getAllChatsUseCase,
        deleteChatUseCase = deleteChatUseCase,
        chatsCommunication = chatsCommunication,
        chatsNavigation = ChatsNavigation.Base(),
        dispatchers = TestDispatchersList()
    )

    @Test
    fun `test successful get all chats`() = runBlocking {
        Mockito.`when`(chatsRepository.getAllChats()).thenReturn(
            Resource.Success(listOf(Chat()))
        )
        chatsViewModel.getAllChats()
        Assertions.assertTrue(chatsCommunication.chats.isNotEmpty())
    }

    @Test
    fun `test successful get all chats, but chats is empty`() = runBlocking {
        Mockito.`when`(chatsRepository.getAllChats()).thenReturn(
            Resource.Success(emptyList())
        )
        chatsViewModel.getAllChats()
        Assertions.assertTrue(chatsCommunication.chats.isNotEmpty())
    }

    @Test
    fun `test failure get all chats, internet is not available`() = runBlocking {
        Mockito.`when`(chatsRepository.getAllChats()).thenReturn(
            Resource.Error(emptyList(), NetworkException(INTERNET_IS_UNAVAILABLE))
        )
        chatsViewModel.getAllChats()
        val result = chatsCommunication.motionToastError.first() == INTERNET_IS_UNAVAILABLE
        Assertions.assertTrue(result)
    }

    @Test
    fun `test successful delete chat`() = runBlocking {
        val chat = Chat()
        Mockito.`when`(chatsRepository.deleteChat(chat)).thenReturn(
            Resource.Success(true)
        )
        chatsViewModel.deleteChat(chat)
        Assertions.assertTrue(chatsCommunication.deleteChat.first() == chat)
    }

    @Test
    fun `test failure delete chat, internet is not available`() = runBlocking {
        Mockito.`when`(chatsRepository.deleteChat(Chat())).thenReturn(
            Resource.Error(false, NetworkException(INTERNET_IS_UNAVAILABLE))
        )
        chatsViewModel.deleteChat(Chat())
        val result = chatsCommunication.motionToastError.first() == INTERNET_IS_UNAVAILABLE
        Assertions.assertTrue(result)
    }
}