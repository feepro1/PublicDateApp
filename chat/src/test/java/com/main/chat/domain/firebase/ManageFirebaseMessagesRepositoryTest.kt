package com.main.chat.domain.firebase

import com.main.core.Resource
import com.main.core.exception.ExceptionMessages
import com.main.core.exception.ExceptionMessages.INTERNET_IS_UNAVAILABLE
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
        Mockito.`when`(manageFirebaseMessagesRepository.deleteAllMessages("some_uid")).thenReturn(
            Resource.Success(true)
        )
        val result = manageFirebaseMessagesRepository.deleteAllMessages("some_uid")
        Assertions.assertTrue(result.data == true)
    }

    @Test
    fun `test failure delete message from firebase, internet is not available`() = runBlocking {
        Mockito.`when`(manageFirebaseMessagesRepository.deleteAllMessages("some_uid")).thenReturn(
            Resource.Error(false, NetworkException(INTERNET_IS_UNAVAILABLE))
        )
        val result = manageFirebaseMessagesRepository.deleteAllMessages("some_uid")
        Assertions.assertTrue(result.exception?.message == INTERNET_IS_UNAVAILABLE)
    }
}