package com.main.likes.presentation.viewmodel

import com.main.core.Resource
import com.main.core.exception.NetworkException
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.mockito.Mockito
import org.mockito.kotlin.mock

class LikesViewModelTest {
    private val chatsCommunication = TestLikesCommunication()
    private val likesRepository = mock<LikesRepository>()
    private val getAllLikesUseCase = GetAllLikesUseCase(likesRepository)
    private val likesViewModel = LikesViewModel(
        getAllLikesUseCase = getAllLikesUseCase,
        chatsCommunication = chatsCommunication,
        chatsNavigation = LikesNavigation.Base(),
        dispatchers = TestDispatchersList()
    )

    @Test
    fun `test successful get all likes`() = runBlocking {
        Mockito.`when`(likesRepository.getAllLikes()).thenReturn(
            Resource.Success(listOf(LikeFromUser()))
        )
        likesViewModel.getAllLikes()
        Assertions.assertTrue(chatsCommunication.likes.isNotEmpty())
    }

    @Test
    fun `test successful get all likes, but likes is empty`() = runBlocking {
        Mockito.`when`(likesRepository.getAllLikes()).thenReturn(
            Resource.Success(emptyList())
        )
        likesViewModel.getAllLikes()
        Assertions.assertTrue(chatsCommunication.likes.isNotEmpty())
    }

    @Test
    fun `test failure get all likes, internet is not available`() = runBlocking {
        Mockito.`when`(likesRepository.getAllLikes()).thenReturn(
            Resource.Error(emptyList(), NetworkException(INTERNET_IS_UNAVAILABLE))
        )
        likesViewModel.getAllLikes()
        val result = chatsCommunication.motionToastError.first() == INTERNET_IS_UNAVAILABLE
        Assertions.assertTrue(result)
    }

}