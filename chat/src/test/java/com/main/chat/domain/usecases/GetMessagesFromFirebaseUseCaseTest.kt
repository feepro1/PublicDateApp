package com.main.chat.domain.usecases

import com.main.core.Resource
import com.main.core.exception.NetworkException
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.mockito.Mockito
import org.mockito.kotlin.mock

class GetMessagesFromFirebaseUseCaseTest {

    private val manageMessageRepository = mock<ManageMessageRepository>()
    private val getMessagesFromFirebaseUseCase = GetMessagesFromFirebaseUseCase(manageMessageRepository)

    @Test
    fun `test successful get messages`() {
        Mockito.`when`(manageMessageRepository.receiveMessagesFromFirebase()).thenReturn(
            Resource.Success(listOf(Message(text = "Hello World")))
        )
        val result = getMessagesFromFirebaseUseCase.execute()
        Assertions.assertTrue(result.data?.isNotEmpty() == true)
    }

    @Test
    fun `test successful get messages, but list of messages is empty`() {
        Mockito.`when`(manageMessageRepository.receiveMessagesFromFirebase()).thenReturn(
            Resource.Success(emptyList())
        )
        val result = getMessagesFromFirebaseUseCase.execute()
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
}