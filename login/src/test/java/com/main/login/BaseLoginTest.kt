package com.main.login

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.main.core.state.InputTextState
import com.main.login.presentation.communication.LoginCommunication

abstract class BaseLoginTest {

    protected class TestLoginCommunication : LoginCommunication {

        var usernameError = mutableListOf<InputTextState>()
        var passwordError = mutableListOf<InputTextState>()
        var emailError = mutableListOf<InputTextState>()

        override fun manageEmailError(inputTextState: InputTextState) {
            usernameError.add(inputTextState)
        }

        override fun manageUsernameError(inputTextState: InputTextState) {
            passwordError.add(inputTextState)
        }

        override fun managePasswordError(inputTextState: InputTextState) {
            emailError.add(inputTextState)
        }

        override fun observeLoginEmailError(owner: LifecycleOwner, observer: Observer<InputTextState>) = Unit

        override fun observeLoginUsernameError(owner: LifecycleOwner, observer: Observer<InputTextState>) = Unit

        override fun observeLoginPasswordError(owner: LifecycleOwner, observer: Observer<InputTextState>) = Unit
    }

//    protected class TestDispatchersList(
//        private val dispatcher: CoroutineDispatcher = UnconfinedTestDispatcher()
//    ) : DispatchersList {
//
//        override fun io(): CoroutineDispatcher = dispatcher
//        override fun ui(): CoroutineDispatcher = dispatcher
//    }

}