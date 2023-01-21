package com.main.swaplike.data.cloud.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.main.chat.data.storage.local.MessageCacheModel
import com.main.chat.data.storage.local.ChatDao
import com.main.chats.data.storage.local.ChatsCacheModel
import com.main.chats.data.storage.local.ChatsDao

@Database(entities = [ChatsCacheModel::class, MessageCacheModel::class], version = 3)
abstract class DatingDatabase : RoomDatabase() {

    abstract fun chatsDao(): ChatsDao
    abstract fun chatDao(): ChatDao

    companion object {
        private var database: DatingDatabase? = null

        @Synchronized
        fun getInstance(context: Context) : DatingDatabase {
            return if (database == null) {
                database = Room.databaseBuilder(context, DatingDatabase::class.java, "dating_database")
                    .fallbackToDestructiveMigration()
                    .build()
                database as DatingDatabase
            } else {
                database as DatingDatabase
            }
        }
    }
}