package com.main.likes.data.realization

import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.main.core.Resource
import com.main.core.entities.Like
import com.main.core.entities.User
import com.main.core.firebase.FirebaseConstants.REFERENCE_USERS
import com.main.likes.data.firebase.LikesFirebaseRepository
import com.main.likes.data.firebase.UserFirebaseRepository
import com.main.likes.domain.firebase.LikesRepository
import kotlinx.coroutines.tasks.await

class LikesRepositoryImpl(
    private val likesFirebaseRepository: LikesFirebaseRepository,
    private val userFirebaseRepository: UserFirebaseRepository
) : LikesRepository {

    override suspend fun getAllLikes(): Resource<User> {
        return likesFirebaseRepository.getAllLikes()
    }

    override suspend fun likeUser(user: User): Resource<Boolean> {
        val uid = Firebase.auth.currentUser?.uid.toString()
        val firstUser = userFirebaseRepository.getCurrentUser(uid).data
        user.likeFromAnotherUser[uid] = Like(
            firstName = firstUser?.firstName ?: "", lastName = firstUser?.lastName ?: "",
            age = firstUser?.age, city = firstUser?.city ?: "",
            avatarUrl = firstUser?.avatarUrl ?: "", uid = firstUser?.uid ?: ""
        )
        val task = Firebase.firestore.collection(REFERENCE_USERS).document(user.uid).set(user)
        task.await()

        firstUser?.likeFromAnotherUser?.get(user.uid)?.isMutualLike = true
        user.likeFromAnotherUser[uid]?.isMutualLike = true

        firstUser?.let { Firebase.firestore.collection(REFERENCE_USERS).document(uid).set(it) }
        Firebase.firestore.collection(REFERENCE_USERS).document(user.uid).set(user)

        return Resource.Success(task.isSuccessful)
    }
}