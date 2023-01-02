package com.main.login.data.realization

import com.main.core.Resource
import com.main.core.exception.EmailException
import com.main.login.data.entities.LoginData
import com.main.login.domain.repository.LoginRepository
import javax.security.auth.login.LoginException

class LoginRepositoryImpl : LoginRepository {
    override suspend fun login(loginData: LoginData): Resource<Boolean> {
        return Resource.Error(false, EmailException("Username is too short"))
    }
}