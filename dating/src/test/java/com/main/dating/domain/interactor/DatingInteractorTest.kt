package com.main.dating.domain.interactor

import com.main.core.Resource
import com.main.core.exception.NetworkException
import com.main.dating.data.entities.User
import com.main.dating.data.exception.message.DatingExceptionMessages.INTERNET_IS_UNAVAILABLE
import com.main.dating.data.realization.firebase.database.DatabaseRepository
import com.main.dating.domain.firebase.repository.ManageUserRepository
import com.main.dating.domain.usecases.DislikeUserUseCase
import com.main.dating.domain.usecases.GetUsersFromDatabaseUseCase
import com.main.dating.domain.usecases.LikeUserUseCase
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class DatingInteractorTest {

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

    @Test
    fun `test successful get users`() = runBlocking {
        Mockito.`when`(getUsersFromDatabaseUseCase.execute()).thenReturn(
            Resource.Success(
                listOf(
                    User(
                        firstName = "Vadym", lastName = "Hrynyk",
                        avatarUrl = "https://some", email = "vadwwxz@mail.com"
                    )
                )
            )
        )
        val result = datingInteractor.getUsersFromDatabase()

        Assertions.assertTrue(result.data?.isNotEmpty() == true)
    }

    @Test
    fun `test failure get users, internet is unavailable`() = runBlocking {
        Mockito.`when`(getUsersFromDatabaseUseCase.execute()).thenReturn(
            Resource.Error(emptyList(), NetworkException(INTERNET_IS_UNAVAILABLE))
        )
        val result = datingInteractor.getUsersFromDatabase()

        val assertsResult = result.exception?.message == INTERNET_IS_UNAVAILABLE
        Assertions.assertTrue(assertsResult)
    }

    @Test
    fun `test successful like user`() = runBlocking {
        Mockito.`when`(likeUserUseCase.execute(user)).thenReturn(Resource.Success(true))
        val result = datingInteractor.likeUser(user)

        Assertions.assertTrue(result.data == true)
    }

    @Test
    fun `test failure like user, internet is unavailable`() = runBlocking {
        Mockito.`when`(likeUserUseCase.execute(user)).thenReturn(
            Resource.Error(false, NetworkException(INTERNET_IS_UNAVAILABLE))
        )
        val result = datingInteractor.likeUser(user)
        val finishResult = result.exception?.message == INTERNET_IS_UNAVAILABLE

        Assertions.assertTrue(finishResult)
    }

    @Test
    fun `test successful dislike user`() = runBlocking {
        Mockito.`when`(dislikeUserUseCase.execute(user)).thenReturn(
            Resource.Success(true)
        )
        val result = datingInteractor.dislikeUser(user)

        Assertions.assertTrue(result.data == true)
    }

    @Test
    fun `test failure dislike user, internet is unavailable`() = runBlocking {
        Mockito.`when`(dislikeUserUseCase.execute(user)).thenReturn(
            Resource.Error(false, NetworkException(INTERNET_IS_UNAVAILABLE))
        )
        val result = datingInteractor.dislikeUser(user)
        val finishResult = result.exception?.message == INTERNET_IS_UNAVAILABLE

        Assertions.assertTrue(finishResult)
    }
}