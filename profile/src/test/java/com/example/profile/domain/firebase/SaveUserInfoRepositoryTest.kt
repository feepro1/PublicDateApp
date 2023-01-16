package com.example.profile.domain.firebase

import com.example.profile.data.entities.UserInfo
import com.example.profile.data.exception.message.ProfileExceptionMessages.INTERNET_IS_UNAVAILABLE
import com.main.core.Resource
import com.main.core.exception.NetworkException
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.mockito.Mockito
import org.mockito.kotlin.mock

class SaveUserInfoRepositoryTest {

    private val saveUserInfoRepository = mock<SaveUserInfoRepository>()

    @Test
    fun `test successful save user info`() = runBlocking {
        Mockito.`when`(saveUserInfoRepository.saveUserInfo(UserInfo())).thenReturn(
            Resource.Success(true)
        )
        val result = saveUserInfoRepository.saveUserInfo(UserInfo())

        Assertions.assertTrue(result.data == true)
    }

    @Test
    fun `test failure save user info, internet is not available`() = runBlocking {
        Mockito.`when`(saveUserInfoRepository.saveUserInfo(UserInfo())).thenReturn(
            Resource.Error(false, NetworkException(INTERNET_IS_UNAVAILABLE))
        )
        val result = saveUserInfoRepository.saveUserInfo(UserInfo())

        Assertions.assertTrue(result.exception?.message == INTERNET_IS_UNAVAILABLE)
    }

}