package com.main.dating.domain.exception

import com.main.core.Resource
import com.main.core.entities.User
import com.main.core.exception.ExceptionMessages.INTERNET_IS_UNAVAILABLE
import com.main.core.exception.NetworkException

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