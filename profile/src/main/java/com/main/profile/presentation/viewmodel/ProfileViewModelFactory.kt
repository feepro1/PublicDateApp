package com.main.profile.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.main.profile.domain.usecases.GetUserInfoUseCase
import com.main.profile.domain.usecases.SaveUserInfoUseCase
import com.main.profile.presentation.communication.ProfileCommunication
import com.main.core.DispatchersList
import com.main.profile.domain.navigation.ProfileNavigation

class ProfileViewModelFactory(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val saveUserInfoUseCase: SaveUserInfoUseCase,
    private val dispatchers: DispatchersList,
    private val profileCommunication: ProfileCommunication,
    private val profileNavigation: ProfileNavigation
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProfileViewModel(
            getUserInfoUseCase = getUserInfoUseCase,
            saveUserInfoUseCase = saveUserInfoUseCase,
            dispatchers = dispatchers,
            profileCommunication = profileCommunication,
            profileNavigation = profileNavigation
        ) as T
    }
}