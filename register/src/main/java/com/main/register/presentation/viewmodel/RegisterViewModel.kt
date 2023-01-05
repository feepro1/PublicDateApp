package com.main.register.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.main.core.dispatchers.DispatchersList
import com.main.core.exception.*
import com.main.core.state.InputTextState
import com.main.register.data.entities.RegisterData
import com.main.register.domain.navigation.RegisterNavigation
import com.main.register.domain.usecase.RegisterUseCase
import com.main.register.presentation.communication.RegisterCommunication
import kotlinx.coroutines.launch

class RegisterViewModel(
    val registerUseCase: RegisterUseCase,
    val registerCommunication: RegisterCommunication,
    val dispatchers: DispatchersList,
    val registerNavigation: RegisterNavigation
) : ViewModel() {

    fun register(registerData: RegisterData) {
        viewModelScope.launch(dispatchers.io()) {
            val result = registerUseCase.execute(registerData)
            if (result.data == true) {
                //todo navigate to main fragment
                return@launch
            }
            when (result.exception) {
                is PasswordException -> {
                    registerCommunication.managePasswordError(InputTextState.ShowError(result.exception?.message!!))
                }
                is EmailException -> {
                    registerCommunication.manageEmailError(InputTextState.ShowError(result.exception?.message!!))
                }
                is ConfirmPasswordException -> {
                    registerCommunication.manageConfirmPasswordError(InputTextState.ShowError(result.exception?.message!!))
                }
                is FirstNameException -> {
                    registerCommunication.manageFirstNameError(InputTextState.ShowError(result.exception?.message!!))
                }
                is LastNameException -> {
                    registerCommunication.manageLastNameError(InputTextState.ShowError(result.exception?.message!!))
                }
            }
        }
    }

}