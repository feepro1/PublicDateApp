package com.main.dating.domain.interactor

import com.main.core.Resource
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
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
    fun `test successful dislike user`() = runBlocking {
        Mockito.`when`(manageUserRepository.dislikeUser(user)).thenReturn(
            Resource.Success(true)
        )
        val result = dislikeUserUseCase.execute(user)

        Assertions.assertTrue(result.data == true)
    }

    @Test
    fun `test failure dislike user, internet is unavailable`() = runBlocking {
        Mockito.`when`(manageUserRepository.dislikeUser(user)).thenReturn(
            Resource.Error(false, NetworkException(INTERNET_IS_UNAVAILABLE))
        )
        val result = dislikeUserUseCase.execute(user)
        val finishResult = result.exception.message == INTERNET_IS_UNAVAILABLE

        Assertions.assertTrue(finishResult)
    }

    @Test
    fun `test successful like user`() = runBlocking {
        Mockito.`when`(manageUserRepository.likeUser(user)).thenReturn(Resource.Success(true))
        val result = likeUserUseCase.execute(user)

        Assertions.assertTrue(result.data == true)
    }

    @Test
    fun `test failure like user, internet is unavailable`() = runBlocking {
        Mockito.`when`(manageUserRepository.likeUser(user)).thenReturn(
            Resource.Error(false, NetworkException(INTERNET_IS_UNAVAILABLE))
        )
        val result = likeUserUseCase.execute(user)
        val finishResult = result.exception.message == INTERNET_IS_UNAVAILABLE

        Assertions.assertTrue(finishResult)
    }

    @Test
    fun `test successful get users`() = runBlocking {
        Mockito.`when`(databaseRepository.getUsers()).thenReturn(
            Resource.Success(
                listOf(
                    User(
                        firstName = "Vadym", lastName = "Hrynyk",
                        avatarUrl = "https://some", email = "vadwwxz@mail.com"
                    )
                )
            )
        )
        val result = getUsersFromDatabaseUseCase.execute()

        Assertions.assertTrue(result.data.isNotEmpty())
    }

    @Test
    fun `test failure get users, internet is unavailable`() = runBlocking {
        val result = getUsersFromDatabaseUseCase.execute()

        Mockito.`when`(databaseRepository.getUsers()).thenReturn(
            Resource.Error(emptyList(), NetworkException(INTERNET_IS_UNAVAILABLE))
        )

        val assertsResult = result.exception.message == INTERNET_IS_UNAVAILABLE
        Assertions.assertTrue(assertsResult)
    }
}