package com.main.chat.domain.usecases

import com.main.chat.data.entities.Message
import com.main.chat.data.exception.messages.ChatExceptionMessages.INTERNET_IS_UNAVAILABLE
import com.main.chat.domain.firebase.ManageMessageRepository
import com.main.core.Resource
import com.main.core.exception.NetworkException
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.mockito.Mockito
import org.mockito.kotlin.mock

class GetMessagesUseCaseTest {

    private val manageMessageRepository = mock<ManageMessageRepository>()
    private val getMessagesUseCase = GetMessagesUseCase(manageMessageRepository)

    @Test
    fun `test successful get messages`() = runBlocking {
        Mockito.`when`(manageMessageRepository.receiveMessages()).thenReturn(
            Resource.Success(listOf(Message(text = "Hello World")))
        )
        val result = getMessagesUseCase.execute()
        Assertions.assertTrue(result.data?.isNotEmpty() == true)
    }

    @Test
    fun `test successful get messages, but list of messages is empty`() = runBlocking {
        Mockito.`when`(manageMessageRepository.receiveMessages()).thenReturn(
            Resource.Success(emptyList())
        )
        val result = getMessagesUseCase.execute()
        Assertions.assertTrue(result.data?.isEmpty() == true)
    }

    @Test
    fun `test failure get messages, internet is not available`() = runBlocking {
        Mockito.`when`(manageMessageRepository.receiveMessages()).thenReturn(
            Resource.Error(emptyList(), NetworkException(INTERNET_IS_UNAVAILABLE))
        )
        val result = getMessagesUseCase.execute()
        Assertions.assertTrue(result.exception?.message == INTERNET_IS_UNAVAILABLE)
    }
}