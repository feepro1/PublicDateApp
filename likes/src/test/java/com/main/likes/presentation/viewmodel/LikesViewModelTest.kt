package com.main.likes.presentation.viewmodel

import com.main.core.Resource
import com.main.core.entities.User
import com.main.core.exception.ExceptionMessages.INTERNET_IS_UNAVAILABLE
import com.main.core.exception.ExceptionMessages.UID_IS_EMPTY
import com.main.core.exception.NetworkException
import com.main.core.exception.UidException
import com.main.likes.BaseLikesTest
import com.main.likes.domain.firebase.LikesRepository
import com.main.likes.domain.firebase.UserRepository
import com.main.likes.domain.navigation.LikesNavigation
import com.main.likes.domain.usecases.GetAllLikesUseCase
import com.main.likes.domain.usecases.GetUserByUidUseCase
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
    private val getUserByUidUseCase = GetUserByUidUseCase(userRepository)
    private val likesViewModel = LikesViewModel(
        getAllLikesUseCase = getAllLikesUseCase,
        likeUserUseCase = likeUserUseCase,
        getUserByUidUseCase = getUserByUidUseCase,
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
        likesViewModel.likeUser()
        Assertions.assertTrue(likesCommunication.motionToastError.isEmpty())
    }

    @Test
    fun `test failure like user, internet is not available`() = runBlocking {
        likesCommunication.manageCurrentUser(User())
        Mockito.`when`(likesRepository.likeUser(User())).thenReturn(
            Resource.Error(false, NetworkException(INTERNET_IS_UNAVAILABLE))
        )
        likesViewModel.likeUser()
        val result = likesCommunication.motionToastError.first() == INTERNET_IS_UNAVAILABLE
        Assertions.assertTrue(result)
    }

    @Test
    fun `test successful get user by uid`() = runBlocking {
        Mockito.`when`(userRepository.getUserByUid("test_uid")).thenReturn(
            Resource.Success(User())
        )
        likesViewModel.getUserByUid("test_uid")
        Assertions.assertTrue(likesCommunication.user.isNotEmpty())
    }

    @Test
    fun `test failure get user by uid, internet is not available`() = runBlocking {
        Mockito.`when`(userRepository.getUserByUid("test_uid")).thenReturn(
            Resource.Error(User(), NetworkException(INTERNET_IS_UNAVAILABLE))
        )
        likesViewModel.getUserByUid("test_uid")
        val result = likesCommunication.motionToastError.first() == INTERNET_IS_UNAVAILABLE
        Assertions.assertTrue(result)
    }

    @Test
    fun `test failure get user by uid, uid is empty`() = runBlocking {
        Mockito.`when`(userRepository.getUserByUid("")).thenReturn(
            Resource.Error(User(), UidException(UID_IS_EMPTY))
        )
        likesViewModel.getUserByUid("")
        val result = likesCommunication.motionToastError.first() == UID_IS_EMPTY
        Assertions.assertTrue(result)
    }
}