package com.main.register.presentation.communication

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.main.core.communication.Communication
import com.main.core.state.InputTextState
import com.main.register.data.entities.RegisterData

interface RegisterCommunication : ObserveRegisterCommunications, ValueRegisterCommunications<RegisterData> {

    fun manageEmailError(inputTextState: InputTextState)

    fun managePasswordError(inputTextState: InputTextState)

    fun manageConfirmPasswordError(inputTextState: InputTextState)

    fun manageFirstNameError(inputTextState: InputTextState)

    fun manageLastNameError(inputTextState: InputTextState)

    fun manageRegisterData(registerData: RegisterData)

    class Base(
        private val registerEmailCommunication: RegisterEmailCommunication,
        private val registerPasswordCommunication: RegisterPasswordCommunication,
        private val registerConfirmPasswordCommunication: RegisterConfirmPasswordCommunication,
        private val registerFirstNameCommunication: RegisterFirstNameCommunication,
        private val registerLastNameCommunication: RegisterLastNameCommunication,
        private val registerRegisterDataCommunication: RegisterRegisterDataCommunication
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

        override fun manageRegisterData(registerData: RegisterData) {
            registerRegisterDataCommunication.map(registerData)
        }

        override fun observeRegisterEmailError(owner: LifecycleOwner, observer: Observer<InputTextState>) {
            registerEmailCommunication.observe(owner, observer)
        }

        override fun observeRegisterPasswordError(owner: LifecycleOwner, observer: Observer<InputTextState>) {
            registerPasswordCommunication.observe(owner, observer)
        }

        override fun observeRegisterConfirmPasswordError(owner: LifecycleOwner, observer: Observer<InputTextState>) {
            registerConfirmPasswordCommunication.observe(owner, observer)
        }

        override fun observeRegisterFirstNameError(owner: LifecycleOwner, observer: Observer<InputTextState>) {
            registerFirstNameCommunication.observe(owner, observer)
        }

        override fun observeRegisterLastNameError(owner: LifecycleOwner, observer: Observer<InputTextState>) {
            registerLastNameCommunication.observe(owner, observer)
        }

        override fun valueRegisterData(): RegisterData? {
            return registerRegisterDataCommunication.value()
        }


    }
}

interface ObserveRegisterCommunications {

    fun observeRegisterEmailError(owner: LifecycleOwner, observer: Observer<InputTextState>)

    fun observeRegisterPasswordError(owner: LifecycleOwner, observer: Observer<InputTextState>)

    fun observeRegisterConfirmPasswordError(owner: LifecycleOwner, observer: Observer<InputTextState>)

    fun observeRegisterFirstNameError(owner: LifecycleOwner, observer: Observer<InputTextState>)

    fun observeRegisterLastNameError(owner: LifecycleOwner, observer: Observer<InputTextState>)
}

interface ValueRegisterCommunications<T> {
    fun valueRegisterData(): T?
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

interface RegisterRegisterDataCommunication: Communication.Mutable<RegisterData> {
    class Base: Communication.Post<RegisterData>(), RegisterRegisterDataCommunication
}