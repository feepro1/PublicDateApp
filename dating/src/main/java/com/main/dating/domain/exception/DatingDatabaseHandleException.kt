package com.main.dating.domain.exception

import com.main.core.Resource
import com.main.core.exception.NetworkException
import com.main.dating.data.entities.User
import com.main.dating.data.exception.message.DatingExceptionMessages.INTERNET_IS_UNAVAILABLE

interface DatingDatabaseHandleException {

    fun <T> handle(exception: Exception): Resource<T>

    class Base : DatingDatabaseHandleException {
        @Suppress("UNCHECKED_CAST")
        override fun <T> handle(exception: Exception): Resource<T> {
            val list = emptyList<User>() as T
            if (exception is NetworkException) {
                return Resource.Error(list, NetworkException(INTERNET_IS_UNAVAILABLE))
            }
            return Resource.Success(list)
        }
    }
}