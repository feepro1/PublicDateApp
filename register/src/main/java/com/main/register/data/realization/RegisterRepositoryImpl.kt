package com.main.register.data.realization

import com.main.core.Resource
import com.main.register.data.entities.RegisterData
import com.main.register.domain.repository.RegisterRepository

class RegisterRepositoryImpl : RegisterRepository {

    override suspend fun register(registerData: RegisterData): Resource<Boolean> {
        TODO("Not yet implemented")
    }
}