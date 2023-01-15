package com.main.dating.domain.exception

import com.main.core.Resource
import com.main.core.exception.NetworkException
import com.main.dating.data.entities.User
import com.main.dating.data.exception.message.DatingExceptionMessages.INTERNET_IS_UNAVAILABLE

interface DatingHandleException {

    fun handle(exception: Exception): Resource<List<User>>

    class Base : DatingHandleException {
        override fun handle(exception: Exception): Resource<List<User>> {
            if (exception is NetworkException) {
                return Resource.Error(mutableListOf(), NetworkException(INTERNET_IS_UNAVAILABLE))
            }
            return Resource.Success(mutableListOf())
        }
    }
}