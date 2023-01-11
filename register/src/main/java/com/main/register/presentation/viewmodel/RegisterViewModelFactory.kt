package com.main.register.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.main.core.DispatchersList
import com.main.core.ManageImageRepository
import com.main.register.data.validation.ValidateStartRegisterData
import com.main.register.domain.navigation.RegisterNavigation
import com.main.register.domain.usecase.RegisterUseCase
import com.main.register.presentation.communication.RegisterCommunication

class RegisterViewModelFactory(
    private val registerUseCase: RegisterUseCase,
    private val registerCommunication: RegisterCommunication,
    private val dispatchers: DispatchersList,
    private val registerNavigation: RegisterNavigation,
    private val validateStartRegisterData: ValidateStartRegisterData,
    private val manageImageRepository: ManageImageRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RegisterViewModel(
            registerUseCase = registerUseCase,
            registerCommunication = registerCommunication,
            dispatchers = dispatchers,
            registerNavigation = registerNavigation,
            validateStartRegisterData = validateStartRegisterData,
            manageImageRepository = manageImageRepository
        ) as T
    }
}