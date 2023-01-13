package com.main.dating.domain.usecases

import com.main.core.Resource
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class DislikeUserUseCaseTest {

    private val manageUserRepository = mock<ManageUserRepository>()
    private val dislikeUserUseCase = DislikeUserUseCase(
        manageUserRepository = manageUserRepository
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
}