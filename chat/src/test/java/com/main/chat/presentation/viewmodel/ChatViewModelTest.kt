package com.main.chat.presentation.viewmodel

import com.main.chat.BaseChatTest
import com.main.chat.data.exception.messages.ChatExceptionMessages.INTERNET_IS_UNAVAILABLE
import com.main.chat.data.exception.messages.ChatExceptionMessages.MESSAGE_IS_EMPTY
import com.main.chat.data.exception.messages.ChatExceptionMessages.MESSAGE_WAS_NOT_FOUND
import com.main.chat.data.exception.messages.ChatExceptionMessages.RECEIVER_UID_IS_EMPTY
import com.main.chat.data.exception.messages.ChatExceptionMessages.SENDER_UID_IS_EMPTY
import com.main.chat.data.exception.messages.ChatExceptionMessages.USER_WAS_NOT_FOUND
import com.main.chat.data.storage.local.MessageCacheModel
import com.main.chat.domain.firebase.ManageMessageRepository
import com.main.chat.domain.navigation.ChatNavigation
import com.main.chat.domain.usecases.DeleteMessageUseCase
import com.main.chat.domain.usecases.GetMessagesUseCase
import com.main.chat.domain.usecases.SendMessageUseCase
import com.main.core.Resource
import com.main.core.exception.MessageException
import com.main.core.exception.NetworkException
import com.main.core.exception.UidException
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
    private val deleteMessageUseCase = DeleteMessageUseCase(manageMessageRepository)
    private val chatViewModel = ChatViewModel(
        getMessagesUseCase = getMessagesUseCase,
        sendMessageUseCase = sendMessageUseCase,
        deleteMessageUseCase = deleteMessageUseCase,
        chatCommunication = chatCommunication,
        chatNavigation = ChatNavigation.Base(),
        dispatchers = TestDispatchersList()
    )

    @Test
    fun `test successful get messages`() = runBlocking {
        Mockito.`when`(manageMessageRepository.receiveMessages()).thenReturn(
            Resource.Success(listOf(MessageCacheModel(message = "Hello World", receiverUid = "", senderUid = "")))
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
        val message = MessageCacheModel(message = "HelloWorld!", senderUid = "", receiverUid = "")
        Mockito.`when`(manageMessageRepository.sendMessage(message)).thenReturn(
            Resource.Success(true)
        )
        chatViewModel.sendMessage(message)
        Assertions.assertTrue(chatCommunication.motionToastError.isEmpty())
    }

    @Test
    fun `test failure send message, internet is not available`() = runBlocking {
        val message = MessageCacheModel(message = "HelloWorld!", senderUid = "", receiverUid = "")
        Mockito.`when`(manageMessageRepository.sendMessage(message)).thenReturn(
            Resource.Error(false, NetworkException(INTERNET_IS_UNAVAILABLE))
        )
        chatViewModel.sendMessage(message)
        Assertions.assertTrue(chatCommunication.motionToastError.first() == INTERNET_IS_UNAVAILABLE)
    }

    @Test
    fun `test failure send message, user was not found`() = runBlocking {
        val message = MessageCacheModel(message = "HelloWorld!", senderUid = "", receiverUid = "")
        Mockito.`when`(manageMessageRepository.sendMessage(message)).thenReturn(
            Resource.Error(false, UserException(USER_WAS_NOT_FOUND))
        )
        chatViewModel.sendMessage(message)
        Assertions.assertTrue(chatCommunication.motionToastError.first() == USER_WAS_NOT_FOUND)
    }

    @Test
    fun `test successful delete message`() = runBlocking {
        val message = MessageCacheModel(message = "HelloWorld!", senderUid = "1", receiverUid = "2")
        Mockito.`when`(manageMessageRepository.deleteMessage(message)).thenReturn(
            Resource.Success(true)
        )
        chatViewModel.deleteMessage(message)
        Assertions.assertTrue(chatCommunication.motionToastError.isEmpty())
    }

    @Test
    fun `test failure delete message, internet is not available`() = runBlocking {
        val message = MessageCacheModel(message = "HelloWorld!", senderUid = "1", receiverUid = "2")
        Mockito.`when`(manageMessageRepository.deleteMessage(message)).thenReturn(
            Resource.Error(false, NetworkException(INTERNET_IS_UNAVAILABLE))
        )
        chatViewModel.deleteMessage(message)
        Assertions.assertTrue(chatCommunication.motionToastError.first() == INTERNET_IS_UNAVAILABLE)
    }

    @Test
    fun `test failure delete message, message was not found`() = runBlocking {
        val message = MessageCacheModel(message = "HelloWorld!", senderUid = "1", receiverUid = "2")
        Mockito.`when`(manageMessageRepository.deleteMessage(message)).thenReturn(
            Resource.Error(false, MessageException(MESSAGE_WAS_NOT_FOUND))
        )
        chatViewModel.deleteMessage(message)
        Assertions.assertTrue(chatCommunication.motionToastError.first() == MESSAGE_WAS_NOT_FOUND)
    }

    @Test
    fun `test failure delete message, message is empty`() = runBlocking {
        val message = MessageCacheModel(message = "", senderUid = "1", receiverUid = "2")
        Mockito.`when`(manageMessageRepository.deleteMessage(message)).thenReturn(
            Resource.Error(false, MessageException(MESSAGE_IS_EMPTY))
        )
        chatViewModel.deleteMessage(message)
        Assertions.assertTrue(chatCommunication.motionToastError.first() == MESSAGE_IS_EMPTY)
    }

    @Test
    fun `test failure delete message, senderUid is empty`() = runBlocking {
        val message = MessageCacheModel(message = "Hello World", senderUid = "", receiverUid = "2")
        Mockito.`when`(manageMessageRepository.deleteMessage(message)).thenReturn(
            Resource.Error(false, UidException(SENDER_UID_IS_EMPTY))
        )
        chatViewModel.deleteMessage(message)
        Assertions.assertTrue(chatCommunication.motionToastError.first() == SENDER_UID_IS_EMPTY)
    }

    @Test
    fun `test failure delete message, receiverUid is empty`() = runBlocking {
        val message = MessageCacheModel(message = "Hello World", senderUid = "1", receiverUid = "")
        Mockito.`when`(manageMessageRepository.deleteMessage(message)).thenReturn(
            Resource.Error(false, UidException(RECEIVER_UID_IS_EMPTY))
        )
        chatViewModel.deleteMessage(message)
        Assertions.assertTrue(chatCommunication.motionToastError.first() == RECEIVER_UID_IS_EMPTY)
    }
}