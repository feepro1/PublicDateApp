package com.main.swaplike.data.cloud.firebase

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.main.chat.data.storage.local.ChatCacheRepository
import com.main.chat.data.storage.local.MessageCacheModel
import com.main.core.firebase.FirebaseConstants.REFERENCE_CHATS
import com.main.core.firebase.FirebaseConstants.REFERENCE_MESSAGES
import com.main.core.firebase.FirebaseConstants.REFERENCE_MESSENGERS
import com.main.swaplike.domain.ManageMessages
import kotlinx.coroutines.tasks.await

class ManageMessagesImpl : ManageMessages {

    override suspend fun receiveMessagesFromFirebase(): List<MessageCacheModel> {
        val uid = Firebase.auth.currentUser?.uid.toString()
        val messages = mutableListOf<MessageCacheModel>()
        val task = Firebase.firestore.collection(REFERENCE_MESSENGERS).document(uid)
            .collection(REFERENCE_CHATS).get().addOnSuccessListener { userChats ->
                userChats.documents.forEach { userChat ->
                    val currentUid = userChat.reference.path.split("/").last()
                    Firebase.firestore.collection(REFERENCE_MESSENGERS).document(uid)
                        .collection(REFERENCE_CHATS).document(currentUid)
                        .collection(REFERENCE_MESSAGES).get()
                        .addOnSuccessListener {
                            it.documents.forEach { documentMessage ->
                                val messageCacheModel = documentMessage.toObject(MessageCacheModel::class.java)
                                messages.add(messageCacheModel ?: MessageCacheModel())
                            }
                        }
                }
            }
        task.await()
        return task.result.toObjects(MessageCacheModel::class.java)
    }

    override suspend fun deleteMessagesOnFirebase() {
        val uid = Firebase.auth.currentUser?.uid.toString()
        Firebase.firestore.collection(REFERENCE_MESSENGERS)
            .document(uid)
            .collection(REFERENCE_CHATS).get()
            .addOnSuccessListener { userChats ->
                userChats.documents.forEach { userChat ->
                    val uidUser = userChat.reference.path.split("/").last()
                    Firebase.firestore.collection(REFERENCE_MESSENGERS)
                        .document(uid)
                        .collection(REFERENCE_CHATS).document(uidUser).delete()
                    Firebase.firestore.collection(REFERENCE_MESSENGERS)
                        .document(uid)
                        .collection(REFERENCE_CHATS).document(uidUser).set(mapOf("isChatBanned" to false))
                }
            }
    }

    override suspend fun addMessagesToLocalDatabase(
        list: List<MessageCacheModel>,
        chatCacheRepository: ChatCacheRepository
    ) {
        list.forEach { messageCacheModel ->
            chatCacheRepository.addMessage(messageCacheModel)
        }
    }
}