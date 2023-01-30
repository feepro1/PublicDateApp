package com.main.chats.domain.usecases

import com.main.chats.domain.firebase.ChatsRepository
import com.main.core.Resource
import com.main.core.base.entity.Chat
import com.main.core.exception.ExceptionMessages
import com.main.core.exception.ExceptionMessages.INTERNET_IS_UNAVAILABLE
import com.main.core.exception.NetworkException
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.mockito.Mockito
import org.mockito.kotlin.mock

class DeleteChatUseCaseTest {

    private val chatsRepository = mock<ChatsRepository>()
    private val deleteChatUseCase = DeleteChatUseCase(
        chatsRepository = chatsRepository
    )
    //todo make a better unit testing

    @Test
    fun `test successful delete chat`() = runBlocking {
        Mockito.`when`(chatsRepository.deleteChat(Chat())).thenReturn(
            Resource.Success(true)
        )
        val result = deleteChatUseCase.execute(Chat())
        Assertions.assertTrue(result.data?.isNotEmpty() == true)
    }

    @Test
    fun `test failure delete chat, internet is not available`() = runBlocking {
        Mockito.`when`(chatsRepository.deleteChat(Chat())).thenReturn(
            Resource.Error(false, NetworkException(INTERNET_IS_UNAVAILABLE))
        )
        val result = deleteChatUseCase.execute(Chat())
        Assertions.assertTrue(result.exception?.message == INTERNET_IS_UNAVAILABLE)
    }
}