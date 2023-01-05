package com.main.register.domain.firebase

import com.google.firebase.auth.FirebaseAuth
import com.main.core.Resource
import com.main.register.data.entities.RegisterData
import com.main.register.domain.exception.HandleFirebaseRegisterException
import kotlinx.coroutines.tasks.await

interface RegisterFirebaseRepository {

    suspend fun register(registerData: RegisterData): Resource<Boolean>

    class Base(
        private val firebaseAuth: FirebaseAuth,
        private val handleFirebaseRegisterException: HandleFirebaseRegisterException
    ): RegisterFirebaseRepository {

        override suspend fun register(registerData: RegisterData): Resource<Boolean> {
            //todo make register logic
            val task = firebaseAuth.createUserWithEmailAndPassword(registerData.email, registerData.password)
            return try {
                task.await()
                Resource.Success(true)
            } catch (e: Exception) {
                handleFirebaseRegisterException.handle(task.exception ?: Exception())
            }
        }
    }
}