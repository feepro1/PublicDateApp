package com.main.register.presentation.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.main.core.dispatchers.DispatchersList
import com.main.core.exception.*
import com.main.core.state.InputTextState
import com.main.register.data.entities.RegisterData
import com.main.register.domain.exception.ManageRegisterCommunications
import com.main.register.domain.navigation.RegisterNavigation
import com.main.register.domain.usecase.RegisterUseCase
import com.main.register.presentation.communication.ObserveRegisterCommunications
import com.main.register.presentation.communication.RegisterCommunication
import com.main.register.presentation.communication.ValueRegisterCommunications
import kotlinx.coroutines.launch

class RegisterViewModel(
    val registerUseCase: RegisterUseCase,
    val registerCommunication: RegisterCommunication,
    val dispatchers: DispatchersList,
    val registerNavigation: RegisterNavigation
) : ViewModel(), ObserveRegisterCommunications, ManageRegisterCommunications, ValueRegisterCommunications<RegisterData> {

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

    fun navigateToLoginFragment(navController: NavController) {
        registerNavigation.navigateToLoginFragment(navController)
    }

    fun navigateToFinishRegisterFragment(navController: NavController) {
        registerNavigation.navigateToFinishRegisterFragment(navController)
    }

    override fun observeRegisterEmailError(owner: LifecycleOwner, observer: Observer<InputTextState>) =
        registerCommunication.observeRegisterEmailError(owner, observer)

    override fun observeRegisterPasswordError(owner: LifecycleOwner, observer: Observer<InputTextState>) =
        registerCommunication.observeRegisterPasswordError(owner, observer)

    override fun observeRegisterConfirmPasswordError(owner: LifecycleOwner, observer: Observer<InputTextState>) =
        registerCommunication.observeRegisterConfirmPasswordError(owner, observer)

    override fun observeRegisterFirstNameError(owner: LifecycleOwner, observer: Observer<InputTextState>) =
        registerCommunication.observeRegisterFirstNameError(owner, observer)

    override fun observeRegisterLastNameError(owner: LifecycleOwner, observer: Observer<InputTextState>) =
        registerCommunication.observeRegisterFirstNameError(owner, observer)


    override fun clearEmailError() = registerCommunication.manageEmailError(InputTextState.ClearError())

    override fun clearPasswordError() = registerCommunication.managePasswordError(InputTextState.ClearError())

    override fun clearConfirmPasswordError() = registerCommunication.manageConfirmPasswordError(InputTextState.ClearError())

    override fun clearFirstNameError() = registerCommunication.manageFirstNameError(InputTextState.ClearError())

    override fun clearLastNameError() = registerCommunication.manageLastNameError(InputTextState.ClearError())

    override fun showEmailMessage(exception: ApplicationException) =
         registerCommunication.manageEmailError(InputTextState.ShowError(exception.message!!))

    override fun showPasswordMessage(exception: ApplicationException) =
        registerCommunication.managePasswordError(InputTextState.ShowError(exception.message!!))

    override fun showConfirmPasswordMessage(exception: ApplicationException) =
        registerCommunication.manageConfirmPasswordError(InputTextState.ShowError(exception.message!!))

    override fun showFirstNameMessage(exception: ApplicationException) =
        registerCommunication.manageFirstNameError(InputTextState.ShowError(exception.message!!))

    override fun showLastNameMessage(exception: ApplicationException) =
        registerCommunication.manageLastNameError(InputTextState.ShowError(exception.message!!))

    override fun mapRegisterData(registerData: RegisterData) {
        registerCommunication.manageRegisterData(registerData = registerData)
    }

    override fun valueRegisterData(): RegisterData? {
        return registerCommunication.valueRegisterData()
    }
}