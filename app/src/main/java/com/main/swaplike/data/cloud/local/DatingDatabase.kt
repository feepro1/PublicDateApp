package com.main.swaplike.data.cloud.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.main.chats.data.cloud.local.ChatCacheModel
import com.main.chats.data.cloud.local.ChatsDao

@Database(entities = [ChatCacheModel::class], version = 1)
abstract class DatingDatabase : RoomDatabase() {

    abstract fun chatsDao(): ChatsDao

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