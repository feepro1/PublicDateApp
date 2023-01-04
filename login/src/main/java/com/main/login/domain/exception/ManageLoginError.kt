package com.main.login.domain.exception

import com.main.core.exception.ApplicationException

interface ManageLoginError {

    fun clearEmailError()

    fun clearPasswordError()

    fun showEmailMessage(exception: ApplicationException)

    fun showPasswordMessage(exception: ApplicationException)
}