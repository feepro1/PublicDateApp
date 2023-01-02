package com.main.login.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.main.core.dispatchers.DispatchersList
import com.main.login.domain.usecase.LoginUseCase
import com.main.login.presentation.communication.LoginCommunication
import javax.inject.Inject

class LoginViewModelFactory @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val loginCommunication: LoginCommunication,
    private val dispatchers: DispatchersList
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginViewModel(
            loginUseCase = loginUseCase,
            loginCommunication = loginCommunication,
            dispatchers = dispatchers
        ) as T
    }
}