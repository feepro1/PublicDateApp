package com.main.chat.domain.notification

import android.content.Context

interface ManageNotification {

    fun showNotification(
        context: Context,
        titleAndUsername: String,
        content: String
    )
}