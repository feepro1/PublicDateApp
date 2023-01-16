package com.main.profile.presentation.viewmodel

import android.view.MenuItem
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.main.profile.data.entities.UserInfo
import com.main.profile.data.exception.message.ProfileExceptionMessages.INTERNET_IS_UNAVAILABLE
import com.main.profile.domain.usecases.GetUserInfoUseCase
import com.main.profile.domain.usecases.SaveUserInfoUseCase
import com.main.profile.presentation.communication.ObserveProfileCommunications
import com.main.profile.presentation.communication.ProfileCommunication
import com.main.core.DispatchersList
import com.main.core.exception.NetworkException
import com.main.profile.domain.navigation.ProfileNavigation
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val saveUserInfoUseCase: SaveUserInfoUseCase,
    private val dispatchers: DispatchersList,
    private val profileCommunication: ProfileCommunication,
    private val profileNavigation: ProfileNavigation
) : ViewModel(), ObserveProfileCommunications {

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
            if (result.data != null) {
                profileCommunication.manageUserInfo(result.data!!)
            }
        }
    }

    fun saveUserInfo(userInfo: UserInfo) {
        viewModelScope.launch(dispatchers.io()) {
            val result = saveUserInfoUseCase.execute(userInfo)
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

    override fun observeMotionToastText(owner: LifecycleOwner, observer: Observer<String>) {
        profileCommunication.observeMotionToastText(owner, observer)
    }

    override fun observeUserInfo(owner: LifecycleOwner, observer: Observer<UserInfo>) {
        profileCommunication.observeUserInfo(owner, observer)
    }
}