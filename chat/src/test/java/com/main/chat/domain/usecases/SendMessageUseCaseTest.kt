package com.main.chat.domain.usecases

import com.main.core.Resource
import com.main.core.exception.NetworkException
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.mockito.Mockito
import org.mockito.kotlin.mock

class SendMessageUseCaseTest {

    private val manageMessageRepository = mock<ManageMessageRepository>()
    private val sendMessageUseCase = SendMessageUseCase(manageMessageRepository)

    @Test
    fun `test successful send message`() {
        val message = Message(text = "HelloWorld!")
        Mockito.`when`(manageMessageRepository.sendMessage(message)).thenReturn(
            Resource.Success(true)
        )
        val result = sendMessageUseCase.execute(message)
        Assertions.assertTrue(result.data == true)
    }

    @Test
    fun `test failure send message, internet is not available`() {
        val message = Message(text = "HelloWorld!")
        Mockito.`when`(manageMessageRepository.sendMessage(message)).thenReturn(
            Resource.Error(false, NetworkException(INTERNET_IS_UNAVAILABLE))
        )
        val result = sendMessageUseCase.execute(message)
        Assertions.assertTrue(result.exception?.message == INTERNET_IS_UNAVAILABLE)
    }

    @Test
    fun `test failure send message, user was not found`() {
        val message = Message(text = "HelloWorld!")
        Mockito.`when`(manageMessageRepository.sendMessage(message)).thenReturn(
            Resource.Error(false, UserException(USER_WAS_NOT_FOUND))
        )
        val result = sendMessageUseCase.execute(message)
        Assertions.assertTrue(result.exception?.message == USER_WAS_NOT_FOUND)
    }
}