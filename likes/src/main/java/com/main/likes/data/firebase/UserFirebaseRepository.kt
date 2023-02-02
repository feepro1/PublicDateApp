package com.main.likes.data.firebase

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.main.core.Resource
import com.main.core.entities.User
import com.main.core.exception.ExceptionMessages.INTERNET_IS_UNAVAILABLE
import com.main.core.exception.NetworkException
import com.main.core.firebase.FirebaseConstants.REFERENCE_USERS
import kotlinx.coroutines.tasks.await

interface UserFirebaseRepository {

    suspend fun getCurrentUser(uid: String): Resource<User>

    class Base : UserFirebaseRepository {
        override suspend fun getCurrentUser(uid: String): Resource<User> {
            val task = Firebase.firestore.collection(REFERENCE_USERS).document(uid).get()
            task.await()
            return if (task.isSuccessful) {
                Resource.Success(task.result.toObject(User::class.java))
            } else {
                Resource.Error(null, NetworkException(INTERNET_IS_UNAVAILABLE))
            }
        }
    }
}