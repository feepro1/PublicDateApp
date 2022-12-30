package com.main.core.state

import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

sealed class InputTextState {

    abstract fun apply(textInputEditText: TextInputEditText, textInputLayout: TextInputLayout)

    abstract class AbstractError(
        private val errorMessage: String,
        private val errorEnabled: Boolean
    ): InputTextState() {
        override fun apply(textInputEditText: TextInputEditText, textInputLayout: TextInputLayout) {
            textInputLayout.error = errorMessage
            textInputLayout.isErrorEnabled = errorEnabled
        }
    }

    class ShowError(errorMessage: String): AbstractError(errorMessage, true)
    class ClearError: AbstractError("", false)
}