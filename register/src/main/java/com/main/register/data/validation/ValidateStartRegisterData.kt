package com.main.register.data.validation

import com.main.core.Resource
import com.main.core.exception.ConfirmPasswordException
import com.main.core.exception.EmailException
import com.main.core.exception.PasswordException
import com.main.register.data.entities.RegisterData
import com.main.register.data.exception.message.RegisterExceptionMessages.CONFIRM_PASSWORD_IS_EMPTY
import com.main.register.data.exception.message.RegisterExceptionMessages.EMAIL_ADDRESS_IS_INCORRECT_UI
import com.main.register.data.exception.message.RegisterExceptionMessages.EMAIL_IS_EMPTY
import com.main.register.data.exception.message.RegisterExceptionMessages.PASSWORDS_DO_NOT_MATCH
import com.main.register.data.exception.message.RegisterExceptionMessages.PASSWORD_DOES_NOT_CONSIST_A_CAPITAL_LETTER
import com.main.register.data.exception.message.RegisterExceptionMessages.PASSWORD_IS_EMPTY
import com.main.register.data.exception.message.RegisterExceptionMessages.PASSWORD_IS_TOO_SHORT

interface ValidateStartRegisterData {

    fun valid(registerData: RegisterData): Resource<Boolean>

    class Base : ValidateStartRegisterData {

        override fun valid(registerData: RegisterData): Resource<Boolean> {
            if (registerData.email.isEmpty()) {
                return Resource.Error(false, EmailException(EMAIL_IS_EMPTY))
            }
            if (!registerData.email.contains('@')) {
                return Resource.Error(false, EmailException(EMAIL_ADDRESS_IS_INCORRECT_UI))
            }
            if (registerData.password.isEmpty()) {
                return Resource.Error(false, PasswordException(PASSWORD_IS_EMPTY))
            }
            if (registerData.password.length < 5) {
                return Resource.Error(false, PasswordException(PASSWORD_IS_TOO_SHORT))
            }
            if (registerData.password == registerData.password.lowercase()) {
                return Resource.Error(false, PasswordException(PASSWORD_DOES_NOT_CONSIST_A_CAPITAL_LETTER))
            }
            if (registerData.confirmPassword.isEmpty()) {
                return Resource.Error(false, ConfirmPasswordException(CONFIRM_PASSWORD_IS_EMPTY))
            }
            if (registerData.password != registerData.confirmPassword)  {
                return Resource.Error(false, ConfirmPasswordException(PASSWORDS_DO_NOT_MATCH))
            }
            return Resource.Success(true)
        }
    }
}