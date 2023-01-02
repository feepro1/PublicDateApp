package com.main.login

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.main.core.dispatchers.DispatchersList
import com.main.core.state.InputTextState
import com.main.login.presentation.communication.LoginCommunication
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher

abstract class BaseLoginTest {

    protected class TestLoginCommunication : LoginCommunication {

        var usernameError = mutableListOf<InputTextState.ShowError>()
        var passwordError = mutableListOf<InputTextState.ShowError>()
        var emailError = mutableListOf<InputTextState.ShowError>()

        override fun manageEmailError(inputTextState: InputTextState) {
            if (inputTextState is InputTextState.ShowError) {
                emailError.add(inputTextState)
            }
        }

        override fun manageUsernameError(inputTextState: InputTextState) {
            if (inputTextState is InputTextState.ShowError) {
                usernameError.add(inputTextState)
            }
        }

        override fun managePasswordError(inputTextState: InputTextState) {
            if (inputTextState is InputTextState.ShowError) {
                passwordError.add(inputTextState)
            }
        }

        override fun observeLoginEmailError(owner: LifecycleOwner, observer: Observer<InputTextState>) = Unit

        override fun observeLoginUsernameError(owner: LifecycleOwner, observer: Observer<InputTextState>) = Unit

        override fun observeLoginPasswordError(owner: LifecycleOwner, observer: Observer<InputTextState>) = Unit
    }

    protected class TestDispatchersList(
        private val dispatcher: CoroutineDispatcher = UnconfinedTestDispatcher()
    ) : DispatchersList {

        override fun io(): CoroutineDispatcher = dispatcher
        override fun ui(): CoroutineDispatcher = dispatcher
    }
}