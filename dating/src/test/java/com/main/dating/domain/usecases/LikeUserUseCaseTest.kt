package com.main.dating.domain.usecases

import com.main.core.Resource
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class LikeUserUseCaseTest {

    private val manageUserRepository = mock<ManageUserRepository>()
    private val likeUserUseCase = LikeUserUseCase(
        manageUserRepository = manageUserRepository
    )

    @Test
    fun `test successful like user`() = runBlocking {
        val user = User(
            firstName = "Vadym", lastName = "Hrynyk",
            avatarUrl = "https://some", email = "vadwwxz@mail.com"
        )
        Mockito.`when`(manageUserRepository.likeUser(user)).thenReturn(
            Resource.Error(false, NetworkException(INTERNET_IS_UNAVAILABLE))
        )
        val result = likeUserUseCase.execute(user)

        Assertions.assertTrue(result.data == true)
    }

    @Test
    fun `test failure like user, internet is unavailable`() = runBlocking {
        val user = User(
            firstName = "Vadym", lastName = "Hrynyk",
            avatarUrl = "https://some", email = "vadwwxz@mail.com"
        )
        val result = likeUserUseCase.execute(user)
        val finishResult = result.exception.message == INTERNET_IS_UNAVAILABLE

        Assertions.assertTrue(finishResult)
    }

}