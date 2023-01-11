package com.main.register.data.realization

import com.main.core.Resource
import com.main.register.data.entities.RegisterData
import com.main.register.data.validation.ValidateFinishRegisterData
import com.main.register.domain.firebase.RegisterFirebaseRepository
import com.main.register.domain.repository.RegisterRepository

class RegisterRepositoryImpl(
    val registerFirebaseRepository: RegisterFirebaseRepository,
    val validateFinishRegisterData: ValidateFinishRegisterData
) : RegisterRepository {

    override suspend fun register(registerData: RegisterData): Resource<Boolean> {
        val validResult = validateFinishRegisterData.valid(registerData)
        if (validResult.data == false) return validResult
        return registerFirebaseRepository.register(registerData)
    }
}