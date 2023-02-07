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

class DeleteFirebaseMessageUseCaseTest {

    private val manageFirebaseMessageRepository = mock<ManageFirebaseMessageRepository>()
    private val deleteFirebaseMessageUseCase = DeleteFirebaseMessageUseCase(manageFirebaseMessageRepository)

    @Test
    fun `test successful delete message`() = runBlocking {
        val message = MessageCacheModel(message = "HelloWorld!", senderUid = "1", receiverUid = "2")
        Mockito.`when`(manageFirebaseMessageRepository.deleteMessage(message)).thenReturn(
            Resource.Success(true)
        )
        val result = deleteFirebaseMessageUseCase.execute(message)
        Assertions.assertTrue(result.data == true)
    }

    @Test
    fun `test failure delete message, internet is not available`() = runBlocking {
        val message = MessageCacheModel(message = "HelloWorld!", senderUid = "1", receiverUid = "2")
        Mockito.`when`(manageFirebaseMessageRepository.deleteMessage(message)).thenReturn(
            Resource.Error(false, NetworkException(ExceptionMessages.INTERNET_IS_UNAVAILABLE))
        )
        val result = deleteFirebaseMessageUseCase.execute(message)
        Assertions.assertTrue(result.exception?.message == ExceptionMessages.INTERNET_IS_UNAVAILABLE)
    }
}