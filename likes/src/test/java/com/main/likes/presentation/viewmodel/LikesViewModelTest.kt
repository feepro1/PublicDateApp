package com.main.likes.presentation.viewmodel

import com.main.core.Resource
import com.main.core.exception.ExceptionMessages.INTERNET_IS_UNAVAILABLE
import com.main.core.exception.NetworkException
import com.main.likes.BaseLikesTest
import com.main.likes.data.entities.LikeFromUser
import com.main.likes.domain.firebase.LikesRepository
import com.main.likes.domain.navigation.LikesNavigation
import com.main.likes.domain.usecases.GetAllLikesUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.mockito.Mockito
import org.mockito.kotlin.mock

class LikesViewModelTest : BaseLikesTest() {

    private val likesCommunication = TestLikesCommunication()
    private val likesRepository = mock<LikesRepository>()
    private val getAllLikesUseCase = GetAllLikesUseCase(likesRepository)
    private val likesViewModel = LikesViewModel(
        getAllLikesUseCase = getAllLikesUseCase,
        likesCommunication = likesCommunication,
        likesNavigation = LikesNavigation.Base(),
        dispatchers = TestDispatchersList()
    )

    @Test
    fun `test successful get all likes`() = runBlocking {
        Mockito.`when`(likesRepository.getAllLikes()).thenReturn(
            Resource.Success(LikeFromUser())
        )
        likesViewModel.getAllLikes()
        Assertions.assertTrue(likesCommunication.likes.isNotEmpty())
    }

    @Test
    fun `test successful get all likes, but likes is empty`() = runBlocking {
        Mockito.`when`(likesRepository.getAllLikes()).thenReturn(
            Resource.Success(LikeFromUser())
        )
        likesViewModel.getAllLikes()
        Assertions.assertTrue(likesCommunication.likes.isNotEmpty())
    }

    @Test
    fun `test failure get all likes, internet is not available`() = runBlocking {
        Mockito.`when`(likesRepository.getAllLikes()).thenReturn(
            Resource.Error(LikeFromUser(), NetworkException(INTERNET_IS_UNAVAILABLE))
        )
        likesViewModel.getAllLikes()
        val result = likesCommunication.motionToastError.first() == INTERNET_IS_UNAVAILABLE
        Assertions.assertTrue(result)
    }

}