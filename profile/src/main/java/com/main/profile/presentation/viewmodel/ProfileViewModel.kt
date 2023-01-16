package com.main.profile.presentation.viewmodel

import android.content.Intent
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.main.core.DispatchersList
import com.main.core.ManageImageRepository
import com.main.core.exception.NetworkException
import com.main.profile.data.entities.UserInfo
import com.main.profile.data.entities.UserInfoLocal
import com.main.profile.data.exception.message.ProfileExceptionMessages.INTERNET_IS_UNAVAILABLE
import com.main.profile.domain.navigation.ProfileNavigation
import com.main.profile.domain.usecases.GetUserInfoUseCase
import com.main.profile.domain.usecases.SaveUserInfoUseCase
import com.main.profile.presentation.communication.ObserveProfileCommunications
import com.main.profile.presentation.communication.ProfileCommunication
import com.main.profile.presentation.communication.ValueProfileCommunication
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val saveUserInfoUseCase: SaveUserInfoUseCase,
    private val dispatchers: DispatchersList,
    private val profileCommunication: ProfileCommunication,
    private val profileNavigation: ProfileNavigation,
    private val manageImageRepository: ManageImageRepository
) : ViewModel(), ObserveProfileCommunications, ValueProfileCommunication {

    init {
        receiveUserInfo()
    }

    fun receiveUserInfo() {
        viewModelScope.launch(dispatchers.io()) {
            val result = getUserInfoUseCase.execute()
            if (result.data == null) {
                when (result.exception) {
                    is NetworkException -> {
                        profileCommunication.manageMotionToastText(INTERNET_IS_UNAVAILABLE)
                    }
                }
            }
            profileCommunication.manageUserInfo(result.data ?: UserInfo())
        }
    }

    fun saveUserInfo(userInfoLocal: UserInfoLocal) {
        viewModelScope.launch(dispatchers.io()) {
            val result = saveUserInfoUseCase.execute(userInfoLocal)
            if (result.data == true) return@launch

            when (result.exception) {
                is NetworkException -> {
                    profileCommunication.manageMotionToastText(INTERNET_IS_UNAVAILABLE)
                }
            }
        }
    }

    fun manageMenuItem(menuItem: MenuItem, navController: NavController): Boolean {
        when (menuItem.itemId) {
            com.main.core.R.id.itemDating -> profileNavigation.navigateToDatingFragment(navController)
        }
        return true
    }

    fun onClickChooseImage(launcher: ActivityResultLauncher<Intent>) {
        manageImageRepository.onClickChooseImage(launcher)
    }

    override fun observeMotionToastText(owner: LifecycleOwner, observer: Observer<String>) {
        profileCommunication.observeMotionToastText(owner, observer)
    }

    override fun observeUserInfo(owner: LifecycleOwner, observer: Observer<UserInfo>) {
        profileCommunication.observeUserInfo(owner, observer)
    }

    override fun valueUserInfo(): UserInfo? {
        return profileCommunication.valueUserInfo()
    }
}