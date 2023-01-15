package com.main.dating.domain.usecases

import com.main.core.Resource
import com.main.core.exception.NetworkException
import com.main.dating.data.entities.User
import com.main.dating.data.exception.message.DatingExceptionMessages.INTERNET_IS_UNAVAILABLE
import com.main.dating.domain.firebase.repository.ManageUserRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.mockito.Mockito
import org.mockito.kotlin.mock

class LikeUserUseCaseTest {

    private val manageUserRepository = mock<ManageUserRepository>()
    private val likeUserUseCase = LikeUserUseCase(
        manageUserRepository = manageUserRepository
    )

    private val user = User(
        firstName = "Vadym", lastName = "Hrynyk",
        avatarUrl = "https://some", email = "vadwwxz@mail.com"
    )

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
        val finishResult = result.exception?.message == INTERNET_IS_UNAVAILABLE

        Assertions.assertTrue(finishResult)
    }
}