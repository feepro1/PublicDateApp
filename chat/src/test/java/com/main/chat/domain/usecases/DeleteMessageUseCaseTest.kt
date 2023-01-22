package com.main.chat.domain.usecases

import com.main.chat.data.exception.messages.ChatExceptionMessages.INTERNET_IS_UNAVAILABLE
import com.main.chat.data.storage.local.MessageCacheModel
import com.main.chat.domain.firebase.ManageMessageRepository
import com.main.core.Resource
import com.main.core.exception.NetworkException
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.mockito.Mockito
import org.mockito.kotlin.mock

class DeleteMessageUseCaseTest {

    private val manageMessageRepository = mock<ManageMessageRepository>()
    private val deleteMessageUseCase = DeleteMessageUseCase(manageMessageRepository)

    @Test
    fun `test successful delete message`() = runBlocking {
        val message = MessageCacheModel(message = "HelloWorld!", senderUid = "1", receiverUid = "2")
        Mockito.`when`(manageMessageRepository.deleteMessage(message)).thenReturn(
            Resource.Success(true)
        )
        val result = deleteMessageUseCase.execute(message)
        Assertions.assertTrue(result.data == true)
    }

    @Test
    fun `test failure delete message, internet is not available`() = runBlocking {
        val message = MessageCacheModel(message = "HelloWorld!", senderUid = "1", receiverUid = "2")
        Mockito.`when`(manageMessageRepository.deleteMessage(message)).thenReturn(
            Resource.Error(false, NetworkException(INTERNET_IS_UNAVAILABLE))
        )
        val result = deleteMessageUseCase.execute(message)
        Assertions.assertTrue(result.exception?.message == INTERNET_IS_UNAVAILABLE)
    }

    @Test
    fun `test failure delete message, message was not found`() = runBlocking {
        val message = MessageCacheModel(message = "HelloWorld!", senderUid = "1", receiverUid = "2")
        Mockito.`when`(manageMessageRepository.deleteMessage(message)).thenReturn(
            Resource.Error(false, MessageException(MESSAGE_WAS_NOT_FOUND))
        )
        val result = deleteMessageUseCase.execute(message)
        Assertions.assertTrue(result.exception?.message == MESSAGE_WAS_NOT_FOUND)
    }

    @Test
    fun `test failure delete message, message is empty`() = runBlocking {
        val message = MessageCacheModel(message = "", senderUid = "1", receiverUid = "2")
        Mockito.`when`(manageMessageRepository.deleteMessage(message)).thenReturn(
            Resource.Error(false, MessageException(MESSAGE_IS_EMPTY))
        )
        val result = deleteMessageUseCase.execute(message)
        Assertions.assertTrue(result.exception?.message == MESSAGE_IS_EMPTY)
    }

    @Test
    fun `test failure delete message, senderUid is empty`() = runBlocking {
        val message = MessageCacheModel(message = "Hello World", senderUid = "", receiverUid = "2")
        Mockito.`when`(manageMessageRepository.deleteMessage(message)).thenReturn(
            Resource.Error(false, UidException(SENDER_UID_IS_EMPTY))
        )
        val result = deleteMessageUseCase.execute(message)
        Assertions.assertTrue(result.exception?.message == SENDER_UID_IS_EMPTY)
    }

    @Test
    fun `test failure delete message, receiverUid is empty`() = runBlocking {
        val message = MessageCacheModel(message = "Hello World", senderUid = "1", receiverUid = "")
        Mockito.`when`(manageMessageRepository.deleteMessage(message)).thenReturn(
            Resource.Error(false, UidException(RECEIVER_UID_IS_EMPTY))
        )
        val result = deleteMessageUseCase.execute(message)
        Assertions.assertTrue(result.exception?.message == RECEIVER_UID_IS_EMPTY)
    }
}