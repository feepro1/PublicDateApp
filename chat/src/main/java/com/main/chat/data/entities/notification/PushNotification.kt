package com.main.chat.data.entities.notification

data class PushNotification(
    val to: String,
    val notification: NotificationData
)