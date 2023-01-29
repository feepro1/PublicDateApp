package com.main.chats.data.storage.remote

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.main.core.base.entity.Chat
import com.main.core.entities.User
import com.main.core.firebase.FirebaseConstants.REFERENCE_USERS
import kotlinx.coroutines.tasks.await

interface ChatsFirebaseRepository {

    suspend fun getListUsers(): List<Chat>

    class Base : ChatsFirebaseRepository {

        override suspend fun getListUsers(): List<Chat> {
            val uid = Firebase.auth.currentUser?.uid.toString()
            val result = Firebase.firestore.collection(REFERENCE_USERS).document(uid).get()
            result.await()
            return result.result.toObject(User::class.java)?.userChats?.values?.toList() ?: emptyList()
        }
    }
}