package com.main.login.presentation.communication

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.main.core.communication.Communication
import com.main.core.state.InputTextState

interface LoginCommunication: ObserveLoginErrors {

    fun manageEmailError(inputTextState: InputTextState)

    fun managePasswordError(inputTextState: InputTextState)

    fun manageMotionToastText(text: String)

    class Base(
        private val loginEmailCommunication: LoginEmailCommunication,
        private val loginPasswordCommunication: LoginPasswordCommunication,
        private val loginMotionToastCommunication: LoginMotionToastCommunication
    ): LoginCommunication {
        override fun manageEmailError(inputTextState: InputTextState) {
            loginEmailCommunication.map(inputTextState)
        }

        override fun managePasswordError(inputTextState: InputTextState) {
            loginPasswordCommunication.map(inputTextState)
        }

        override fun manageMotionToastText(text: String) {
            loginMotionToastCommunication.map(text)
        }

        override fun observeLoginEmailError(owner: LifecycleOwner, observer: Observer<InputTextState>) {
            loginEmailCommunication.observe(owner, observer)
        }

        override fun observeLoginPasswordError(owner: LifecycleOwner, observer: Observer<InputTextState>) {
            loginPasswordCommunication.observe(owner, observer)
        }

        override fun observeLoginMotionToastText(owner: LifecycleOwner, observer: Observer<String>) {
            loginMotionToastCommunication.observe(owner, observer)
        }
    }
}

interface ObserveLoginErrors {

    fun observeLoginEmailError(owner: LifecycleOwner, observer: Observer<InputTextState>)

    fun observeLoginPasswordError(owner: LifecycleOwner, observer: Observer<InputTextState>)

    fun observeLoginMotionToastText(owner: LifecycleOwner, observer: Observer<String>)
}

interface LoginEmailCommunication: Communication.Mutable<InputTextState> {
    class Base: Communication.Post<InputTextState>(), LoginEmailCommunication
}

interface LoginPasswordCommunication: Communication.Mutable<InputTextState> {
    class Base: Communication.Post<InputTextState>(), LoginPasswordCommunication
}

interface LoginMotionToastCommunication: Communication.Mutable<String> {
    class Base: Communication.SinglePost<String>(), LoginMotionToastCommunication
}