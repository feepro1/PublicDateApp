package com.main.chat.data.repository

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.main.chat.data.entities.notification.NotificationData
import com.main.chat.data.entities.notification.PushNotification
import com.main.chat.data.entities.notification.UserInfoForNotification
import com.main.chat.data.firebase.repository.SendMessageFirebaseRepository
import com.main.chat.data.storage.firebase.notification.repository.NotificationRepository
import com.main.chat.data.storage.local.ChatCacheRepository
import com.main.chat.data.storage.local.MessageCacheModel
import com.main.core.Resource
import com.main.core.firebase.FirebaseConstants.REFERENCE_USERS
import kotlinx.coroutines.tasks.await

interface SendMessageRepository {

    suspend fun sendMessage(messageCacheModel: MessageCacheModel): Resource<Boolean>

    class Base(
        private val chatCacheRepository: ChatCacheRepository,
        private val sendMessageFirebaseRepository: SendMessageFirebaseRepository,
        private val notificationRepository: NotificationRepository
    ) : SendMessageRepository {

        override suspend fun sendMessage(messageCacheModel: MessageCacheModel): Resource<Boolean> {
            chatCacheRepository.addMessage(messageCacheModel)
            val task = Firebase.firestore.collection(REFERENCE_USERS)
                .document(messageCacheModel.receiverUid).get()
            task.await()
            val userInfo = task.result.toObject<UserInfoForNotification>()
            val pushNotification = PushNotification(
                userInfo?.token.toString(),
                NotificationData(
                    title = "${userInfo?.firstName} ${userInfo?.lastName}",
                    body = messageCacheModel.message
                )
            )
            notificationRepository.sendNotification(pushNotification)
            return sendMessageFirebaseRepository.sendMessage(messageCacheModel)
        }
    }
}