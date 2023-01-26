package com.main.login.data.validation

import com.main.core.Resource
import com.main.core.exception.EmailException
import com.main.core.exception.ExceptionMessages.EMAIL_IS_EMPTY
import com.main.core.exception.ExceptionMessages.PASSWORD_DOES_NOT_CONSIST_A_CAPITAL_LETTER
import com.main.core.exception.ExceptionMessages.PASSWORD_IS_EMPTY
import com.main.core.exception.ExceptionMessages.PASSWORD_IS_TOO_SHORT
import com.main.core.exception.PasswordException
import com.main.login.data.entities.LoginData

interface ValidateLoginData {

    fun valid(loginData: LoginData): Resource<Boolean>

    class Base : ValidateLoginData {

        override fun valid(loginData: LoginData): Resource<Boolean> {
            if (loginData.email.isEmpty()) {
                return Resource.Error(false, EmailException(EMAIL_IS_EMPTY))
            }
            if (loginData.password.isEmpty()) {
                return Resource.Error(false, PasswordException(PASSWORD_IS_EMPTY))
            }
            if (loginData.password.length < 5) {
                return Resource.Error(false, PasswordException(PASSWORD_IS_TOO_SHORT))
            }
            if (loginData.password == loginData.password.lowercase()) {
                return Resource.Error(false, PasswordException(PASSWORD_DOES_NOT_CONSIST_A_CAPITAL_LETTER))
            }
            return Resource.Success(true)
        }
    }
}