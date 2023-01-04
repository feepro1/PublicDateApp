package com.main.login.domain.firebase

import com.google.firebase.auth.FirebaseAuth
import com.main.core.Resource
import com.main.login.data.entities.LoginData
import com.main.login.domain.exception.HandleFirebaseLoginException
import kotlinx.coroutines.tasks.await

interface LoginFirebaseRepository {

    suspend fun login(loginData: LoginData): Resource<Boolean>

    class Base(
        private val firebaseAuth: FirebaseAuth,
        private val handleFirebaseLoginException: HandleFirebaseLoginException
    ): LoginFirebaseRepository {

        override suspend fun login(loginData: LoginData): Resource<Boolean> {
            val task = firebaseAuth.signInWithEmailAndPassword(loginData.email, loginData.password)
            return try {
                task.await()
                Resource.Success(true)
            } catch (e: Exception) {
                handleFirebaseLoginException.handle(task.exception ?: Exception())
            }
        }
    }
}