package com.example.profile.presentation.viewmodel

import com.example.profile.BaseProfileTest
import com.example.profile.data.entities.UserInfo
import com.example.profile.data.exception.message.ProfileExceptionMessages.INTERNET_IS_UNAVAILABLE
import com.example.profile.domain.firebase.GetUserInfoRepository
import com.example.profile.domain.firebase.SaveUserInfoRepository
import com.example.profile.domain.usecases.GetUserInfoUseCase
import com.example.profile.domain.usecases.SaveUserInfoUseCase
import com.main.core.Resource
import com.main.core.exception.NetworkException
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.mockito.Mockito
import org.mockito.kotlin.mock

class ProfileViewModelTest : BaseProfileTest() {

    private val profileCommunication = TestProfileCommunication()
    private val getUserInfoRepository = mock<GetUserInfoRepository>()
    private val getUserInfoUseCase = GetUserInfoUseCase(getUserInfoRepository)
    private val saveUserInfoRepository = mock<SaveUserInfoRepository>()
    private val saveUserInfoUseCase = SaveUserInfoUseCase(saveUserInfoRepository)
    private val profileViewModel = ProfileViewModel(
        getUserInfoUseCase = getUserInfoUseCase,
        saveUserInfoUseCase = saveUserInfoUseCase,
        dispatchers = TestDispatchersList(),
        profileCommunication = profileCommunication
    )

    @BeforeEach
    fun setUp() {
        profileCommunication.motionToastText.clear()
    }

    @Test
    fun `test successful get user info`() = runBlocking {
        Mockito.`when`(getUserInfoUseCase.execute()).thenReturn(
            Resource.Success(UserInfo())
        )
        profileViewModel.receiveUserInfo()

        Assertions.assertTrue(profileCommunication.motionToastText.isEmpty())
    }

    @Test
    fun `test successful save user info`() = runBlocking {
        Mockito.`when`(saveUserInfoUseCase.execute(UserInfo())).thenReturn(
            Resource.Success(true)
        )
        profileViewModel.saveUserInfo(UserInfo())

        Assertions.assertTrue(profileCommunication.motionToastText.isEmpty())
    }

    @Test
    fun `test failure get user info, internet is not available`() = runBlocking {
        Mockito.`when`(getUserInfoUseCase.execute()).thenReturn(
            Resource.Error(null, NetworkException(INTERNET_IS_UNAVAILABLE))
        )
        profileViewModel.receiveUserInfo()
        val result = profileCommunication.motionToastText.first() == INTERNET_IS_UNAVAILABLE

        Assertions.assertTrue(result)
    }

    @Test
    fun `test failure save user info, internet is not available`() = runBlocking {
        Mockito.`when`(saveUserInfoUseCase.execute(UserInfo())).thenReturn(
            Resource.Error(false, NetworkException(INTERNET_IS_UNAVAILABLE))
        )
        profileViewModel.saveUserInfo(UserInfo())
        val result = profileCommunication.motionToastText.first() == INTERNET_IS_UNAVAILABLE

        Assertions.assertTrue(result)
    }
}