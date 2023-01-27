package com.main.likes.data.realization

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.main.core.Resource
import com.main.core.entities.Like
import com.main.core.firebase.FirebaseConstants
import com.main.likes.data.entities.LikeFromUser
import com.main.likes.data.entities.User
import com.main.likes.data.firebase.LikesFirebaseRepository
import com.main.likes.domain.firebase.LikesRepository
import kotlinx.coroutines.tasks.await

class LikesRepositoryImpl(
    private val likesFirebaseRepository: LikesFirebaseRepository
) : LikesRepository {

    override suspend fun getAllLikes(): Resource<LikeFromUser> {
        return likesFirebaseRepository.getAllLikes()
    }

    override suspend fun likeUser(user: User): Resource<Boolean> {
        val uid = Firebase.auth.currentUser?.uid.toString()
        user.likeFromAnotherUser[uid] = Like(
            firstName = user.firstName, lastName = user.lastName, age = user.age ?: 0,
            city = user.city, avatarUrl = user.avatarUrl, uid = user.uid
        )
        val task = Firebase.firestore.collection(FirebaseConstants.REFERENCE_USERS).document(user.uid).set(user)
        task.await()
        return Resource.Success(task.isSuccessful)
    }
}