package com.main.chats.data.storage.remote

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.main.core.entities.Chat
import com.main.core.entities.User
import com.main.core.firebase.FirebaseConstants.REFERENCE_CHATS
import com.main.core.firebase.FirebaseConstants.REFERENCE_MESSAGES
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
            val uid = Firebase.auth.currentUser?.uid.toString()
            val task = Firebase.firestore.collection(REFERENCE_USERS).document(uid).get()
            task.await()
            val result = task.result.toObject(User::class.java)
            result?.userChats?.remove(chat.uid)
            val firstUserMessagesTask = Firebase.firestore.collection(REFERENCE_MESSENGERS).document(chat.uid)
                .collection(REFERENCE_CHATS).document(uid).collection(REFERENCE_MESSAGES).get()
            val secondUserMessagesTask = Firebase.firestore.collection(REFERENCE_MESSENGERS).document(uid)
                .collection(REFERENCE_CHATS).document(chat.uid).collection(REFERENCE_MESSAGES).get()
            val firstUserDataTask = Firebase.firestore.collection(REFERENCE_USERS).document(chat.uid).get()
            val secondUserDataTask = Firebase.firestore.collection(REFERENCE_USERS).document(uid).get()
            firstUserMessagesTask.await()
            secondUserMessagesTask.await()
            firstUserMessagesTask.result.documents.forEach { documentSnapshot ->
                Firebase.firestore.collection(REFERENCE_MESSENGERS).document(chat.uid)
                    .collection(REFERENCE_CHATS).document(uid)
                    .collection(REFERENCE_MESSAGES).document(documentSnapshot.id).delete()
            }
            secondUserMessagesTask.result.documents.forEach { documentSnapshot ->
                Firebase.firestore.collection(REFERENCE_MESSENGERS).document(uid)
                    .collection(REFERENCE_CHATS).document(chat.uid)
                    .collection(REFERENCE_MESSAGES).document(documentSnapshot.id).delete()
            }
            firstUserDataTask.await()
            secondUserDataTask.await()
            val firstUserData = firstUserDataTask.result.toObject(User::class.java)
            val secondUserData = secondUserDataTask.result.toObject(User::class.java)
            firstUserData?.userChats?.remove(secondUserData?.uid)
            secondUserData?.userChats?.remove(firstUserData?.uid)

            Firebase.firestore.collection(REFERENCE_MESSENGERS).document(chat.uid)
                .collection(REFERENCE_CHATS).document(uid).delete()
            Firebase.firestore.collection(REFERENCE_MESSENGERS).document(uid)
                .collection(REFERENCE_CHATS).document(chat.uid).delete()
            if (firstUserData != null && secondUserData != null) {
                Firebase.firestore.collection(REFERENCE_USERS).document(chat.uid).set(firstUserData)
                Firebase.firestore.collection(REFERENCE_USERS).document(uid).set(secondUserData)
            }
            return task.isSuccessful
        }
    }
}