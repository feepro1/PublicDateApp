package com.main.register.data.database

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.main.core.Resource
import com.main.core.exception.EmailException
import com.main.register.data.entities.RegisterData
import com.main.register.data.exception.message.RegisterExceptionMessages.EMAIL_ADDRESS_IS_INCORRECT
import com.main.register.data.exception.message.RegisterExceptionMessages.EMAIL_ADDRESS_IS_INCORRECT_UI
import kotlinx.coroutines.tasks.await

interface FirebaseUserStorageRepository {

    suspend fun addUser(registerData: RegisterData): Resource<Boolean>

    class Base(
        private val firebase: Firebase
    ) : FirebaseUserStorageRepository {

        override suspend fun addUser(registerData: RegisterData): Resource<Boolean> {
            val uid = firebase.auth.currentUser?.uid.toString()
            val task = firebase.firestore.collection(COLLECTION_USERS).document(uid).set(
                registerData.mapToRegisterDataForDatabase()
            )
            return try {
                task.await()
                Resource.Success(true)
            } catch (e: Exception) {
                when (e.message.toString()) {
                    EMAIL_ADDRESS_IS_INCORRECT -> {
                        Resource.Error(false, EmailException(EMAIL_ADDRESS_IS_INCORRECT_UI))
                    }
                    else -> Resource.Success(true)
                }
            }
        }
    }

    companion object {
        const val COLLECTION_USERS = "users"
    }
}