package com.main.login.presentation.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.main.core.dispatchers.DispatchersList
import com.main.core.exception.ApplicationException
import com.main.core.exception.EmailException
import com.main.core.exception.PasswordException
import com.main.core.exception.UsernameException
import com.main.core.state.InputTextState
import com.main.login.data.entities.LoginData
import com.main.login.domain.exception.ManageLoginError
import com.main.login.domain.usecase.LoginUseCase
import com.main.login.presentation.communication.LoginCommunication
import com.main.login.presentation.communication.ObserveLoginErrors
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

//todo null safety
class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val loginCommunication: LoginCommunication,
    private val dispatchers: DispatchersList
) : ViewModel(), ObserveLoginErrors, ManageLoginError {

    fun login(loginData: LoginData) {
        viewModelScope.launch(dispatchers.io()) {
            val result = loginUseCase.execute(loginData)
            //todo make business logic
            if (result.data == true) {
                return@launch
            }
            when (result.exception) {
                is PasswordException -> {
                    loginCommunication.managePasswordError(InputTextState.ShowError(result.exception?.message!!))
                }
                is EmailException -> {
                    loginCommunication.manageEmailError(InputTextState.ShowError(result.exception?.message!!))
                }
                is UsernameException -> {
                    loginCommunication.manageUsernameError(InputTextState.ShowError(result.exception?.message!!))
                }
            }
        }
    }

    override fun observeLoginEmailError(owner: LifecycleOwner, observer: Observer<InputTextState>) =
        loginCommunication.observeLoginEmailError(owner, observer)

    override fun observeLoginUsernameError(
        owner: LifecycleOwner,
        observer: Observer<InputTextState>
    ) =
        loginCommunication.observeLoginUsernameError(owner, observer)

    override fun observeLoginPasswordError(
        owner: LifecycleOwner,
        observer: Observer<InputTextState>
    ) =
        loginCommunication.observeLoginPasswordError(owner, observer)

    override fun clearEmailError() =
        loginCommunication.manageEmailError(InputTextState.ClearError())

    override fun clearUsernameError() =
        loginCommunication.manageUsernameError(InputTextState.ClearError())

    override fun clearPasswordError() =
        loginCommunication.managePasswordError(InputTextState.ClearError())

    override fun showEmailMessage(exception: ApplicationException) =
        loginCommunication.manageEmailError(InputTextState.ShowError(exception.message!!))

    override fun showUsernameMessage(exception: ApplicationException) =
        loginCommunication.manageUsernameError(InputTextState.ShowError(exception.message!!))

    override fun showPasswordMessage(exception: ApplicationException) =
        loginCommunication.managePasswordError(InputTextState.ShowError(exception.message!!))
}