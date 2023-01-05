package com.main.register.presentation.communication

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.main.core.communication.Communication
import com.main.core.state.InputTextState

interface RegisterCommunication: ObserveRegisterErrors {

    fun manageEmailError(inputTextState: InputTextState)

    fun managePasswordError(inputTextState: InputTextState)

    fun manageConfirmPasswordError(inputTextState: InputTextState)

    fun manageFirstNameError(inputTextState: InputTextState)

    fun manageLastNameError(inputTextState: InputTextState)

    class Base(
        private val registerEmailCommunication: RegisterEmailCommunication,
        private val registerPasswordCommunication: RegisterPasswordCommunication,
        private val registerConfirmPasswordCommunication: RegisterConfirmPasswordCommunication,
        private val registerFirstNameCommunication: RegisterFirstNameCommunication,
        private val registerLastNameCommunication: RegisterLastNameCommunication,
    ): RegisterCommunication {
        override fun manageEmailError(inputTextState: InputTextState) {
            registerEmailCommunication.map(inputTextState)
        }

        override fun managePasswordError(inputTextState: InputTextState) {
            registerPasswordCommunication.map(inputTextState)
        }

        override fun manageConfirmPasswordError(inputTextState: InputTextState) {
            registerConfirmPasswordCommunication.map(inputTextState)
        }

        override fun manageFirstNameError(inputTextState: InputTextState) {
            registerFirstNameCommunication.map(inputTextState)
        }

        override fun manageLastNameError(inputTextState: InputTextState) {
            registerLastNameCommunication.map(inputTextState)
        }

        override fun observeLoginEmailError(owner: LifecycleOwner, observer: Observer<InputTextState>) {
            registerEmailCommunication.observe(owner, observer)
        }

        override fun observeLoginPasswordError(owner: LifecycleOwner, observer: Observer<InputTextState>) {
            registerPasswordCommunication.observe(owner, observer)
        }

        override fun observeLoginConfirmPasswordError(owner: LifecycleOwner, observer: Observer<InputTextState>) {
            registerConfirmPasswordCommunication.observe(owner, observer)
        }

        override fun observeLoginFirstNameError(owner: LifecycleOwner, observer: Observer<InputTextState>) {
            registerFirstNameCommunication.observe(owner, observer)
        }

        override fun observeLoginLastNameError(owner: LifecycleOwner, observer: Observer<InputTextState>) {
            registerLastNameCommunication.observe(owner, observer)
        }
    }
}

interface ObserveRegisterErrors {

    fun observeLoginEmailError(owner: LifecycleOwner, observer: Observer<InputTextState>)

    fun observeLoginPasswordError(owner: LifecycleOwner, observer: Observer<InputTextState>)

    fun observeLoginConfirmPasswordError(owner: LifecycleOwner, observer: Observer<InputTextState>)

    fun observeLoginFirstNameError(owner: LifecycleOwner, observer: Observer<InputTextState>)

    fun observeLoginLastNameError(owner: LifecycleOwner, observer: Observer<InputTextState>)
}

interface RegisterEmailCommunication: Communication.Mutable<InputTextState> {
    class Base: Communication.Post<InputTextState>(), RegisterEmailCommunication
}

interface RegisterPasswordCommunication: Communication.Mutable<InputTextState> {
    class Base: Communication.Post<InputTextState>(), RegisterPasswordCommunication
}

interface RegisterConfirmPasswordCommunication: Communication.Mutable<InputTextState> {
    class Base: Communication.Post<InputTextState>(), RegisterConfirmPasswordCommunication
}

interface RegisterFirstNameCommunication: Communication.Mutable<InputTextState> {
    class Base: Communication.Post<InputTextState>(), RegisterFirstNameCommunication
}

interface RegisterLastNameCommunication: Communication.Mutable<InputTextState> {
    class Base: Communication.Post<InputTextState>(), RegisterLastNameCommunication
}