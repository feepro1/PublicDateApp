package com.main.register.domain.repository

import com.main.core.Resource
import com.main.register.data.entities.RegisterData

interface RegisterRepository {

    suspend fun register(registerData: RegisterData): Resource<Boolean>
}