package com.main.register.data.validation

import com.main.core.Resource
import com.main.core.exception.EmailException
import com.main.core.exception.PasswordException
import com.main.register.data.entities.RegisterData
import com.main.register.data.exception.message.RegisterExceptionMessages.EMAIL_IS_EMPTY
import com.main.register.data.exception.message.RegisterExceptionMessages.PASSWORD_DOES_NOT_CONSIST_A_CAPITAL_LETTER
import com.main.register.data.exception.message.RegisterExceptionMessages.PASSWORD_IS_EMPTY
import com.main.register.data.exception.message.RegisterExceptionMessages.PASSWORD_IS_TOO_SHORT

interface ValidateRegisterData {

    fun valid(registerData: RegisterData): Resource<Boolean>

    class Base : ValidateRegisterData {

        override fun valid(registerData: RegisterData): Resource<Boolean> {
            if (registerData.email.isEmpty()) {
                return Resource.Error(false, EmailException(EMAIL_IS_EMPTY))
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
            return Resource.Success(true)
        }
    }
}