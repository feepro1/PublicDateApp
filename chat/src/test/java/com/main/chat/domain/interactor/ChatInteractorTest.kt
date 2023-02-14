package com.main.chat.domain.interactor

import com.main.chat.data.storage.local.MessageCacheModel
import com.main.chat.domain.firebase.ManageFirebaseMessagesRepository
import com.main.chat.domain.firebase.ManageMessageRepository
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
            Resource.Error(false, NetworkException(INTERNET_IS_UNAVAILABLE))
        )
        val result = chatInteractor.sendMessage(message)
        Assertions.assertTrue(result.exception?.message == INTERNET_IS_UNAVAILABLE)
    }

    @Test
    fun `test failure send message, user was not found`() = runBlocking {
        val message = MessageCacheModel(message = "HelloWorld!", senderUid = "", receiverUid = "")
        Mockito.`when`(sendMessageUseCase.execute(message)).thenReturn(
            Resource.Error(false, UserException(USER_WAS_NOT_FOUND))
        )
        val result = chatInteractor.sendMessage(message)
        Assertions.assertTrue(result.exception?.message == USER_WAS_NOT_FOUND)
    }

    @Test
    fun `test successful get messages`() = runBlocking {
        Mockito.`when`(getMessagesUseCase.execute()).thenReturn(
            Resource.Success(listOf(MessageCacheModel(message = "Hello World", receiverUid = "", senderUid = "")))
        )
        val result = chatInteractor.getMessages()
        Assertions.assertTrue(result.data?.isNotEmpty() == true)
    }

    @Test
    fun `test successful get messages, but list of messages is empty`() = runBlocking {
        Mockito.`when`(getMessagesUseCase.execute()).thenReturn(
            Resource.Success(emptyList())
        )
        val result = chatInteractor.getMessages()
        Assertions.assertTrue(result.data?.isEmpty() == true)
    }

    @Test
    fun `test failure get messages, internet is not available`() = runBlocking {
        Mockito.`when`(getMessagesUseCase.execute()).thenReturn(
            Resource.Error(emptyList(), NetworkException(INTERNET_IS_UNAVAILABLE))
        )
        val result = chatInteractor.getMessages()
        Assertions.assertTrue(result.exception?.message == INTERNET_IS_UNAVAILABLE)
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
            Resource.Error(false, NetworkException(INTERNET_IS_UNAVAILABLE))
        )
        val result = chatInteractor.deleteMessage(message)
        Assertions.assertTrue(result.exception?.message == INTERNET_IS_UNAVAILABLE)
    }

    @Test
    fun `test failure delete message, message was not found`() = runBlocking {
        val message = MessageCacheModel(message = "HelloWorld!", senderUid = "1", receiverUid = "2")
        Mockito.`when`(deleteMessageUseCase.execute(message)).thenReturn(
            Resource.Error(false, MessageException(MESSAGE_WAS_NOT_FOUND))
        )
        val result = chatInteractor.deleteMessage(message)
        Assertions.assertTrue(result.exception?.message == MESSAGE_WAS_NOT_FOUND)
    }

    @Test
    fun `test failure delete message, message is empty`() = runBlocking {
        val message = MessageCacheModel(message = "", senderUid = "1", receiverUid = "2")
        Mockito.`when`(deleteMessageUseCase.execute(message)).thenReturn(
            Resource.Error(false, MessageException(MESSAGE_IS_EMPTY))
        )
        val result = chatInteractor.deleteMessage(message)
        Assertions.assertTrue(result.exception?.message == MESSAGE_IS_EMPTY)
    }

    @Test
    fun `test failure delete message, senderUid is empty`() = runBlocking {
        val message = MessageCacheModel(message = "Hello World", senderUid = "", receiverUid = "2")
        Mockito.`when`(deleteMessageUseCase.execute(message)).thenReturn(
            Resource.Error(false, UidException(SENDER_UID_IS_EMPTY))
        )
        val result = chatInteractor.deleteMessage(message)
        Assertions.assertTrue(result.exception?.message == SENDER_UID_IS_EMPTY)
    }

    @Test
    fun `test failure delete message, receiverUid is empty`() = runBlocking {
        val message = MessageCacheModel(message = "Hello World", senderUid = "1", receiverUid = "")
        Mockito.`when`(deleteMessageUseCase.execute(message)).thenReturn(
            Resource.Error(false, UidException(RECEIVER_UID_IS_EMPTY))
        )
        val result = chatInteractor.deleteMessage(message)
        Assertions.assertTrue(result.exception?.message == RECEIVER_UID_IS_EMPTY)
    }

    @Test
    fun `test successful delete message from firebase`() = runBlocking {
        Mockito.`when`(deleteFirebaseMessagesUseCase.execute("some_uid")).thenReturn(
            Resource.Success(true)
        )
        val result = chatInteractor.deleteAllMessagesFromFirebase("some_uid")
        Assertions.assertTrue(result.data == true)
    }

    @Test
    fun `test failure delete message from firebase, internet is not available`() = runBlocking {
        Mockito.`when`(deleteFirebaseMessagesUseCase.execute("some_uid")).thenReturn(
            Resource.Error(false, NetworkException(INTERNET_IS_UNAVAILABLE))
        )
        val result = chatInteractor.deleteAllMessagesFromFirebase("some_uid")
        Assertions.assertTrue(result.exception?.message == INTERNET_IS_UNAVAILABLE)
    }
}