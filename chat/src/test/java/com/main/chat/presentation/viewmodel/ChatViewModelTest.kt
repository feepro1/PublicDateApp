package com.main.chat.presentation.viewmodel

import com.main.chat.BaseChatTest
import com.main.chat.data.storage.local.ChatCacheRepository
import com.main.chat.data.storage.local.ChatCacheRepositoryImpl
import com.main.chat.data.storage.local.ChatDao
import com.main.chat.data.storage.local.MessageCacheModel
import com.main.chat.domain.firebase.ManageFirebaseMessagesRepository
import com.main.chat.domain.firebase.ManageMessageRepository
import com.main.chat.domain.interactor.ChatInteractor
import com.main.chat.domain.navigation.ChatNavigation
import com.main.chat.domain.usecases.DeleteFirebaseMessagesUseCase
import com.main.chat.domain.usecases.DeleteMessageUseCase
import com.main.chat.domain.usecases.GetMessagesUseCase
import com.main.chat.domain.usecases.SendMessageUseCase
import com.main.core.Resource
import com.main.core.exception.*
import com.main.core.exception.ExceptionMessages.INTERNET_IS_UNAVAILABLE
import com.main.core.exception.ExceptionMessages.MESSAGE_IS_EMPTY
import com.main.core.exception.ExceptionMessages.MESSAGE_WAS_NOT_FOUND
import com.main.core.exception.ExceptionMessages.RECEIVER_UID_IS_EMPTY
import com.main.core.exception.ExceptionMessages.SENDER_UID_IS_EMPTY
import com.main.core.exception.ExceptionMessages.USER_WAS_NOT_FOUND
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.mockito.Mockito
import org.mockito.kotlin.mock

class ChatViewModelTest : BaseChatTest() {

    private val chatCommunication = TestChatCommunication()
    private val chatCacheRepository = mock<ChatCacheRepository>()
    private val manageMessageRepository = mock<ManageMessageRepository>()
    private val manageFirebaseMessagesRepository = mock<ManageFirebaseMessagesRepository>()
    private val sendMessageUseCase = SendMessageUseCase(manageMessageRepository)
    private val getMessagesUseCase = GetMessagesUseCase(manageMessageRepository)
    private val deleteMessageUseCase = DeleteMessageUseCase(manageMessageRepository)
    private val deleteFirebaseMessagesUseCase = DeleteFirebaseMessagesUseCase(manageFirebaseMessagesRepository)
    private val chatInteractor = ChatInteractor(
        sendMessageUseCase = sendMessageUseCase,
        getMessagesUseCase = getMessagesUseCase,
        deleteMessageUseCase = deleteMessageUseCase,
        deleteFirebaseMessagesUseCase = deleteFirebaseMessagesUseCase
    )
    private val chatViewModel = ChatViewModel(
        chatInteractor = chatInteractor,
        chatCommunication = chatCommunication,
        chatNavigation = ChatNavigation.Base(),
        dispatchers = TestDispatchersList(),
        chatCacheRepository = chatCacheRepository
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