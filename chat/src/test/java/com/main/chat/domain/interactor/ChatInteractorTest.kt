package com.main.chat.domain.interactor

import com.main.chat.data.storage.local.MessageCacheModel
import com.main.chat.domain.firebase.ManageMessageRepository
import com.main.chat.domain.usecases.DeleteMessageUseCase
import com.main.chat.domain.usecases.GetMessagesUseCase
import com.main.chat.domain.usecases.SendMessageUseCase
import com.main.core.Resource
import com.main.core.exception.*
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.mockito.Mockito
import org.mockito.kotlin.mock

class ChatInteractorTest {

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

    @Test
    fun `test successful send message`() = runBlocking {
        val message = MessageCacheModel(message = "HelloWorld!", senderUid = "", receiverUid = "")
        Mockito.`when`(sendMessageUseCase.execute(message)).thenReturn(
            Resource.Success(true)
        )
        val result = chatInteractor.sendMessage(message)
        Assertions.assertTrue(result.data == true)
    }

    @Test
    fun `test failure send message, internet is not available`() = runBlocking {
        val message = MessageCacheModel(message = "HelloWorld!", senderUid = "", receiverUid = "")
        Mockito.`when`(sendMessageUseCase.execute(message)).thenReturn(
            Resource.Error(false, NetworkException(ExceptionMessages.INTERNET_IS_UNAVAILABLE))
        )
        val result = chatInteractor.sendMessage(message)
        Assertions.assertTrue(result.exception?.message == ExceptionMessages.INTERNET_IS_UNAVAILABLE)
    }

    @Test
    fun `test failure send message, user was not found`() = runBlocking {
        val message = MessageCacheModel(message = "HelloWorld!", senderUid = "", receiverUid = "")
        Mockito.`when`(sendMessageUseCase.execute(message)).thenReturn(
            Resource.Error(false, UserException(ExceptionMessages.USER_WAS_NOT_FOUND))
        )
        val result = chatInteractor.sendMessage(message)
        Assertions.assertTrue(result.exception?.message == ExceptionMessages.USER_WAS_NOT_FOUND)
    }

    @Test
    fun `test successful get messages`() = runBlocking {
        Mockito.`when`(getMessagesUseCase.execute()).thenReturn(
            Resource.Success(listOf(MessageCacheModel(message = "Hello World", receiverUid = "", senderUid = "")))
        )
        val result = chatInteractor.getMessage()
        Assertions.assertTrue(result.data?.isNotEmpty() == true)
    }

    @Test
    fun `test successful get messages, but list of messages is empty`() = runBlocking {
        Mockito.`when`(getMessagesUseCase.execute()).thenReturn(
            Resource.Success(emptyList())
        )
        val result = chatInteractor.getMessage()
        Assertions.assertTrue(result.data?.isEmpty() == true)
    }

    @Test
    fun `test failure get messages, internet is not available`() = runBlocking {
        Mockito.`when`(getMessagesUseCase.execute()).thenReturn(
            Resource.Error(emptyList(), NetworkException(ExceptionMessages.INTERNET_IS_UNAVAILABLE))
        )
        val result = chatInteractor.getMessage()
        Assertions.assertTrue(result.exception?.message == ExceptionMessages.INTERNET_IS_UNAVAILABLE)
    }

    @Test
    fun `test successful delete message`() = runBlocking {
        val message = MessageCacheModel(message = "HelloWorld!", senderUid = "1", receiverUid = "2")
        Mockito.`when`(deleteMessageUseCase.execute(message)).thenReturn(
            Resource.Success(true)
        )
        val result = chatInteractor.deleteMessage(message)
        Assertions.assertTrue(result.data == true)
    }

    @Test
    fun `test failure delete message, internet is not available`() = runBlocking {
        val message = MessageCacheModel(message = "HelloWorld!", senderUid = "1", receiverUid = "2")
        Mockito.`when`(deleteMessageUseCase.execute(message)).thenReturn(
            Resource.Error(false, NetworkException(ExceptionMessages.INTERNET_IS_UNAVAILABLE))
        )
        val result = chatInteractor.deleteMessage(message)
        Assertions.assertTrue(result.exception?.message == ExceptionMessages.INTERNET_IS_UNAVAILABLE)
    }

    @Test
    fun `test failure delete message, message was not found`() = runBlocking {
        val message = MessageCacheModel(message = "HelloWorld!", senderUid = "1", receiverUid = "2")
        Mockito.`when`(deleteMessageUseCase.execute(message)).thenReturn(
            Resource.Error(false, MessageException(ExceptionMessages.MESSAGE_WAS_NOT_FOUND))
        )
        val result = chatInteractor.deleteMessage(message)
        Assertions.assertTrue(result.exception?.message == ExceptionMessages.MESSAGE_WAS_NOT_FOUND)
    }

    @Test
    fun `test failure delete message, message is empty`() = runBlocking {
        val message = MessageCacheModel(message = "", senderUid = "1", receiverUid = "2")
        Mockito.`when`(deleteMessageUseCase.execute(message)).thenReturn(
            Resource.Error(false, MessageException(ExceptionMessages.MESSAGE_IS_EMPTY))
        )
        val result = chatInteractor.deleteMessage(message)
        Assertions.assertTrue(result.exception?.message == ExceptionMessages.MESSAGE_IS_EMPTY)
    }

    @Test
    fun `test failure delete message, senderUid is empty`() = runBlocking {
        val message = MessageCacheModel(message = "Hello World", senderUid = "", receiverUid = "2")
        Mockito.`when`(deleteMessageUseCase.execute(message)).thenReturn(
            Resource.Error(false, UidException(ExceptionMessages.SENDER_UID_IS_EMPTY))
        )
        val result = chatInteractor.deleteMessage(message)
        Assertions.assertTrue(result.exception?.message == ExceptionMessages.SENDER_UID_IS_EMPTY)
    }

    @Test
    fun `test failure delete message, receiverUid is empty`() = runBlocking {
        val message = MessageCacheModel(message = "Hello World", senderUid = "1", receiverUid = "")
        Mockito.`when`(deleteMessageUseCase.execute(message)).thenReturn(
            Resource.Error(false, UidException(ExceptionMessages.RECEIVER_UID_IS_EMPTY))
        )
        val result = chatInteractor.deleteMessage(message)
        Assertions.assertTrue(result.exception?.message == ExceptionMessages.RECEIVER_UID_IS_EMPTY)
    }

    @Test
    fun `test successful delete message from firebase`() = runBlocking {
        Mockito.`when`(deleteFirebaseMessagesUseCase.execute()).thenReturn(
            Resource.Success(true)
        )
        val result = chatInteractor.deleteAllMessagesFromFirebase()
        Assertions.assertTrue(result.data == true)
    }

    @Test
    fun `test failure delete message from firebase, internet is not available`() = runBlocking {
        Mockito.`when`(deleteFirebaseMessagesUseCase.execute()).thenReturn(
            Resource.Error(false, NetworkException(ExceptionMessages.INTERNET_IS_UNAVAILABLE))
        )
        val result = chatInteractor.deleteAllMessagesFromFirebase()
        Assertions.assertTrue(result.exception?.message == ExceptionMessages.INTERNET_IS_UNAVAILABLE)
    }
}