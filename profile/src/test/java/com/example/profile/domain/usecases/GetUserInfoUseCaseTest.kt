package com.example.profile.domain.usecases

import com.main.core.Resource
import com.main.core.exception.NetworkException
import com.main.profile.data.entities.UserInfo
import com.main.profile.data.exception.message.ProfileExceptionMessages.INTERNET_IS_UNAVAILABLE
import com.main.profile.domain.firebase.GetUserInfoRepository
import com.main.profile.domain.usecases.GetUserInfoUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.mockito.Mockito
import org.mockito.kotlin.mock

class GetUserInfoUseCaseTest {

    private val getUserInfoRepository = mock<GetUserInfoRepository>()
    private val getUserInfoUseCase = GetUserInfoUseCase(
        getUserInfoRepository = getUserInfoRepository
    )

    @Test
    fun `test successful get user info`() = runBlocking {
        Mockito.`when`(getUserInfoRepository.receiveUserInfo()).thenReturn(
            Resource.Success(UserInfo())
        )
        val result = getUserInfoUseCase.execute()

        Assertions.assertTrue(result.data != null)
    }

    @Test
    fun `test failure get user info, internet is not available`() = runBlocking {
        Mockito.`when`(getUserInfoRepository.receiveUserInfo()).thenReturn(
            Resource.Error(null, NetworkException(INTERNET_IS_UNAVAILABLE))
        )
        val result = getUserInfoUseCase.execute()

        Assertions.assertTrue(result.exception?.message == INTERNET_IS_UNAVAILABLE)
    }
}