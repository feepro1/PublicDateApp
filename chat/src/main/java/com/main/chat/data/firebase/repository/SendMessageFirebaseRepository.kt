package com.main.chat.data.firebase.repository

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.main.chat.data.storage.local.MessageCacheModel
import com.main.core.Resource
import com.main.core.entities.Chat
import com.main.core.entities.User
import com.main.core.exception.FirebaseException
import com.main.core.firebase.FirebaseConstants.REFERENCE_CHATS
import com.main.core.firebase.FirebaseConstants.REFERENCE_MESSAGES
import com.main.core.firebase.FirebaseConstants.REFERENCE_MESSENGERS
import com.main.core.firebase.FirebaseConstants.REFERENCE_USERS
import kotlinx.coroutines.tasks.await
import java.util.*

interface SendMessageFirebaseRepository {

    suspend fun sendMessage(messageCacheModel: MessageCacheModel): Resource<Boolean>

    class Base : SendMessageFirebaseRepository {

        override suspend fun sendMessage(messageCacheModel: MessageCacheModel): Resource<Boolean> {
            val isBannedTask = Firebase.firestore.collection(REFERENCE_MESSENGERS).document(messageCacheModel.receiverUid)
                .collection(REFERENCE_CHATS).document(messageCacheModel.senderUid).get()
            isBannedTask.await()
            return if (isBannedTask.result.data?.values?.first() == true) {
                Resource.Error(false, FirebaseException("Chat was banned"))
            } else {
                val firstDocumentInitTask = Firebase.firestore.collection(REFERENCE_MESSENGERS)
                    .document(messageCacheModel.receiverUid)
                firstDocumentInitTask.set(mapOf("Bubble" to "Hello, World!"))

                val secondDocumentInitTask = firstDocumentInitTask.collection(REFERENCE_CHATS)
                    .document(messageCacheModel.senderUid)
                secondDocumentInitTask.set(mapOf("isChatBanned" to false))

                val sendMessageTask = secondDocumentInitTask.collection(REFERENCE_MESSAGES)
                    .document(UUID.randomUUID().toString()).set(messageCacheModel)
                sendMessageTask.await()

                val senderUserTask = Firebase.firestore.collection(REFERENCE_USERS)
                    .document(messageCacheModel.senderUid).get()
                val receiverUserTask = Firebase.firestore.collection(REFERENCE_USERS)
                    .document(messageCacheModel.receiverUid).get()
                senderUserTask.await()
                receiverUserTask.await()
                //todo correct/check the logic
                val senderUserData = senderUserTask.result.toObject(User::class.java)
                val receiverUserData = receiverUserTask.result.toObject(User::class.java)
                senderUserData?.userChats?.put(
                    messageCacheModel.receiverUid,
                    senderUserData.mapToChat().copy(lastMessage = messageCacheModel.message)
                )
                receiverUserData?.userChats?.put(
                    messageCacheModel.senderUid,
                    receiverUserData.mapToChat().copy(lastMessage = messageCacheModel.message)
                )
                if (senderUserData != null && receiverUserData != null) {
                    Firebase.firestore.collection(REFERENCE_USERS).document(messageCacheModel.receiverUid).set(receiverUserData)
                    Firebase.firestore.collection(REFERENCE_USERS).document(messageCacheModel.senderUid).set(senderUserData)
                }

                if (sendMessageTask.isSuccessful) {
                    Resource.Success(true)
                } else {
                    Resource.Error(false, FirebaseException("Task is not successful"))
                }
            }
        }
    }
}