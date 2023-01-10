package com.main.login.domain.usecase

import com.main.core.Resource
import com.main.login.data.entities.LoginData
import com.main.login.domain.repository.LoginRepository

class LoginUseCase(
    private val loginRepository: LoginRepository
) {

    suspend fun execute(loginData: LoginData): Resource<Boolean> {
        return loginRepository.login(loginData)
    }
}