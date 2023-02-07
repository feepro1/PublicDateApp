package com.main.chat.domain.firebase

import com.main.chat.data.storage.local.MessageCacheModel
import com.main.core.Resource
import com.main.core.exception.ExceptionMessages
import com.main.core.exception.NetworkException
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.mockito.Mockito
import org.mockito.kotlin.mock

class ManageFirebaseMessagesRepositoryTest {

    private val manageFirebaseMessagesRepository = mock<ManageFirebaseMessagesRepository>()

    @Test
    fun `test successful delete message from firebase`() = runBlocking {
        Mockito.`when`(manageFirebaseMessagesRepository.deleteAllMessages()).thenReturn(
            Resource.Success(true)
        )
        val result = manageFirebaseMessagesRepository.deleteAllMessages()
        Assertions.assertTrue(result.data == true)
    }

    @Test
    fun `test failure delete message from firebase, internet is not available`() = runBlocking {
        Mockito.`when`(manageFirebaseMessagesRepository.deleteAllMessages()).thenReturn(
            Resource.Error(false, NetworkException(ExceptionMessages.INTERNET_IS_UNAVAILABLE))
        )
        val result = manageFirebaseMessagesRepository.deleteAllMessage()
        Assertions.assertTrue(result.exception?.message == ExceptionMessages.INTERNET_IS_UNAVAILABLE)
    }
}