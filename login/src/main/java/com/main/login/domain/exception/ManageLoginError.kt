package com.main.login.domain.exception

import com.main.core.exception.ApplicationException

interface ManageLoginError {

    fun clearEmailError()

    fun clearUsernameError()

    fun clearPasswordError()

    fun showEmailMessage(exception: ApplicationException)

    fun showUsernameMessage(exception: ApplicationException)

    fun showPasswordMessage(exception: ApplicationException)
}