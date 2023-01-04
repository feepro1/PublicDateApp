package com.main.login.domain.exception

import com.main.core.Resource
import com.main.core.exception.EmailException
import com.main.core.exception.PasswordException
import com.main.login.data.exception.message.LoginExceptionMessages.EMAIL_ADDRESS_IS_INCORRECT
import com.main.login.data.exception.message.LoginExceptionMessages.EMAIL_ADDRESS_IS_INCORRECT_UI
import com.main.login.data.exception.message.LoginExceptionMessages.EMAIL_WAS_NOT_FOUND
import com.main.login.data.exception.message.LoginExceptionMessages.EMAIL_WAS_NOT_FOUND_UI
import com.main.login.data.exception.message.LoginExceptionMessages.PASSWORD_IS_INCORRECT
import com.main.login.data.exception.message.LoginExceptionMessages.PASSWORD_IS_INCORRECT_UI

interface HandleFirebaseLoginException {

    fun handle(exception: Exception): Resource<Boolean>

    class Base : HandleFirebaseLoginException {

        override fun handle(exception: Exception): Resource<Boolean> {
            return when(exception.message.toString()) {
                EMAIL_ADDRESS_IS_INCORRECT -> {
                    Resource.Error(false, EmailException(EMAIL_ADDRESS_IS_INCORRECT_UI))
                }
                EMAIL_WAS_NOT_FOUND -> {
                    Resource.Error(false, EmailException(EMAIL_WAS_NOT_FOUND_UI))
                }
                PASSWORD_IS_INCORRECT -> {
                    Resource.Error(false, PasswordException(PASSWORD_IS_INCORRECT_UI))
                }
                else -> { Resource.Success(true) }
            }
        }
    }
}