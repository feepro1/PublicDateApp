package com.main.login.data.realization

import com.main.core.Resource
import com.main.core.exception.EmailException
import com.main.login.data.entities.LoginData
import com.main.login.data.validation.ValidateLoginData
import com.main.login.domain.firebase.LoginFirebaseRepository
import com.main.login.domain.repository.LoginRepository
import javax.security.auth.login.LoginException
import kotlin.math.log

class LoginRepositoryImpl(
    val loginFirebaseRepository: LoginFirebaseRepository,
    val validateLoginData: ValidateLoginData
) : LoginRepository {

    override suspend fun login(loginData: LoginData): Resource<Boolean> {
        val validResult = validateLoginData.valid(loginData)
        if (validResult.data == false) {
            return validResult
        }
        return loginFirebaseRepository.login(loginData)
    }
}