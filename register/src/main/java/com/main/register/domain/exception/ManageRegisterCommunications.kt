package com.main.register.domain.exception

import com.main.core.exception.ApplicationException
import com.main.register.data.entities.RegisterData

interface ManageRegisterCommunications {

    fun clearEmailError()

    fun clearPasswordError()

    fun clearConfirmPasswordError()

    fun clearFirstNameError()

    fun clearLastNameError()

    fun showEmailMessage(exception: ApplicationException)

    fun showPasswordMessage(exception: ApplicationException)

    fun showConfirmPasswordMessage(exception: ApplicationException)

    fun showFirstNameMessage(exception: ApplicationException)

    fun showLastNameMessage(exception: ApplicationException)

    fun mapRegisterData(registerData: RegisterData)

    fun mapMotionToastText(text: String)
}