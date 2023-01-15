package com.main.dating.data.realization.firebase.database

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.main.core.FirebaseConstants.COLLECTION_USERS
import com.main.core.Resource
import com.main.core.exception.NetworkException
import com.main.dating.data.entities.User
import com.main.dating.data.exception.message.DatingExceptionMessages
import com.main.dating.data.exception.message.DatingExceptionMessages.INTERNET_IS_UNAVAILABLE
import com.main.dating.domain.exception.DatingHandleException
import com.main.dating.domain.firebase.database.DatabaseRepository
import kotlinx.coroutines.tasks.await

class DatabaseRepositoryImpl(
    private val datingHandleException: DatingHandleException
) : DatabaseRepository {

    override suspend fun getUsers(): Resource<List<User>> {
        val task = Firebase.firestore.collection(COLLECTION_USERS).get()
        return try {
            task.await()
            val users = mutableListOf<User>()
            task.result.forEach {
                users.add(it.toObject())
            }
            Resource.Success(users)
        } catch (exception: Exception) {
            datingHandleException.handle(exception)
        }
    }
}