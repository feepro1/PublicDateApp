package com.main.likes.presentation.viewmodel

import com.main.core.Resource
import com.main.core.entities.User
import com.main.core.exception.ExceptionMessages.INTERNET_IS_UNAVAILABLE
import com.main.core.exception.NetworkException
import com.main.likes.BaseLikesTest
import com.main.likes.domain.firebase.LikesRepository
import com.main.likes.domain.navigation.LikesNavigation
import com.main.likes.domain.usecases.GetAllLikesUseCase
import com.main.likes.domain.usecases.GetCurrentUserUseCaseTest
import com.main.likes.domain.usecases.LikeUserUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.mockito.Mockito
import org.mockito.kotlin.mock

class LikesViewModelTest : BaseLikesTest() {

    private val likesCommunication = TestLikesCommunication()
    private val likesRepository = mock<LikesRepository>()
    private val userRepository = mock<UserRepository>()
    private val getAllLikesUseCase = GetAllLikesUseCase(likesRepository)
    private val likeUserUseCase = LikeUserUseCase(likesRepository)
    private val getCurrentUserUseCase = GetCurrentUserUseCase(userRepository)
    private val likesViewModel = LikesViewModel(
        getAllLikesUseCase = getAllLikesUseCase,
        likeUserUseCase = likeUserUseCase,
        getCurrentUserUseCase = getCurrentUserUseCase,
        likesCommunication = likesCommunication,
        likesNavigation = LikesNavigation.Base(),
        dispatchers = TestDispatchersList()
    )

    @Test
    fun `test successful get all likes`() = runBlocking {
        Mockito.`when`(likesRepository.getAllLikes()).thenReturn(
            Resource.Success(User())
        )
        likesViewModel.getAllLikes()
        Assertions.assertTrue(likesCommunication.likes.isNotEmpty())
    }

    @Test
    fun `test successful get all likes, but likes is empty`() = runBlocking {
        Mockito.`when`(likesRepository.getAllLikes()).thenReturn(
            Resource.Success(User())
        )
        likesViewModel.getAllLikes()
        Assertions.assertTrue(likesCommunication.likes.isNotEmpty())
    }

    @Test
    fun `test failure get all likes, internet is not available`() = runBlocking {
        Mockito.`when`(likesRepository.getAllLikes()).thenReturn(
            Resource.Error(User(), NetworkException(INTERNET_IS_UNAVAILABLE))
        )
        likesViewModel.getAllLikes()
        val result = likesCommunication.motionToastError.first() == INTERNET_IS_UNAVAILABLE
        Assertions.assertTrue(result)
    }

    @Test
    fun `test successful like user`() = runBlocking {
        Mockito.`when`(likesRepository.likeUser(User())).thenReturn(
            Resource.Success(true)
        )
        likesViewModel.likeUser(User())
        Assertions.assertTrue(likesCommunication.motionToastError.isEmpty())
    }

    @Test
    fun `test failure like user, internet is not available`() = runBlocking {
        Mockito.`when`(likesRepository.likeUser(User())).thenReturn(
            Resource.Error(false, NetworkException(INTERNET_IS_UNAVAILABLE))
        )
        likesViewModel.likeUser(User())
        val result = likesCommunication.motionToastError.first() == INTERNET_IS_UNAVAILABLE
        Assertions.assertTrue(result)
    }

    @Test
    fun `test successful get current user`() = runBlocking {
        Mockito.`when`(userRepository.getCurrentUser()).thenReturn(
            Resource.Success(User())
        )
        likesViewModel.getCurrentUser()
        Assertions.assertTrue(likesCommunication.user.isNotEmpty())
    }

    @Test
    fun `test failure get current user, internet is not available`() = runBlocking {
        Mockito.`when`(userRepository.getCurrentUser()).thenReturn(
            Resource.Error(User(), NetworkException(INTERNET_IS_UNAVAILABLE))
        )
        likesViewModel.getCurrentUser()
        val result = likesCommunication.motionToastError.first() == INTERNET_IS_UNAVAILABLE
        Assertions.assertTrue(result)
    }
}