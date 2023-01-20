package com.main.chat.domain.repository

import com.main.chat.data.entities.Message
import com.main.chat.data.exception.messages.ChatExceptionMessages.INTERNET_IS_UNAVAILABLE
import com.main.chat.data.exception.messages.ChatExceptionMessages.USER_WAS_NOT_FOUND
import com.main.core.Resource
import com.main.core.exception.NetworkException
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
            Resource.Success(listOf(Message(text = "Hello World")))
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
        val message = Message(text = "HelloWorld!")
        Mockito.`when`(manageMessageRepository.sendMessage(message)).thenReturn(
            Resource.Success(true)
        )
        val result = manageMessageRepository.sendMessage(message)
        Assertions.assertTrue(result.data == true)
    }

    @Test
    fun `test failure send message, internet is not available`() = runBlocking {
        val message = Message(text = "HelloWorld!")
        Mockito.`when`(manageMessageRepository.sendMessage(message)).thenReturn(
            Resource.Error(false, NetworkException(INTERNET_IS_UNAVAILABLE))
        )
        val result = manageMessageRepository.sendMessage(message)
        Assertions.assertTrue(result.exception?.message == INTERNET_IS_UNAVAILABLE)
    }

    @Test
    fun `test failure send message, user was not found`() = runBlocking {
        val message = Message(text = "HelloWorld!")
        Mockito.`when`(manageMessageRepository.sendMessage(message)).thenReturn(
            Resource.Error(false, UserException(USER_WAS_NOT_FOUND))
        )
        val result = manageMessageRepository.sendMessage(message)
        Assertions.assertTrue(result.exception?.message == USER_WAS_NOT_FOUND)
    }
}