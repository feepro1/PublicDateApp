package com.main.chats.presentation

import com.main.core.Resource
import com.main.core.exception.NetworkException
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.mockito.Mockito
import org.mockito.kotlin.mock

class ChatsViewModelTest {

    private val chatsCommunication = TestChatsCommunication
    private val chatsRepository = mock<ChatsRepository>()
    private val likesRepository = mock<LikesRepository>()
    private val getAllChatsUseCase = GetAllChatsUseCase(chatsRepository)
    private val getAllLikesUseCase = GetAllLikesUseCase(likesRepository)
    private val chatsViewModel = ChatsViewModel(
        getAllChatsUseCase = getAllChatsUseCase,
        getAllLikesUseCase = getAllLikesUseCase,
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
        Assertions.assertTrue(chatsCommunication.chats.isEmpty())
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
    fun `test successful get all likes`() = runBlocking {
        Mockito.`when`(likesRepository.getAllLikes()).thenReturn(
            Resource.Success(listOf(Like()))
        )
        chatsViewModel.getAllLikes()
        Assertions.assertTrue(chatsCommunication.likes.isNotEmpty())
    }

    @Test
    fun `test successful get all likes, but likes is empty`() = runBlocking {
        Mockito.`when`(likesRepository.getAllLikes()).thenReturn(
            Resource.Success(emptyList())
        )
        chatsViewModel.getAllLikes()
        Assertions.assertTrue(chatsCommunication.likes.isEmpty())
    }

    @Test
    fun `test failure get all likes, internet is not available`() = runBlocking {
        Mockito.`when`(likesRepository.getAllLikes()).thenReturn(
            Resource.Error(emptyList(), NetworkException(INTERNET_IS_UNAVAILABLE))
        )
        chatsViewModel.getAllLikes()
        val result = chatsCommunication.motionToastError.first() == INTERNET_IS_UNAVAILABLE
        Assertions.assertTrue(result)
    }
}