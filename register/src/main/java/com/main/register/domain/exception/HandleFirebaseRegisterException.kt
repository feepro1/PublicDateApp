package com.main.register.domain.exception

import com.main.core.Resource
import com.main.core.exception.EmailException
import com.main.register.data.exception.message.RegisterExceptionMessages.EMAIL_ADDRESS_IS_INCORRECT
import com.main.register.data.exception.message.RegisterExceptionMessages.EMAIL_ADDRESS_IS_INCORRECT_UI

interface HandleFirebaseRegisterException {

    fun handle(exception: Exception): Resource<Boolean>

    class Base : HandleFirebaseRegisterException {

        override fun handle(exception: Exception): Resource<Boolean> {
            return when(exception.message.toString()) {
                EMAIL_ADDRESS_IS_INCORRECT -> {
                    Resource.Error(false, EmailException(EMAIL_ADDRESS_IS_INCORRECT_UI))
                }
                else -> { Resource.Success(true) }
            }
        }
    }
}