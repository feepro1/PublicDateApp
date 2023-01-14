package com.main.dating.presentation.viewmodel

import com.main.core.Resource
import com.main.core.exception.NetworkException
import com.main.dating.BaseDatingTest
import com.main.dating.data.entities.User
import com.main.dating.data.exception.message.DatingExceptionMessages.INTERNET_IS_UNAVAILABLE
import com.main.dating.domain.firebase.database.DatabaseRepository
import com.main.dating.domain.firebase.repository.ManageUserRepository
import com.main.dating.domain.interactor.DatingInteractor
import com.main.dating.domain.usecases.DislikeUserUseCase
import com.main.dating.domain.usecases.GetUsersFromDatabaseUseCase
import com.main.dating.domain.usecases.LikeUserUseCase
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.Test
import org.junit.jupiter.api.BeforeEach
import org.mockito.Mockito
import org.mockito.kotlin.mock

class DatingViewModelTest : BaseDatingTest() {

    private val manageUserRepository = mock<ManageUserRepository>()
    private val databaseRepository = mock<DatabaseRepository>()
    private val getUsersFromDatabaseUseCase = GetUsersFromDatabaseUseCase(
        databaseRepository = databaseRepository
    )
    private val likeUserUseCase = LikeUserUseCase(
        manageUserRepository = manageUserRepository
    )
    private val dislikeUserUseCase = DislikeUserUseCase(
        manageUserRepository = manageUserRepository
    )
    private val datingInteractor = DatingInteractor(
        getUsersFromDatabaseUseCase = getUsersFromDatabaseUseCase,
        likeUserUseCase = likeUserUseCase,
        dislikeUserUseCase = dislikeUserUseCase
    )
    private val user = User(
        firstName = "Vadym", lastName = "Hrynyk",
        avatarUrl = "https://some", email = "vadwwxz@mail.com"
    )
    private val datingCommunication = TestDatingCommunication()
    private val datingViewModel = DatingViewModel(
        datingInteractor = datingInteractor,
        datingCommunication = datingCommunication,
        dispatchers = TestDispatchersList()
    )

    @BeforeEach
    fun setUp() {
        datingCommunication.users.clear()
        datingCommunication.motionToastError.clear()
    }

    @Test
    fun `test successful get users`() = runBlocking {
        Mockito.`when`(datingInteractor.getUsersFromDatabase()).thenReturn(
            Resource.Success(
                listOf(
                    User(
                        firstName = "Vadym", lastName = "Hrynyk",
                        avatarUrl = "https://some", email = "vadwwxz@mail.com"
                    )
                )
            )
        )
        datingViewModel.getUsersFromDatabase()

        Assertions.assertTrue(datingCommunication.users.isNotEmpty())
    }

    @Test
    fun `test failure get users, internet is unavailable`() = runBlocking {
        Mockito.`when`(datingInteractor.getUsersFromDatabase()).thenReturn(
            Resource.Error(emptyList(), NetworkException(INTERNET_IS_UNAVAILABLE))
        )
        datingViewModel.getUsersFromDatabase()

        val result = datingCommunication.motionToastError.first() == INTERNET_IS_UNAVAILABLE
        Assertions.assertTrue(result)
    }

    @Test
    fun `test successful like user`() = runBlocking {
        Mockito.`when`(datingInteractor.likeUser(user)).thenReturn(Resource.Success(true))
        datingViewModel.likeUser(user)

        Assertions.assertTrue(datingCommunication.motionToastError.isEmpty())
    }

    @Test
    fun `test failure like user, internet is unavailable`() = runBlocking {
        Mockito.`when`(datingInteractor.likeUser(user)).thenReturn(
            Resource.Error(false, NetworkException(INTERNET_IS_UNAVAILABLE))
        )
        datingViewModel.likeUser(user)

        val result = datingCommunication.motionToastError.first() == INTERNET_IS_UNAVAILABLE
        Assertions.assertTrue(result)
    }

    @Test
    fun `test successful dislike user`() = runBlocking {
        Mockito.`when`(datingInteractor.dislikeUser(user)).thenReturn(Resource.Success(true))
        datingViewModel.dislikeUser(user)

        Assertions.assertTrue(datingCommunication.motionToastError.isEmpty())
    }

    @Test
    fun `test failure dislike user, internet is unavailable`() = runBlocking {
        Mockito.`when`(datingInteractor.dislikeUser(user)).thenReturn(
            Resource.Error(false, NetworkException(INTERNET_IS_UNAVAILABLE))
        )
        datingViewModel.dislikeUser(user)

        val result = datingCommunication.motionToastError.first() == INTERNET_IS_UNAVAILABLE
        Assertions.assertTrue(result)
    }
}