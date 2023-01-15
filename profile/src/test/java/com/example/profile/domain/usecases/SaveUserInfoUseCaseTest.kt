package com.example.profile.domain.usecases

import com.main.core.Resource
import com.main.core.exception.NetworkException
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.mockito.Mockito
import org.mockito.kotlin.mock

class SaveUserInfoUseCaseTest {

    private val saveUserInfoRepository = mock<SaveUserInfoRepository>()
    private val getUserInfoUseCase = SaveUserInfoUseCase(
        saveUserInfoRepository = saveUserInfoRepository
    )

    @Test
    fun `test successful save user info`() = runBlocking {
        Mockito.`when`(saveUserInfoRepository.saveUserInfo(UserInfo())).thenReturn(
            Resource.Success(true)
        )
        val result = getUserInfoUseCase.execute(UserInfo())

        Assertions.assertTrue(result.data == true)
    }

    @Test
    fun `test failure save user info, internet is not available`() = runBlocking {
        Mockito.`when`(saveUserInfoRepository.saveUserInfo(UserInfo())).thenReturn(
            Resource.Error(false, NetworkException(INTERNET_IS_UNAVAILABLE))
        )
        val result = getUserInfoUseCase.execute(UserInfo())

        Assertions.assertTrue(result.exception?.message == INTERNET_IS_UNAVAILABLE)
    }

}