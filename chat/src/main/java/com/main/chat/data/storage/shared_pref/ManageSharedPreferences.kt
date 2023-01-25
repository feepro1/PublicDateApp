package com.main.chat.data.storage.shared_pref

import android.content.Context

interface ManageSharedPreferences {

    fun addNewItemToPreferencesNotificationArray(context: Context, int: Int, username: String)

    fun getArrayFromPreferencesNotificationArray(context: Context, username: String): Array<Int?>

    fun clearPreferencesNotificationArray(context: Context, username: String)

    class Base : ManageSharedPreferences {
        override fun addNewItemToPreferencesNotificationArray(context: Context, int: Int, username: String) {
            val prefs = context.getSharedPreferences(SHARED_PREFERENCES_NOTIFICATION_PREFERENCES+username, 0)
            val editor = prefs.edit()
            val array = getArrayFromPreferencesNotificationArray(context, username)
            editor.putInt(SHARED_PREFERENCES_NOTIFICATION_ARRAY, array.size+1)
            for (i in array.indices) editor.putInt(SHARED_PREFERENCES_NOTIFICATION_ARRAY + "_" + i, array[i].toString().toInt())
            editor.putInt(SHARED_PREFERENCES_NOTIFICATION_ARRAY + "_" + array.size, int)
            editor.apply()
        }

        override fun getArrayFromPreferencesNotificationArray(context: Context, username: String): Array<Int?> {
            val prefs = context.getSharedPreferences(SHARED_PREFERENCES_NOTIFICATION_PREFERENCES + username, 0)
            val size = prefs.getInt(SHARED_PREFERENCES_NOTIFICATION_ARRAY, 0)
            val array = arrayOfNulls<Int>(size)
            for (i in 0 until size) array[i] = prefs.getInt(SHARED_PREFERENCES_NOTIFICATION_ARRAY + "_" + i, 0)
            return array
        }

        override fun clearPreferencesNotificationArray(context: Context, username: String) {
            val prefs = context.getSharedPreferences(SHARED_PREFERENCES_NOTIFICATION_PREFERENCES + username, 0)
            val editor = prefs.edit()
            editor.clear()
            editor.apply()
        }
    }

    companion object  {
        const val SHARED_PREFERENCES_NOTIFICATION_ARRAY = "notification_array"
        const val SHARED_PREFERENCES_NOTIFICATION_PREFERENCES = "notification_preferences"
    }
}