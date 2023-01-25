package com.main.chat.data.storage.firebase.notification.repository

import com.main.chat.data.entities.notification.PushNotification
import retrofit2.Response

interface NotificationRepository {

    suspend fun sendNotification(pushNotification: PushNotification) : Response<PushNotification>
}