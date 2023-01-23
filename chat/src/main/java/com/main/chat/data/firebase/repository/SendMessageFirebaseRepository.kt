package com.main.chat.data.firebase.repository

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.main.chat.data.storage.local.MessageCacheModel
import com.main.core.Resource
import com.main.core.exception.FirebaseException
import kotlinx.coroutines.tasks.await
import java.util.*

interface SendMessageFirebaseRepository {

    suspend fun sendMessage(messageCacheModel: MessageCacheModel): Resource<Boolean>

    class Base : SendMessageFirebaseRepository {

        override suspend fun sendMessage(messageCacheModel: MessageCacheModel): Resource<Boolean> {
            val task = Firebase.firestore.collection(REFERENCE_MESSENGERS).document(messageCacheModel.receiverUid)
                .collection(REFERENCE_CHATS).document(messageCacheModel.senderUid).collection(REFERENCE_MESSAGES)
                .document(UUID.randomUUID().toString()).set(messageCacheModel)
            task.await()
            return if (task.isSuccessful) {
                Resource.Success(true)
            } else {
                Resource.Error(false, FirebaseException("Exception"))
            }
        }
    }

    companion object {
        const val REFERENCE_MESSENGERS = "messengers1"
        const val REFERENCE_CHATS = "chats"
        const val REFERENCE_MESSAGES = "messages"
    }
}