package com.main.register.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.main.core.dispatchers.DispatchersList
import com.main.register.data.validation.ValidateStartRegisterData
import com.main.register.domain.navigation.RegisterNavigation
import com.main.register.domain.usecase.RegisterUseCase
import com.main.register.presentation.communication.RegisterCommunication

class RegisterViewModelFactory(
    private val registerUseCase: RegisterUseCase,
    private val registerCommunication: RegisterCommunication,
    private val dispatchers: DispatchersList,
    private val registerNavigation: RegisterNavigation,
    private val validateStartRegisterData: ValidateStartRegisterData
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RegisterViewModel(
            registerUseCase = registerUseCase,
            registerCommunication = registerCommunication,
            dispatchers = dispatchers,
            registerNavigation = registerNavigation,
            validateStartRegisterData = validateStartRegisterData
        ) as T
    }
}