package com.main.chat.presentation.viewmodel

import com.main.core.Resource
import com.main.core.exception.NetworkException
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.kotlin.mock

class ChatViewModelTest {

    private val chatCommunication = TesChatCommunication()
    private val manageMessageRepository = mock<ManageMessageRepository>()
    private val sendMessageUseCase = SendMessageUseCase(manageMessageRepository)
    private val getMessagesFromFirebaseUseCase = GetMessagesFromFirebaseUseCase(manageMessageRepository)
    private val chatViewModel = ChatViewModel(
        getMessagesFromFirebaseUseCase = getMessagesFromFirebaseUseCase,
        sendMessageUseCase = sendMessageUseCase,
        chatCommunication = chatCommunication,
        chatNavigation = ChatNavigation.Base(),
        dispatchers = TestDispatchers()
    )

    @Test
    fun `test successful get messages`() {
        Mockito.`when`(manageMessageRepository.receiveMessagesFromFirebase()).thenReturn(
            Resource.Success(listOf(Message(text = "Hello World")))
        )
        chatViewModel.receiveMessagesFromFirebase()
        Assertions.assertTrue(chatCommunication.messages.isNotEmpty())
    }

    @Test
    fun `test successful get messages, but list of messages is empty`() {
        Mockito.`when`(manageMessageRepository.receiveMessagesFromFirebase()).thenReturn(
            Resource.Success(emptyList())
        )
        chatViewModel.receiveMessagesFromFirebase()
        Assertions.assertTrue(chatCommunication.messages.isNotEmpty())
    }

    @Test
    fun `test failure get messages, internet is not available`() {
        Mockito.`when`(manageMessageRepository.receiveMessagesFromFirebase()).thenReturn(
            Resource.Success(emptyList())
        )
        chatViewModel.receiveMessagesFromFirebase()
        Assertions.assertTrue(chatCommunication.messages.isNotEmpty())
    }

    @Test
    fun `test successful send message`() {
        val message = Message(text = "Hello World")
        Mockito.`when`(manageMessageRepository.sendMessages(message)).thenReturn(
            Resource.Success(true)
        )
        chatViewModel.sendMessage(message)
        Assertions.assertTrue(chatCommunication.motionToastError.first().isEmpty())
    }

    @Test
    fun `test failure send message, internet is not available`() {
        val message = Message(text = "HelloWorld!")
        Mockito.`when`(manageMessageRepository.sendMessage(message)).thenReturn(
            Resource.Error(false, NetworkException(INTERNET_IS_UNAVAILABLE))
        )
        chatViewModel.sendMessage(message)
        Assertions.assertTrue(chatCommunication.motionToastError.first() == INTERNET_IS_UNAVAILABLE)
    }

    @Test
    fun `test failure send message, user was not found`() {
        val message = Message(text = "HelloWorld!")
        Mockito.`when`(manageMessageRepository.sendMessage(message)).thenReturn(
            Resource.Error(false, UserException(USER_WAS_NOT_FOUND))
        )
        chatViewModel.sendMessage(message)
        Assertions.assertTrue(chatCommunication.motionToastError.first() == USER_WAS_NOT_FOUND)
    }
}