package com.main.chat.data.storage.firebase.notification.repository

import com.main.chat.data.entities.notification.PushNotification
import com.main.chat.data.storage.firebase.notification.api.NotificationApi
import retrofit2.Response

class NotificationRepositoryImpl(
    private val notificationApi: NotificationApi
): NotificationRepository {

    override suspend fun sendNotification(pushNotification: PushNotification) : Response<PushNotification> {
        return notificationApi.sendNotification(pushNotification)
    }
}