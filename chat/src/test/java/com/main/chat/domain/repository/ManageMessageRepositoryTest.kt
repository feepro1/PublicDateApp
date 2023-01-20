package com.main.chat.domain.repository

import com.main.core.Resource
import com.main.core.exception.NetworkException
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.mockito.Mockito
import org.mockito.kotlin.mock

interface ManageMessageRepositoryTest {

    private val manageMessageRepository = mock<ManageMessageRepository>()

    @Test
    fun `test successful get messages`() {
        Mockito.`when`(manageMessageRepository.receiveMessagesFromFirebase()).thenReturn(
            Resource.Success(listOf(Message(text = "Hello World")))
        )
        val result = manageMessageRepository.receiveMessagesFromFirebase()
        Assertions.assertTrue(result.data?.isNotEmpty() == true)
    }

    @Test
    fun `test successful get messages, but list of messages is empty`() {
        Mockito.`when`(manageMessageRepository.receiveMessagesFromFirebase()).thenReturn(
            Resource.Success(emptyList())
        )
        val result = manageMessageRepository.receiveMessagesFromFirebase()
        Assertions.assertTrue(result.data?.isEmpty() == true)
    }

    @Test
    fun `test failure get messages, internet is not available`() {
        Mockito.`when`(manageMessageRepository.receiveMessagesFromFirebase()).thenReturn(
            Resource.Error(emptyList(), NetworkException(INTERNET_IS_UNAVAILABLE))
        )
        val result = getMessagesFromFirebaseUseCase.execute()
        Assertions.assertTrue(result.exception?.message == INTERNET_IS_UNAVAILABLE)
    }

    @Test
    fun `test successful send message`() {
        val message = Message(text = "HelloWorld!")
        Mockito.`when`(manageMessageRepository.sendMessage(message)).thenReturn(
            Resource.Success(true)
        )
        val result = manageMessageRepository.sendMessage(message)
        Assertions.assertTrue(result.data == true)
    }

    @Test
    fun `test failure send message, internet is not available`() {
        val message = Message(text = "HelloWorld!")
        Mockito.`when`(manageMessageRepository.sendMessage(message)).thenReturn(
            Resource.Error(false, NetworkException(INTERNET_IS_UNAVAILABLE))
        )
        val result = manageMessageRepository.sendMessage(message)
        Assertions.assertTrue(result.exception?.message == INTERNET_IS_UNAVAILABLE)
    }

    @Test
    fun `test failure send message, user was not found`() {
        val message = Message(text = "HelloWorld!")
        Mockito.`when`(manageMessageRepository.sendMessage(message)).thenReturn(
            Resource.Error(false, UserException(USER_WAS_NOT_FOUND))
        )
        val result = manageMessageRepository.sendMessage(message)
        Assertions.assertTrue(result.exception?.message == USER_WAS_NOT_FOUND)
    }
}