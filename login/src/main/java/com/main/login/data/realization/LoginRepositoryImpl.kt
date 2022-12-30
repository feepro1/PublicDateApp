package com.main.login.data.realization

import com.main.core.Resource
import com.main.login.data.entities.LoginData
import com.main.login.domain.repository.LoginRepository

class LoginRepositoryImpl : LoginRepository {
    override suspend fun login(loginData: LoginData): Resource<Boolean> {
        TODO("Not yet implemented")
    }
}