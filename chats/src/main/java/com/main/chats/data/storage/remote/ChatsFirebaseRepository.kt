package com.main.chats.data.storage.remote

import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.main.core.entities.Chat
import com.main.core.entities.User
import com.main.core.firebase.FirebaseConstants.REFERENCE_CHATS
import com.main.core.firebase.FirebaseConstants.REFERENCE_MESSENGERS
import com.main.core.firebase.FirebaseConstants.REFERENCE_USERS
import kotlinx.coroutines.tasks.await

interface ChatsFirebaseRepository {

    suspend fun getListUsers(): List<Chat>

    suspend fun deleteChat(chat: Chat): Boolean

    class Base : ChatsFirebaseRepository {

        override suspend fun getListUsers(): List<Chat> {
            val uid = Firebase.auth.currentUser?.uid.toString()
            val result = Firebase.firestore.collection(REFERENCE_USERS).document(uid).get()
            result.await()
            return result.result.toObject(User::class.java)?.userChats?.values?.toList() ?: emptyList()
        }

        override suspend fun deleteChat(chat: Chat): Boolean {
            //todo
            Log.d("MyLog", chat.toString())
            val uid = Firebase.auth.currentUser?.uid.toString()
            val task = Firebase.firestore.collection(REFERENCE_USERS).document(uid).get()
            task.await()
            val result = task.result.toObject(User::class.java)
            result?.userChats?.remove(chat.uid)
            Firebase.firestore.collection(REFERENCE_MESSENGERS).document(uid)
                .collection(REFERENCE_CHATS).document(chat.uid).delete()
            Firebase.firestore.collection(REFERENCE_MESSENGERS).document(chat.uid)
                .collection(REFERENCE_CHATS).document(uid).delete()
            return task.isSuccessful
        }
    }
}