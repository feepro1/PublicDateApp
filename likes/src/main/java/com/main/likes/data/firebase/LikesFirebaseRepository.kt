package com.main.likes.data.firebase

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.main.core.Resource
import com.main.core.entities.User
import com.main.core.exception.ExceptionMessages.INTERNET_IS_UNAVAILABLE
import com.main.core.exception.NetworkException
import com.main.core.firebase.FirebaseConstants.REFERENCE_USERS
import kotlinx.coroutines.tasks.await

interface LikesFirebaseRepository {

    suspend fun getAllLikes(): Resource<User>

    class Base : LikesFirebaseRepository {

        override suspend fun getAllLikes(): Resource<User> {
            val uid = Firebase.auth.currentUser?.uid.toString()
            val task = Firebase.firestore.collection(REFERENCE_USERS).document(uid).get()
            return try {
                task.await()
                val result = task.result.toObject(User::class.java)
                Resource.Success(result)
            } catch (e: Exception) {
                Resource.Error(User(), NetworkException(INTERNET_IS_UNAVAILABLE))
            }
        }
    }

}