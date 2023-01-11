package com.main.login.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.main.core.DispatchersList
import com.main.login.domain.navigation.LoginNavigation
import com.main.login.domain.usecase.LoginUseCase
import com.main.login.presentation.communication.LoginCommunication
import javax.inject.Inject

class LoginViewModelFactory @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val loginCommunication: LoginCommunication,
    private val dispatchers: DispatchersList,
    private val loginNavigation: LoginNavigation
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginViewModel(
            loginUseCase = loginUseCase,
            loginCommunication = loginCommunication,
            dispatchers = dispatchers,
            loginNavigation = loginNavigation
        ) as T
    }
}