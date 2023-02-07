package com.main.chat.domain.usecases

import com.main.chat.data.storage.local.MessageCacheModel
import com.main.core.Resource
import com.main.core.exception.ExceptionMessages
import com.main.core.exception.NetworkException
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.mockito.Mockito
import org.mockito.kotlin.mock

class DeleteFirebaseMessagesUseCaseTest {

    private val manageFirebaseMessagesRepository = mock<ManageFirebaseMessagesRepository>()
    private val deleteFirebaseMessagesUseCase = DeleteFirebaseMessagesUseCase(manageFirebaseMessagesRepository)

    @Test
    fun `test successful delete message from firebase`() = runBlocking {
        Mockito.`when`(manageFirebaseMessagesRepository.deleteAllMessages()).thenReturn(
            Resource.Success(true)
        )
        val result = deleteFirebaseMessagesUseCase.execute()
        Assertions.assertTrue(result.data == true)
    }

    @Test
    fun `test failure delete message from firebase, internet is not available`() = runBlocking {
        Mockito.`when`(manageFirebaseMessagesRepository.deleteAllMessages()).thenReturn(
            Resource.Error(false, NetworkException(ExceptionMessages.INTERNET_IS_UNAVAILABLE))
        )
        val result = deleteFirebaseMessagesUseCase.execute()
        Assertions.assertTrue(result.exception?.message == ExceptionMessages.INTERNET_IS_UNAVAILABLE)
    }
}