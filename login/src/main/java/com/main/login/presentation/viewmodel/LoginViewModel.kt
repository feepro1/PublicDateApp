package com.main.login.presentation.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.main.core.DispatchersList
import com.main.core.exception.ApplicationException
import com.main.core.exception.EmailException
import com.main.core.exception.PasswordException
import com.main.core.state.InputTextState
import com.main.login.data.entities.LoginData
import com.main.login.domain.exception.ManageLoginError
import com.main.login.domain.navigation.LoginNavigation
import com.main.login.domain.usecase.LoginUseCase
import com.main.login.presentation.communication.LoginCommunication
import com.main.login.presentation.communication.ObserveLoginErrors
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val loginCommunication: LoginCommunication,
    private val dispatchers: DispatchersList,
    private val loginNavigation: LoginNavigation
) : ViewModel(), ObserveLoginErrors, ManageLoginError {

    fun login(loginData: LoginData, navController: NavController) {
        viewModelScope.launch(dispatchers.io()) {
            val result = loginUseCase.execute(loginData)
            if (result.data == true) {
                withContext(dispatchers.ui()) {
                    loginNavigation.navigateToDatingFragment(navController)
                }
            }
            when (result.exception) {
                is PasswordException -> {
                    loginCommunication.managePasswordError(InputTextState.ShowError(result.exception?.message!!))
                }
                is EmailException -> {
                    loginCommunication.manageEmailError(InputTextState.ShowError(result.exception?.message!!))
                }
            }
        }
    }

    fun checkIsUserSignedIn(navController: NavController) {
        if (Firebase.auth.currentUser != null && Firebase.auth.currentUser?.isEmailVerified == true) {
            loginNavigation.navigateToDatingFragment(navController)
        }
    }

    fun navigateToStartRegisterFragment(navController: NavController) {
        loginNavigation.navigateToRegisterFragment(navController)
    }

    override fun observeLoginEmailError(owner: LifecycleOwner, observer: Observer<InputTextState>) =
        loginCommunication.observeLoginEmailError(owner, observer)

    override fun observeLoginPasswordError(owner: LifecycleOwner, observer: Observer<InputTextState>) =
        loginCommunication.observeLoginPasswordError(owner, observer)

    override fun clearEmailError() = loginCommunication.manageEmailError(InputTextState.ClearError())

    override fun clearPasswordError() = loginCommunication.managePasswordError(InputTextState.ClearError())

    override fun showEmailMessage(exception: ApplicationException) =
        loginCommunication.manageEmailError(InputTextState.ShowError(exception.message!!))

    override fun showPasswordMessage(exception: ApplicationException) =
        loginCommunication.managePasswordError(InputTextState.ShowError(exception.message!!))
}