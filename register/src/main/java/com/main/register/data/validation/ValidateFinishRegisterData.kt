package com.main.register.data.validation

import com.main.core.Resource
import com.main.core.exception.EmailException
import com.main.core.exception.ExceptionMessages.EMAIL_IS_EMPTY
import com.main.core.exception.ExceptionMessages.PASSWORD_IS_EMPTY
import com.main.core.exception.PasswordException
import com.main.register.data.entities.RegisterData

interface ValidateFinishRegisterData {

    fun valid(registerData: RegisterData): Resource<Boolean>

    class Base : ValidateFinishRegisterData {

        override fun valid(registerData: RegisterData): Resource<Boolean> {
            if (registerData.firstName.isEmpty()) {
                return Resource.Error(false, EmailException(EMAIL_IS_EMPTY))
            }
            if (registerData.lastName.isEmpty()) {
                return Resource.Error(false, PasswordException(PASSWORD_IS_EMPTY))
            }
            return Resource.Success(true)
        }
    }
}