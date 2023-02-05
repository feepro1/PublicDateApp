package com.main.chat.domain.firebase

import com.main.chat.data.storage.local.MessageCacheModel
import com.main.core.Resource
import com.main.core.exception.ExceptionMessages.INTERNET_IS_UNAVAILABLE
import com.main.core.exception.ExceptionMessages.MESSAGE_IS_EMPTY
import com.main.core.exception.ExceptionMessages.MESSAGE_WAS_NOT_FOUND
import com.main.core.exception.ExceptionMessages.RECEIVER_UID_IS_EMPTY
import com.main.core.exception.ExceptionMessages.SENDER_UID_IS_EMPTY
import com.main.core.exception.ExceptionMessages.USER_WAS_NOT_FOUND
import com.main.core.exception.MessageException
import com.main.core.exception.NetworkException
import com.main.core.exception.UidException
import com.main.core.exception.UserException
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.mockito.Mockito
import org.mockito.kotlin.mock

class ManageMessageRepositoryTest {

    private val manageMessageRepository = mock<ManageMessageRepository>()

    @Test
    fun `test successful get messages`() = runBlocking {
        Mockito.`when`(manageMessageRepository.receiveMessages()).thenReturn(
            Resource.Success(listOf(MessageCacheModel(message = "Hello World", receiverUid = "", senderUid = "")))
        )
        val result = manageMessageRepository.receiveMessages()
        Assertions.assertTrue(result.data?.isNotEmpty() == true)
    }

    @Test
    fun `test successful get messages, but list of messages is empty`() = runBlocking {
        Mockito.`when`(manageMessageRepository.receiveMessages()).thenReturn(
            Resource.Success(emptyList())
        )
        val result = manageMessageRepository.receiveMessages()
        Assertions.assertTrue(result.data?.isEmpty() == true)
    }

    @Test
    fun `test failure get messages, internet is not available`() = runBlocking {
        Mockito.`when`(manageMessageRepository.receiveMessages()).thenReturn(
            Resource.Error(emptyList(), NetworkException(INTERNET_IS_UNAVAILABLE))
        )
        val result = manageMessageRepository.receiveMessages()
        Assertions.assertTrue(result.exception?.message == INTERNET_IS_UNAVAILABLE)
    }

    @Test
    fun `test successful send message`() = runBlocking {
        val message = MessageCacheModel(message = "HelloWorld!", senderUid = "", receiverUid = "")
        Mockito.`when`(manageMessageRepository.sendMessage(message)).thenReturn(
            Resource.Success(true)
        )
        val result = manageMessageRepository.sendMessage(message)
        Assertions.assertTrue(result.data == true)
    }

    @Test
    fun `test failure send message, internet is not available`() = runBlocking {
        val message = MessageCacheModel(message = "HelloWorld!", senderUid = "", receiverUid = "")
        Mockito.`when`(manageMessageRepository.sendMessage(message)).thenReturn(
            Resource.Error(false, NetworkException(INTERNET_IS_UNAVAILABLE))
        )
        val result = manageMessageRepository.sendMessage(message)
        Assertions.assertTrue(result.exception?.message == INTERNET_IS_UNAVAILABLE)
    }

    @Test
    fun `test failure send message, user was not found`() = runBlocking {
        val message = MessageCacheModel(message = "HelloWorld!", senderUid = "", receiverUid = "")
        Mockito.`when`(manageMessageRepository.sendMessage(message)).thenReturn(
            Resource.Error(false, UserException(USER_WAS_NOT_FOUND))
        )
        val result = manageMessageRepository.sendMessage(message)
        Assertions.assertTrue(result.exception?.message == USER_WAS_NOT_FOUND)
    }

    @Test
    fun `test successful delete message`() = runBlocking {
        val message = MessageCacheModel(message = "HelloWorld!", senderUid = "1", receiverUid = "2")
        Mockito.`when`(manageMessageRepository.deleteMessage(message)).thenReturn(
            Resource.Success(true)
        )
        val result = manageMessageRepository.deleteMessage(message)
        Assertions.assertTrue(result.data == true)
    }

    @Test
    fun `test failure delete message, internet is not available`() = runBlocking {
        val message = MessageCacheModel(message = "HelloWorld!", senderUid = "1", receiverUid = "2")
        Mockito.`when`(manageMessageRepository.deleteMessage(message)).thenReturn(
            Resource.Error(false, NetworkException(INTERNET_IS_UNAVAILABLE))
        )
        val result = manageMessageRepository.deleteMessage(message)
        Assertions.assertTrue(result.exception?.message == INTERNET_IS_UNAVAILABLE)
    }

    @Test
    fun `test failure delete message, message was not found`() = runBlocking {
        val message = MessageCacheModel(message = "HelloWorld!", senderUid = "1", receiverUid = "2")
        Mockito.`when`(manageMessageRepository.deleteMessage(message)).thenReturn(
            Resource.Error(false, MessageException(MESSAGE_WAS_NOT_FOUND))
        )
        val result = manageMessageRepository.deleteMessage(message)
        Assertions.assertTrue(result.exception?.message == MESSAGE_WAS_NOT_FOUND)
    }

    @Test
    fun `test failure delete message, message is empty`() = runBlocking {
        val message = MessageCacheModel(message = "", senderUid = "1", receiverUid = "2")
        Mockito.`when`(manageMessageRepository.deleteMessage(message)).thenReturn(
            Resource.Error(false, MessageException(MESSAGE_IS_EMPTY))
        )
        val result = manageMessageRepository.deleteMessage(message)
        Assertions.assertTrue(result.exception?.message == MESSAGE_IS_EMPTY)
    }

    @Test
    fun `test failure delete message, senderUid is empty`() = runBlocking {
        val message = MessageCacheModel(message = "Hello World", senderUid = "", receiverUid = "2")
        Mockito.`when`(manageMessageRepository.deleteMessage(message)).thenReturn(
            Resource.Error(false, UidException(SENDER_UID_IS_EMPTY))
        )
        val result = manageMessageRepository.deleteMessage(message)
        Assertions.assertTrue(result.exception?.message == SENDER_UID_IS_EMPTY)
    }

    @Test
    fun `test failure delete message, receiverUid is empty`() = runBlocking {
        val message = MessageCacheModel(message = "Hello World", senderUid = "1", receiverUid = "")
        Mockito.`when`(manageMessageRepository.deleteMessage(message)).thenReturn(
            Resource.Error(false, UidException(RECEIVER_UID_IS_EMPTY))
        )
        val result = manageMessageRepository.deleteMessage(message)
        Assertions.assertTrue(result.exception?.message == RECEIVER_UID_IS_EMPTY)
    }
}