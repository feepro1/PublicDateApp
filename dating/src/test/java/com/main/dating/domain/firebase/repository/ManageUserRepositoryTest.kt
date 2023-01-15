package com.main.dating.domain.firebase.repository

import com.main.core.Resource
import com.main.core.exception.NetworkException
import com.main.dating.data.entities.User
import com.main.dating.data.exception.message.DatingExceptionMessages.INTERNET_IS_UNAVAILABLE
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.mockito.Mockito
import org.mockito.kotlin.mock

class ManageUserRepositoryTest {

    private val manageUserRepository = mock<ManageUserRepository>()
    private val user = User(
        firstName = "Vadym", lastName = "Hrynyk",
        avatarUrl = "https://some", email = "vadwwxz@mail.com"
    )

    @Test
    fun `test successful like user`() = runBlocking {
        Mockito.`when`(manageUserRepository.likeUser(user)).thenReturn(Resource.Success(true))
        val result = manageUserRepository.likeUser(user)

        Assertions.assertTrue(result.data == true)
    }

    @Test
    fun `test failure like user, internet is unavailable`() = runBlocking {
        Mockito.`when`(manageUserRepository.likeUser(user)).thenReturn(
            Resource.Error(false, NetworkException(INTERNET_IS_UNAVAILABLE))
        )
        val result = manageUserRepository.likeUser(user)
        val finishResult = result.exception?.message == INTERNET_IS_UNAVAILABLE

        Assertions.assertTrue(finishResult)
    }

    @Test
    fun `test successful dislike user`() = runBlocking {
        Mockito.`when`(manageUserRepository.dislikeUser(user)).thenReturn(
            Resource.Success(true)
        )
        val result = manageUserRepository.dislikeUser(user)

        Assertions.assertTrue(result.data == true)
    }

    @Test
    fun `test failure dislike user, internet is unavailable`() = runBlocking {
        Mockito.`when`(manageUserRepository.dislikeUser(user)).thenReturn(
            Resource.Error(false, NetworkException(INTERNET_IS_UNAVAILABLE))
        )
        val result = manageUserRepository.dislikeUser(user)
        val finishResult = result.exception?.message == INTERNET_IS_UNAVAILABLE

        Assertions.assertTrue(finishResult)
    }
}