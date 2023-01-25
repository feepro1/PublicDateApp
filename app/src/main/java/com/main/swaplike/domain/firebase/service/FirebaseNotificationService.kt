package com.main.swaplike.domain.firebase.service

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.main.chat.data.notification.ManageNotificationImpl
import com.main.chat.data.storage.shared_pref.ManageSharedPreferences
import com.main.chat.domain.notification.ManageNotification
import com.main.swaplike.data.cloud.firebase.ManageNotificationTokenImpl

class FirebaseNotificationService : FirebaseMessagingService() {

    private var manageNotification: ManageNotification = ManageNotificationImpl(ManageSharedPreferences.Base())
    private val manageNotificationTokenImpl = ManageNotificationTokenImpl()

    override fun onMessageReceived(message: RemoteMessage) {
        val username = message.notification?.title.toString()
        val content = message.notification?.body.toString()
        val context = this.baseContext

        manageNotification.showNotification(context, username, content)
        super.onMessageReceived(message)
    }

    override fun onNewToken(token: String) {
        manageNotificationTokenImpl.updateToken(token)
        super.onNewToken(token)
    }
}