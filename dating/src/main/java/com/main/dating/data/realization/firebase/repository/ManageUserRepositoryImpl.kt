package com.main.dating.data.realization.firebase.repository

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.main.core.Resource
import com.main.core.entities.Like
import com.main.core.firebase.FirebaseConstants.REFERENCE_USERS
import com.main.dating.data.entities.User
import com.main.dating.domain.exception.DatingDatabaseHandleException
import com.main.dating.domain.firebase.repository.ManageUserRepository
import kotlinx.coroutines.tasks.await

class ManageUserRepositoryImpl(
    private val datingDatabaseHandleException: DatingDatabaseHandleException
) : ManageUserRepository {

    override suspend fun likeUser(user: User): Resource<Boolean> {
        val uid = Firebase.auth.currentUser?.uid.toString()
        val myProfileTask = Firebase.firestore.collection(REFERENCE_USERS).document(uid).get()
        myProfileTask.await()
        val result = myProfileTask.result.toObject(User::class.java)
        user.likeFromAnotherUser[uid] = Like(
            firstName = result?.firstName.toString(), lastName = result?.lastName.toString(),
            age = result?.age ?: 0, city = result?.city.toString(),
            avatarUrl = result?.avatarUrl.toString(), uid = result?.uid.toString()
        )
        val task = Firebase.firestore.collection(REFERENCE_USERS).document(user.uid).set(user)
        return try {
            task.await()
            Resource.Success(true)
        } catch (exception: Exception) {
            return datingDatabaseHandleException.handle(exception)
        }
    }

    override suspend fun dislikeUser(user: User): Resource<Boolean> {
        return Resource.Success(true)
    }
}