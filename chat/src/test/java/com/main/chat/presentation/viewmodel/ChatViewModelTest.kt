package com.main.chat.presentation.viewmodel

import com.main.chat.BaseChatTest
import com.main.chat.data.entities.Message
import com.main.chat.data.exception.messages.ChatExceptionMessages.INTERNET_IS_UNAVAILABLE
import com.main.chat.data.exception.messages.ChatExceptionMessages.USER_WAS_NOT_FOUND
import com.main.chat.domain.navigation.ChatNavigation
import com.main.chat.domain.repository.ManageMessageRepository
import com.main.chat.domain.usecases.GetMessagesUseCase
import com.main.chat.domain.usecases.SendMessageUseCase
import com.main.core.Resource
import com.main.core.exception.NetworkException
import com.main.core.exception.UserException
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.mockito.Mockito
import org.mockito.kotlin.mock

class ChatViewModelTest : BaseChatTest() {

    private val chatCommunication = TestChatCommunication()
    private val manageMessageRepository = mock<ManageMessageRepository>()
    private val sendMessageUseCase = SendMessageUseCase(manageMessageRepository)
    private val getMessagesUseCase = GetMessagesUseCase(manageMessageRepository)
    private val chatViewModel = ChatViewModel(
        getMessagesUseCase = getMessagesUseCase,
        sendMessageUseCase = sendMessageUseCase,
        chatCommunication = chatCommunication,
        chatNavigation = ChatNavigation.Base(),
        dispatchers = TestDispatchersList()
    )

    @Test
    fun `test successful get messages`() = runBlocking {
        Mockito.`when`(manageMessageRepository.receiveMessages()).thenReturn(
            Resource.Success(listOf(Message(text = "Hello World")))
        )
        chatViewModel.receiveMessages()
        Assertions.assertTrue(chatCommunication.messages.isNotEmpty())
    }

    @Test
    fun `test successful get messages, but list of messages is empty`() = runBlocking {
        Mockito.`when`(manageMessageRepository.receiveMessages()).thenReturn(
            Resource.Success(emptyList())
        )
        chatViewModel.receiveMessages()
        Assertions.assertTrue(chatCommunication.messages.isNotEmpty())
    }

    @Test
    fun `test failure get messages, internet is not available`() = runBlocking {
        Mockito.`when`(manageMessageRepository.receiveMessages()).thenReturn(
            Resource.Error(null, NetworkException(INTERNET_IS_UNAVAILABLE))
        )
        chatViewModel.receiveMessages()
        Assertions.assertTrue(chatCommunication.motionToastError.first() == INTERNET_IS_UNAVAILABLE)
    }

    @Test
    fun `test successful send message`() = runBlocking {
        val message = Message(text = "Hello World")
        Mockito.`when`(manageMessageRepository.sendMessage(message)).thenReturn(
            Resource.Success(true)
        )
        chatViewModel.sendMessage(message)
        Assertions.assertTrue(chatCommunication.motionToastError.isEmpty())
    }

    @Test
    fun `test failure send message, internet is not available`() = runBlocking {
        val message = Message(text = "HelloWorld!")
        Mockito.`when`(manageMessageRepository.sendMessage(message)).thenReturn(
            Resource.Error(false, NetworkException(INTERNET_IS_UNAVAILABLE))
        )
        chatViewModel.sendMessage(message)
        Assertions.assertTrue(chatCommunication.motionToastError.first() == INTERNET_IS_UNAVAILABLE)
    }

    @Test
    fun `test failure send message, user was not found`() = runBlocking {
        val message = Message(text = "HelloWorld!")
        Mockito.`when`(manageMessageRepository.sendMessage(message)).thenReturn(
            Resource.Error(false, UserException(USER_WAS_NOT_FOUND))
        )
        chatViewModel.sendMessage(message)
        Assertions.assertTrue(chatCommunication.motionToastError.first() == USER_WAS_NOT_FOUND)
    }
}