package com.main.register.domain.firebase

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.main.core.Resource
import com.main.register.data.database.FirebaseUserStorageRepository
import com.main.register.data.entities.RegisterData
import com.main.register.domain.exception.HandleFirebaseRegisterException
import kotlinx.coroutines.tasks.await

interface RegisterFirebaseRepository {

    suspend fun register(registerData: RegisterData): Resource<Boolean>

    class Base(
        private val firebase: Firebase,
        private val handleFirebaseRegisterException: HandleFirebaseRegisterException,
        private val firebaseUserStorageRepository: FirebaseUserStorageRepository
    ): RegisterFirebaseRepository {

        override suspend fun register(registerData: RegisterData): Resource<Boolean> {
            //todo make register logic
            val task = firebase.auth.createUserWithEmailAndPassword(registerData.email, registerData.password)
            return try {
                task.await()
                firebase.auth.currentUser?.sendEmailVerification()
                firebaseUserStorageRepository.addUser(registerData)
            } catch (e: Exception) {
                handleFirebaseRegisterException.handle(task.exception ?: Exception())
            }
        }
    }
}