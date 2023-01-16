package com.example.profile.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.profile.domain.usecases.GetUserInfoUseCase
import com.example.profile.domain.usecases.SaveUserInfoUseCase
import com.example.profile.presentation.communication.ProfileCommunication
import com.main.core.DispatchersList

class ProfileViewModelFactory(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val saveUserInfoUseCase: SaveUserInfoUseCase,
    private val dispatchers: DispatchersList,
    private val profileCommunication: ProfileCommunication
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProfileViewModel(
            getUserInfoUseCase = getUserInfoUseCase,
            saveUserInfoUseCase = saveUserInfoUseCase,
            dispatchers = dispatchers,
            profileCommunication = profileCommunication
        ) as T
    }
}