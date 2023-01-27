package com.main.likes.domain.usecases

import com.main.core.Resource
import com.main.core.exception.ExceptionMessages.INTERNET_IS_UNAVAILABLE
import com.main.core.exception.NetworkException
import com.main.likes.domain.firebase.LikesRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.mockito.Mockito
import org.mockito.kotlin.mock

class LikeUserUseCaseTest {

    private val likesRepository = mock<LikesRepository>()
    private val likeUserUseCase = LikeUserUseCase(
        likesRepository = likesRepository
    )

    @Test
    fun `test successful like user`() = runBlocking {
        Mockito.`when`(likesRepository.likeUser()).thenReturn(
            Resource.Success(true)
        )
        val result = likeUserUseCase.execute()
        Assertions.assertTrue(result.data == true)
    }

    @Test
    fun `test failure like user, internet is not available`() = runBlocking {
        Mockito.`when`(likesRepository.likeUser()).thenReturn(
            Resource.Error(false, NetworkException(INTERNET_IS_UNAVAILABLE))
        )
        val result = likeUserUseCase.execute()
        Assertions.assertTrue(result.exception?.message == INTERNET_IS_UNAVAILABLE)
    }
}