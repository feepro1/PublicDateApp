package com.main.chats.data.storage.remote

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import com.main.chats.data.entities.Chat
import com.main.chats.data.storage.local.ChatCacheModel
import kotlinx.coroutines.tasks.await

interface ChatsFirebaseRepository {

    suspend fun getListUsers(list: List<ChatCacheModel>): List<Chat>

    class Base : ChatsFirebaseRepository {

        override suspend fun getListUsers(list: List<ChatCacheModel>): List<Chat> {
            val result = Firebase.firestore.collection(COLLECTION_USERS).get()
            result.await()
            return result.result.toObjects(Chat::class.java)
        }
    }
    companion object {
        const val COLLECTION_USERS = "users"
    }
}