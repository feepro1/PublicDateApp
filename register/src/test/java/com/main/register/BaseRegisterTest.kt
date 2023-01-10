package com.main.register

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.main.core.dispatchers.DispatchersList
import com.main.core.state.InputTextState
import com.main.register.data.entities.RegisterData
import com.main.register.presentation.communication.RegisterCommunication
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher

abstract class BaseRegisterTest {

    protected class TestRegisterCommunication : RegisterCommunication {

        var passwordError = mutableListOf<InputTextState.ShowError>()
        var emailError = mutableListOf<InputTextState.ShowError>()
        var confirmPasswordError = mutableListOf<InputTextState.ShowError>()
        var firstNameError = mutableListOf<InputTextState.ShowError>()
        var lastNameError = mutableListOf<InputTextState.ShowError>()
        var registerData = mutableListOf<RegisterData>()

        override fun manageEmailError(inputTextState: InputTextState) {
            if (inputTextState is InputTextState.ShowError) {
                emailError.add(inputTextState)
            }
        }

        override fun managePasswordError(inputTextState: InputTextState) {
            if (inputTextState is InputTextState.ShowError) {
                passwordError.add(inputTextState)
            }
        }

        override fun manageConfirmPasswordError(inputTextState: InputTextState) {
            if (inputTextState is InputTextState.ShowError) {
                confirmPasswordError.add(inputTextState)
            }
        }

        override fun manageFirstNameError(inputTextState: InputTextState) {
            if (inputTextState is InputTextState.ShowError) {
                firstNameError.add(inputTextState)
            }
        }

        override fun manageLastNameError(inputTextState: InputTextState) {
            if (inputTextState is InputTextState.ShowError) {
                lastNameError.add(inputTextState)
            }
        }

        override fun manageRegisterData(registerData: RegisterData) {
            this.registerData.add(registerData)
        }

        override fun observeRegisterEmailError(owner: LifecycleOwner, observer: Observer<InputTextState>) = Unit

        override fun observeRegisterPasswordError(owner: LifecycleOwner, observer: Observer<InputTextState>) = Unit

        override fun observeRegisterConfirmPasswordError(owner: LifecycleOwner, observer: Observer<InputTextState>) = Unit

        override fun observeRegisterFirstNameError(owner: LifecycleOwner, observer: Observer<InputTextState>) = Unit

        override fun observeRegisterLastNameError(owner: LifecycleOwner, observer: Observer<InputTextState>) = Unit

        override fun valueRegisterData(): RegisterData? {
           return registerData.first()
        }
    }

    protected class TestDispatchersList(
        private val dispatcher: CoroutineDispatcher = UnconfinedTestDispatcher()
    ) : DispatchersList {

        override fun io(): CoroutineDispatcher = dispatcher
        override fun ui(): CoroutineDispatcher = dispatcher
    }
}