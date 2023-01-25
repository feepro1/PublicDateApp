package com.main.chat.data.notification

import android.content.Context
import com.application.isradeleon.notify.Notify
import com.main.chat.R
import com.main.chat.data.storage.shared_pref.ManageSharedPreferences
import com.main.chat.domain.notification.ManageNotification

class ManageNotificationImpl(
    private val manageSharedPreferences: ManageSharedPreferences
) : ManageNotification {

    override fun showNotification(context: Context, titleAndUsername: String, content: String) {
        val number = (Int.MIN_VALUE..Int.MAX_VALUE).random()
        manageSharedPreferences.getArrayFromPreferencesNotificationArray(context, titleAndUsername)
        Notify.build(context)
            .setTitle(titleAndUsername)
            .setContent(content)
            .setImportance(Notify.NotifyImportance.HIGH)
            .setId(number)
            .setSmallIcon(R.drawable.icon_new_user)
            .show()
    }
}