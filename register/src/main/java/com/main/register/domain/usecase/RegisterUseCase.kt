package com.main.register.domain.usecase

import com.main.core.Resource
import com.main.register.data.entities.RegisterData
import com.main.register.domain.repository.RegisterRepository

class RegisterUseCase(
    private val registerRepository: RegisterRepository
) {

    suspend fun execute(registerData: RegisterData): Resource<Boolean> {
        return registerRepository.register(registerData)
    }
}