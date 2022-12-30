package com.main.login.presentation.communication

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.main.core.communication.Communication
import com.main.core.state.InputTextState

interface LoginCommunication: ObserveLoginErrors {

    fun manageEmailError(inputTextState: InputTextState)

    fun manageUsernameError(inputTextState: InputTextState)

    fun managePasswordError(inputTextState: InputTextState)

    class Base(
        private val loginEmailCommunication: LoginEmailCommunication,
        private val loginUsernameCommunication: LoginUsernameCommunication,
        private val loginPasswordCommunication: LoginPasswordCommunication
    ): LoginCommunication {
        override fun manageEmailError(inputTextState: InputTextState) {
            loginEmailCommunication.map(inputTextState)
        }

        override fun manageUsernameError(inputTextState: InputTextState) {
            loginUsernameCommunication.map(inputTextState)
        }

        override fun managePasswordError(inputTextState: InputTextState) {
            loginPasswordCommunication.map(inputTextState)
        }

        override fun observeLoginEmailError(owner: LifecycleOwner, observer: Observer<InputTextState>) {
            loginEmailCommunication.observe(owner, observer)
        }

        override fun observeLoginUsernameError(owner: LifecycleOwner, observer: Observer<InputTextState>) {
            loginUsernameCommunication.observe(owner, observer)
        }

        override fun observeLoginPasswordError(owner: LifecycleOwner, observer: Observer<InputTextState>) {
            loginPasswordCommunication.observe(owner, observer)
        }
    }
}

interface ObserveLoginErrors {

    fun observeLoginEmailError(owner: LifecycleOwner, observer: Observer<InputTextState>)

    fun observeLoginUsernameError(owner: LifecycleOwner, observer: Observer<InputTextState>)

    fun observeLoginPasswordError(owner: LifecycleOwner, observer: Observer<InputTextState>)
}

interface LoginEmailCommunication: Communication.Mutable<InputTextState> {
    class Base: Communication.Ui<InputTextState>(), LoginEmailCommunication
}

interface LoginUsernameCommunication: Communication.Mutable<InputTextState> {
    class Base: Communication.Ui<InputTextState>(), LoginUsernameCommunication
}

interface LoginPasswordCommunication: Communication.Mutable<InputTextState> {
    class Base: Communication.Ui<InputTextState>(), LoginPasswordCommunication
}