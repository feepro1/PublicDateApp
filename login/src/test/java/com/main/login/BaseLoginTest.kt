package com.main.login

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.main.core.DispatchersList
import com.main.core.state.InputTextState
import com.main.login.presentation.communication.LoginCommunication
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher

abstract class BaseLoginTest {

    protected class TestLoginCommunication : LoginCommunication {

        var passwordError = mutableListOf<InputTextState.ShowError>()
        var emailError = mutableListOf<InputTextState.ShowError>()
        val motionToastText = mutableListOf<String>()

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

        override fun manageMotionToastText(text: String) {
            motionToastText.add(text)
        }

        override fun observeLoginEmailError(owner: LifecycleOwner, observer: Observer<InputTextState>) = Unit

        override fun observeLoginPasswordError(owner: LifecycleOwner, observer: Observer<InputTextState>) = Unit
        override fun observeLoginMotionToastText(owner: LifecycleOwner, observer: Observer<String>) = Unit
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    protected class TestDispatchersList(
        private val dispatcher: CoroutineDispatcher = UnconfinedTestDispatcher()
    ) : DispatchersList {

        override fun io(): CoroutineDispatcher = dispatcher
        override fun ui(): CoroutineDispatcher = dispatcher
    }
}