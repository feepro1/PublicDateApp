package com.main.login.domain.repository

import com.main.core.Resource
import com.main.login.data.entities.LoginData

interface LoginRepository {

    suspend fun login(loginData: LoginData): Resource<Boolean>
}