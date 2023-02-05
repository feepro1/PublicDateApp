package com.main.login.domain.exception

import com.main.core.Resource
import com.main.core.exception.EmailException
import com.main.core.exception.ExceptionMessages.EMAIL_ADDRESS_IS_INCORRECT
import com.main.core.exception.ExceptionMessages.EMAIL_ADDRESS_IS_INCORRECT_UI
import com.main.core.exception.ExceptionMessages.EMAIL_WAS_NOT_FOUND
import com.main.core.exception.ExceptionMessages.EMAIL_WAS_NOT_FOUND_UI
import com.main.core.exception.ExceptionMessages.PASSWORD_IS_INCORRECT
import com.main.core.exception.ExceptionMessages.PASSWORD_IS_INCORRECT_UI
import com.main.core.exception.PasswordException

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